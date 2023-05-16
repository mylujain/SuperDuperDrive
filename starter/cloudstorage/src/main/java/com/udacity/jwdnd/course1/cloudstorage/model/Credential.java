package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.*;

@Setter
@Getter
public class Credential {
  //  @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer credentialid;
    private String url;
    private String username;
    private String key;
    private String password;
    private String decryptedPassword;
    private Integer userid;
}
