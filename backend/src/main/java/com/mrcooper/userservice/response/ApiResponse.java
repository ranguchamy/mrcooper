package com.mrcooper.userservice.response;

import com.mrcooper.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse  {
    private String message;
    private Object data;
}