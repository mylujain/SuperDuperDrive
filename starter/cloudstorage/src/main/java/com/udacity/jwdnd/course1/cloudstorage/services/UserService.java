package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Encoder;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public int usernameAvailability(String username) {
        int available=0;
        if(userMapper.getUserByUsername(username) != null)
            available=1;
        return available;
    }

    public int createUser(User user) {
        String salt = Encoder.getEncoded();
        user.setSalt(salt);
        String pass=user.getPassword();
        user.setPassword(hashService.getHashedValue(pass, salt));
        return userMapper.insertUser(user);

    }

    public User getUser(String username) {
        return userMapper.getUserByUsername(username);
    }


}
