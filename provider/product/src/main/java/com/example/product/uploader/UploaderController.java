package com.example.product.uploader;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/uploader")
public class UploaderController {
    @PostMapping(value = "/image")
    public String uploader(@RequestParam("file") MultipartFile file) {
        if (file == null) {
            return "";
        }
        File dest = new File("/Users/luok/tmp");
        if (!dest.exists()){
            dest.mkdirs();
        }
        System.out.println(file.getName()+":name");
        System.out.println(file.getOriginalFilename()+":OriginalFilename");

        try {
            dest = new File(dest.getPath()+file.getOriginalFilename());
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dest.getPath();

    }
}
