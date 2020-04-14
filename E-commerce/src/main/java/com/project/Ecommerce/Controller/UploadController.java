package com.project.Ecommerce.Controller;


import com.project.Ecommerce.Dao.UploadDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class UploadController
{
    @Autowired
    UploadDao uploadDao;


    @PostMapping("/uploadSm")
    public ResponseEntity<Object> uploadFiles(@RequestParam("files") MultipartFile[] files) throws IOException
    {
        return uploadDao.uploadMultipleFiles(files);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName, HttpServletRequest request) throws IOException
    {
        return uploadDao.downloadImage(fileName,request);
    }


}