package com.example.filetransfer.controller;

import com.example.filetransfer.model.LoadFile;
import com.example.filetransfer.repo.FileRepository;
import com.example.filetransfer.service.FileService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/file")
public class FileController {

    private FileService fileService;
    private GridFsTemplate gridFsTemplate;
    private FileRepository fileRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") List<MultipartFile> multipartFiles) throws IOException {
        List<String> filenames = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String filename = file.getOriginalFilename();
            DBObject metadata = new BasicDBObject();
            metadata.put("fileSize", file.getSize());
            metadata.put("fileName", file.getOriginalFilename());
            gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metadata);
            LoadFile loadFile = new LoadFile();
            loadFile.setFileName(filename);
            fileRepository.insert(loadFile);
            filenames.add(filename);
        }
        return ResponseEntity.ok().body(filenames);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String filename) throws IOException {
        LoadFile loadFile = fileService.downloadFile(filename);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", loadFile.getFileName());
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFileName() + "\"");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getFileType()))
                .headers(httpHeaders)
                .body(new ByteArrayResource(loadFile.getFile()));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getTodos() {
        List<String> filenames = new ArrayList<>();
        List<LoadFile> loadFiles = fileRepository.findAll();
        for(LoadFile file: loadFiles) {
            String filename = file.getFileName();
            filenames.add(filename);
        }
        return ResponseEntity.ok().body(filenames);
    }

    @DeleteMapping("/delete/{filename}")
    public void  delete(@PathVariable String filename) {
        fileService.deleteFile(filename);
        fileRepository.deleteByFileName(filename);
    }






}
