package edu.skku.dealistic.payload;

import lombok.Data;

@Data
public class SignupRequest {
    private String id;
    private String name;
    private String organization;
    private String password;
}
