package com.vn.authservice.client;


import com.vn.authservice.dto.request.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserClient {
    @PutMapping("/api/users/update")
    void updateUser(@RequestBody UserRequest userRequest);

    @PostMapping("/api/users")
    void createUser(@RequestBody UserRequest userRequest);

//    void setAccountId(int idAccount);
}
