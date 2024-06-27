package com.example.DOAN1.RequestResponse;

import com.example.DOAN1.Entities.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
//    private Long id;
    private String token;
    private String type = "Bearer";
    private Long id;
    private RoleName role;

    public JwtResponse(String token, Long id, RoleName role){
        this.token = token;
        this.id = id;
        this.role = role;
    }

}
