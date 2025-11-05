package com.importservice.dto;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
    private MultipartFile file;

    public FileUpload() {}

    public FileUpload(MultipartFile file) { this.file = file; }

    public MultipartFile getFile() { return file; }
    public void setFile(MultipartFile file) { this.file = file; }
}
