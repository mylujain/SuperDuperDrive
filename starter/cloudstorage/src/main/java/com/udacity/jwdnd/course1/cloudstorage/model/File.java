package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.*;

@Setter
@Getter
public class File {

    private Integer fileId;
    private String filename;
    private String contenttype;
    private Long filesize;
    private Integer userid;
    private byte[] filedata;
}
