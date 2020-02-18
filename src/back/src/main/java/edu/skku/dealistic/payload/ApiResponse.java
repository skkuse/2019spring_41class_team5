package edu.skku.dealistic.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    private String message;
    private Object data;

    public static final String SUCCESS_MSG = "success!";
    public static final ApiResponse SUCCESS = ApiResponse.builder().message(SUCCESS_MSG).build();
}
