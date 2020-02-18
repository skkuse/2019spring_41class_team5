package edu.skku.dealistic.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String id;
    private String password;
}
