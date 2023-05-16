package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.*;

@Setter
@Getter
public class User {
//@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userid;
    private String username;
    private String salt;
    private String password;
    private String firstname;
    private String lastname;

}