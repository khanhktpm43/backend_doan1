package com.example.DOAN1.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
   // @GeneratedValue(strategy =  GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "USERS_SEQ", allocationSize = 1)
    private  Long id;
    @NotBlank
    @Size(min = 3, max = 50)
    @Column(name="user_name")
    private String userName;
    @NotBlank
    @Size(min = 3, max = 100)
    @Column(name="pass_word")
    private String passWord;
    @Enumerated(EnumType.STRING)
    private RoleName role;
//    @JsonBackReference("lecturer")
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "lecturer_id")
    @JsonManagedReference("user")
    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private Lecturer lecturer;
    @JsonIgnore
    @ElementCollection
    private List<GrantedAuthority> authorities ;

    public User(String name, String pass){
        this.userName = name;
        this.passWord = pass;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
