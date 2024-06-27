package com.example.DOAN1.Controller;

import com.example.DOAN1.JWT.JwtTokenFilter;
import com.example.DOAN1.RequestResponse.JwtResponse;
import com.example.DOAN1.RequestResponse.PassRequest;
import com.example.DOAN1.RequestResponse.ResponseObject;
import com.example.DOAN1.Entities.RoleName;
import com.example.DOAN1.Entities.User;
import com.example.DOAN1.JWT.JwtProvider;
import com.example.DOAN1.Repositories.UserRepository;
import com.example.DOAN1.Service.BlackList;
import com.example.DOAN1.Service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    UserRepository repository;
    @Autowired
    UserDetailService service;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JwtTokenFilter filter;
    @Autowired
    BlackList List;


    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody User user){

        if(repository.existsByUserName(user.getUsername())){
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObject("fail","username exist",null));
        }
        try {
            RoleName.valueOf(user.getRole().toString());
            user.setPassWord(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "", user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObject("fail","set role fail",null));
        }

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody User user){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        User userPrinciple = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getId(), userPrinciple.getRole()));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null){
            String token = filter.getJwt(request);
            List.addItemToList(token);
            logoutHandler.logout(request, response, authentication);
        }
        return ResponseEntity.ok("Logged out successfully");
    }
    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody PassRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Check if old password is correct
        UserDetails userDetails = service.loadUserByUsername(username);
        if (!new BCryptPasswordEncoder().matches(request.getPass(), userDetails.getPassword())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","không đúng mật khẩu",null));
        }

        // Check if new password and confirm password match
        if (!request.getNewPass().equals(request.getRePass())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("fail","pass không trùng",null));
        }

        // Update password
        service.changePassword(username, request.getNewPass());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","",null));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){

        return ResponseEntity.ok(service.getCurrentUser());
    }
}
