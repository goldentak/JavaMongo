package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByDataPublicDataUsername(String username);

    boolean existsByDataPrivateDataEmail(String email);

    Optional<User> findByDataPublicDataUsernameAndDataPrivateDataPassword(String username, String password);

    boolean existsByDataPublicDataUsername(String username);

    Optional<User> findByDataPublicDataUsernameAndDataPrivateDataPasswordAndDataPrivateDataEmail(
            String username,
            String password,
            String email
    );

}
