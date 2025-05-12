package com.example.demo.repository;

import com.example.demo.model.Dog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DogRepo extends MongoRepository<Dog, UUID> {
    Dog findByName(String name);
}
