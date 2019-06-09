package edu.skku.dealistic.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    private String message;
    private Object data;

    public static final ApiResponse SUCCESS = ApiResponse.builder().message("success").build();
}
