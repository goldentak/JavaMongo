package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User createUserFromDto(UserDto userDto) {
        User user = new User();

        User.DataBlock dataBlock = new User.DataBlock();

        if(userDto.getEmail() != null && userDto.getPassword() != null) {
            User.PrivateData privateData = new User.PrivateData();
            privateData.setEmail(userDto.getEmail());
            privateData.setPassword(userDto.getPassword());
            dataBlock.setPrivateData(privateData);
        }

        if(userDto.getUsername() != null && userDto.getBio() != null && userDto.getAge() != null) {
            User.PublicData publicData = new User.PublicData();
            publicData.setUsername(userDto.getUsername());
            publicData.setBio(userDto.getBio());
            publicData.setAge(userDto.getAge());
            dataBlock.setPublicData(publicData);
        }

        if (dataBlock.getPrivateData() != null || dataBlock.getPublicData() != null) {
            user.setData(dataBlock);
        }

        User.ServiceBlock serviceBlock = new User.ServiceBlock();
        serviceBlock.setSubscriptions(new ArrayList<>());
        serviceBlock.setSubscribers(new ArrayList<>());
        serviceBlock.setLikes(new ArrayList<>());
        serviceBlock.setComments(new ArrayList<>());
        user.setService(serviceBlock);

        return userRepo.save(user);
    }


    public void follow(FollowDto followDto){
        String targetUsername = followDto.getUsername();
        String authUsername = followDto.getAuthUsername();

        var authUserOpt = userRepo.findByDataPublicDataUsernameAndDataPrivateDataPassword(
                followDto.getUsername(),
                followDto.getPassword()
                );

        var targetUserOpt = userRepo.findByDataPublicDataUsername(targetUsername);

        if(authUserOpt.isEmpty()){
            System.out.println("Auth failed: invalid username or password");
            return;
        }

        if (targetUserOpt.isEmpty()) {
            System.out.println("Target user not found");
            return;
        }

        User authUser = authUserOpt.get();
        User targetUser = targetUserOpt.get();

        if (authUser.getService().getSubscriptions().contains((targetUser.getId()))){
            System.out.println("⚠Already following");
            return;
        }

        authUser.getService().getSubscriptions().add(targetUser.getId());
        targetUser.getService().getSubscribers().add(authUser.getId());

        userRepo.save(authUser);
        userRepo.save(targetUser);

        System.out.println(authUser.getData().getPublicData().getUsername()
                + " now follows " + targetUsername);
    }

    public boolean changeBio(BioDto bioDto) {
        String username = bioDto.getUsername();
        String password = bioDto.getPassword();
        String newBio = bioDto.getBio();

        Optional<User> userOpt = userRepo.findByDataPublicDataUsernameAndDataPrivateDataPassword(username, password);

        if (userOpt.isEmpty()) {
            System.out.println("Target user not found");
            return false;
        }

        User user = userOpt.get();
        user.getData().getPublicData().setBio(newBio);
        userRepo.save(user);

        System.out.println("Bio updated for " + username);
        return true;
    }

    public boolean changeUsername(UsernameDto usernameDto) {
        String username = usernameDto.getUsername();
        String password = usernameDto.getPassword();
        String newUsername = usernameDto.getNewUsername();

        Optional<User> userOpt = userRepo.findByDataPublicDataUsernameAndDataPrivateDataPassword(
                username,
                password
        );

        if (userOpt.isEmpty()) {
            System.out.println("Invalid username or password");
            return false;
        }

        if (username.equals(newUsername)) {
            System.out.println("New username is the same as current username");
            return false;
        }

        if (userRepo.existsByDataPublicDataUsername(newUsername)) {
            System.out.println("Username already taken");
            return false;
        }

        User user = userOpt.get();
        user.getData().getPublicData().setUsername(newUsername);
        userRepo.save(user);

        System.out.println("Username updated from " + username + " to " + newUsername);
        return true;
    }

    public boolean changePassword(PasswordDto passwordDto) {
        String username = passwordDto.getUsername();
        String email = passwordDto.getEmail();
        String password = passwordDto.getPassword();
        String newPassword = passwordDto.getNewPassword();

        Optional<User> userOpt = userRepo.findByDataPublicDataUsernameAndDataPrivateDataPasswordAndDataPrivateDataEmail(username, password, email);

        if (userOpt.isEmpty()) {
            System.out.println("Target user not found");
            return false;
        }

        User user = userOpt.get();
        user.getData().getPrivateData().setPassword(newPassword);
        userRepo.save(user);

        System.out.println("Password updated for " + username + " the new password is " + newPassword);
        return true;
    }

}
