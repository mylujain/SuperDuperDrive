package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final FileService fileService;


    public FileController(UserService userService, FileService storageService) {
        this.userService = userService;
        this.fileService = storageService;

    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> findFile(Authentication auth, @PathVariable Integer fileId) {
        File file = fileService.findFile(fileId, userService.getUser(auth.getName()).getUserid());
        String content = file.getContenttype();
        byte[] data = file.getFiledata();
        ResponseEntity<Resource> res = ResponseEntity.ok().contentType(MediaType.parseMediaType(content))
                .header(HttpHeaders.CONTENT_DISPOSITION, "; filename=" + file.getFilename())
                .body(new InputStreamResource(new ByteArrayInputStream(data)));
        return res;
    }

    @PostMapping
    public String uploadFile(Model model, Authentication auth, @RequestParam("fileUpload") MultipartFile file) {
        int userid = userService.getUser(auth.getName()).getUserid();
        try {
            if (fileService.getFileByFileName(userid, file.getOriginalFilename()) != null) {
                model.addAttribute("sameNameError", true);
                model.addAttribute("Message", "there is a file already exists with same name.");
            } else {
                fileService.storeFile(file, userid);
                model.addAttribute("changeSuccessfully", true);
            }
        } catch (Exception exception) {
            model.addAttribute("error", true);
        }
        model.addAttribute("tab", "");
        return "result";
    }

    @PostMapping("/delete/{fileId}")
    public String deleteFile(Model model, Authentication auth, @PathVariable Integer fileId) {
        fileService.deleteFile(fileId, userService.getUser(auth.getName()).getUserid());
        model.addAttribute("changeSuccessfully", true);
        model.addAttribute("tab", "");
        return "result";
    }
}
