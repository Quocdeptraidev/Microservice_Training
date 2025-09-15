package com.vn.userservice.service;


import com.vn.userservice.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(int id);
}
