package com.vn.authservice.dto.request;


import lombok.Data;

@Data

public class UserRequest {
    private Integer accountId;
    private String fullName;
}