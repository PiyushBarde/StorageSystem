package com.bridgelabz.simplestorageservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.simplestorageservice.service.S3Service;

@RestController
@RequestMapping("/file")
public class S3Controller {
	@Autowired
	private S3Service service;
	 	
 	//to upload file through param
    @PostMapping("/file/upload")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
        return new ResponseEntity(service.uploadFile(file), HttpStatus.OK);
    }
    
    //to download file from s3
    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = service.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
    
    //to delete file from s3
    @DeleteMapping("/file/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity(service.deleteFile(fileName), HttpStatus.OK);
    }
}
