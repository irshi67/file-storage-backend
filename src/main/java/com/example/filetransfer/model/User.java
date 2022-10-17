package com.example.filetransfer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "user")
@TypeAlias("User")
@Accessors(chain = true)
public class User extends Base {
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String email;
    private String password;
    private boolean loggedIn;



}
