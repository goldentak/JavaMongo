package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class Dog {
    @Id
    private UUID id;

    @Indexed(unique = true)
    private String name;

    private Integer age;

}
