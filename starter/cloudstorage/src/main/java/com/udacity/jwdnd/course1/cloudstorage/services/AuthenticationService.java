package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HashService hashService;



    public AuthenticationService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String password = authentication.getCredentials().toString();
        if (userMapper.getUserByUsername(authentication.getName()) != null) {
           String savedPas=userMapper.getUserByUsername(authentication.getName()).getPassword();
           String enteredPas=hashService.getHashedValue(password, userMapper.getUserByUsername(authentication.getName()).getSalt());
            boolean check= savedPas.equals(enteredPas);
            if (check) {
                return new UsernamePasswordAuthenticationToken(authentication.getName(), password, new ArrayList<>());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
