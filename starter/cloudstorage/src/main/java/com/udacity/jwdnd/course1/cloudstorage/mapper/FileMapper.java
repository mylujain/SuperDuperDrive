package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE  userid = #{userid} AND fileId = #{fileId}")
    File getFileById(Integer fileId, Integer userid);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} and filename = #{fileName}")
    File getFileByFileName(Integer userId, String fileName);
    @Select("SELECT fileId, filename FROM FILES WHERE userid = #{userid}")
    List<File> getAllFiles(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Delete("DELETE FROM FILES WHERE userid = #{userid} AND fileId = #{fileId}")
    Integer deleteFile(Integer fileId, Integer userid);
}
