package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document(collection="users")
public class User {
    @Id
    private String id;

    private DataBlock data;
    private ServiceBlock service;

    @Data
    public static class DataBlock {
        private PrivateData privateData;
        private PublicData publicData;
    }

    @Data
    public static class PrivateData {
        private String email;
        private String phone;
        private String password;
    }

    @Data
    public static class PublicData {
        private String username;
        private String bio;
        private Integer age;
    }

    @Data
    public static class ServiceBlock {
        private List<String> subscriptions;
        private List<String> subscribers;
        private List<String> likes;
        private List<String> comments;
    }
}
