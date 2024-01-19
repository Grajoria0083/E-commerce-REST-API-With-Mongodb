package com.ecommerce.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

@RestController
public class FileController {


    @Value("project.image")
    String path;


    @PostMapping("/fileUpload")
    String imgUpload(@RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {

        String name = multipartFile.getOriginalFilename();
//        String fileName = UUID.randomUUID().toString()+ path+ File.separator+name;
        String filePath = path+name;
        System.out.println("name "+name);
        System.out.println("file Path "+filePath);
        System.out.println("full paht "+Paths.get(filePath));
        File file = new File(path);

        if (!file.exists()){
            file.mkdir();
        }
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
        return name;

    }


}
