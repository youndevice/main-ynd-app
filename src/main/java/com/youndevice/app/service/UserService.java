package com.youndevice.app.service;


import com.youndevice.app.domain.User;

public interface UserService {
    public User findUserByEmail(String email);

    public void saveUser(User user);
}