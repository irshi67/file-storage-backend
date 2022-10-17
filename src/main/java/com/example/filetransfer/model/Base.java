package com.example.filetransfer.model;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

@Data
public class Base {
    @Id
    private String id;
}
