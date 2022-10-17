package com.example.filetransfer.service;

import com.example.filetransfer.model.LoadFile;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class FileService {
    private GridFsTemplate gridFsTemplate;
    private GridFsOperations gridFsOperations;

    public LoadFile downloadFile(String id) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("filename").is(id)));
        LoadFile loadFile = new LoadFile();
        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setId(UUID.randomUUID().toString());
            loadFile.setFileName(gridFSFile.getFilename());
            loadFile.setFileType(gridFSFile.getMetadata().get("_contentType").toString());
            loadFile.setFileSize(gridFSFile.getMetadata().get("fileSize").toString());
            loadFile.setFile(IOUtils.toByteArray(gridFsOperations.getResource(gridFSFile).getInputStream()));
        }
        return loadFile;
    }


    public void deleteFile(String filename) {
        gridFsTemplate.delete(new Query(Criteria.where("filename").is(filename)));
        gridFsOperations.delete(new Query(Criteria.where("filename").is(filename)));
    }

}
