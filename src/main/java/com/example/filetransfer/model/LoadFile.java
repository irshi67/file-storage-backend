package com.example.filetransfer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoadFile extends Base{
    private String fileName;
    private String fileType;
    private String fileSize;
    private byte[] file;
}
