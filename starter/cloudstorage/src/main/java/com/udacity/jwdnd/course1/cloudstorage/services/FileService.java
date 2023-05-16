package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    @Autowired
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }


    public File findFile(Integer fileId, Integer userid) {
        return fileMapper.getFileById(fileId, userid);
    }


    public File getFileByFileName(Integer userId, String fileName){
        return fileMapper.getFileByFileName(userId, fileName);
    }
    public List<File> getAllFiles(Integer userid) {
        return fileMapper.getAllFiles(userid);
    }

    public void storeFile(MultipartFile file, Integer userid) throws IOException {
        File container = new File();

        container.setFilename(file.getOriginalFilename());
        container.setContenttype(file.getContentType());
        container.setFilesize(file.getSize());
        container.setUserid(userid);
        container.setFiledata(file.getBytes());

        fileMapper.insertFile(container);
    }
    public void deleteFile(Integer fileId, Integer userid) {
        fileMapper.deleteFile(fileId, userid);
    }
}

