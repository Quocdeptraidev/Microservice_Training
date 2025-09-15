package com.vn.userservice.dto.request;

import lombok.Data;

@Data
public class UserRequest {
    private Integer accountId;     // ID lấy từ authservice
    private String fullName;       // Thông tin cần cập nhật
}