package com.example.filetransfer.repo;

import com.example.filetransfer.model.LoadFile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FileRepository extends MongoRepository<LoadFile, String> {
    void insert(List<String> filenames);

    void deleteByFileName(String filename);
}
