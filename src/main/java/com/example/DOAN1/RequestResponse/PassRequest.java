package com.example.DOAN1.RequestResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassRequest {
    private String pass;
    private String newPass;
    private String rePass;

}
