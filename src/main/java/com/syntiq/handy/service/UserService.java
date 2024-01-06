package com.syntiq.handy.service;

import com.syntiq.handy.exception.UserNotFoundException;
import com.syntiq.handy.model.UserRole;
import com.syntiq.handy.model.dto.UserDto;
import com.syntiq.handy.model.dto.UserInfoDto;
import com.syntiq.handy.model.entity.User;
import com.syntiq.handy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;



    public UserInfoDto createUser(UserInfoDto userInfoDto) {
        User newUser = User.builder()
                .fullName(userInfoDto.getName())
                .firstName(userInfoDto.getGivenName())
                .lastName(userInfoDto.getFamilyName())
                .email(userInfoDto.getEmail())
                .sub(UUID.randomUUID().toString())
                .role(UserRole.CUSTOMER)
                .writtenReviews(ConcurrentHashMap.newKeySet())
                .receivedReviews(ConcurrentHashMap.newKeySet())
                .jobPosts(ConcurrentHashMap.newKeySet())
                .registrationDate(LocalDateTime.now())
                .lastLoginDate(LocalDateTime.now())
                .build();
        User created = this.userRepository.save(newUser);
        return this.mapToUserInfoDto(created);
    }

    public UserInfoDto updateUserInfo(UserInfoDto userInfoDto) throws UserNotFoundException {
        // Find existing user
        User existingUser = this.userRepository.findById(userInfoDto.getId())
                .orElseThrow(UserNotFoundException::new);

        // Create new user with updated Information
        User updatedUser = this.mapToUser(userInfoDto);
        updatedUser.setRole(existingUser.getRole());
        updatedUser.setJobPosts(existingUser.getJobPosts());
        updatedUser.setLastLoginDate(LocalDateTime.now());
        updatedUser.setReceivedReviews(existingUser.getReceivedReviews());
        updatedUser.setWrittenReviews(existingUser.getWrittenReviews());
        updatedUser.setRegistrationDate(existingUser.getRegistrationDate());

        User savedUser = this.userRepository.save(updatedUser);
        return this.mapToUserInfoDto(savedUser);

    }

    public void deleteUser(UserInfoDto userInfoDto) {
        this.userRepository.deleteById(userInfoDto.getId());
    }

    public Collection<UserInfoDto> getAllUsersInfo() {
        return userRepository.findAll().stream().map(this::mapToUserInfoDto).toList();
    }

    public UserInfoDto getUserInfo(String mailAddress) throws UserNotFoundException {
        User found = this.userRepository.findByEmail(mailAddress).orElseThrow(UserNotFoundException::new);
        return this.mapToUserInfoDto(found);
    }

    public UserInfoDto getUserInfo(UUID userId) throws UserNotFoundException {
        User found = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return this.mapToUserInfoDto(found);
    }

    public UserDto getUserProfile(UUID userId) throws UserNotFoundException {
        User found = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return this.mapToUserDto(found);
    }

    public UserDto getUserProfile(String mailAddress) throws UserNotFoundException {
        User found = this.userRepository.findByEmail(mailAddress).orElseThrow(UserNotFoundException::new);
        return this.mapToUserDto(found);
    }

    public UserInfoDto mapToUserInfoDto(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .sub(user.getSub())
                .familyName(user.getLastName())
                .givenName(user.getFirstName())
                .name(user.getFullName())
                .build();
    }

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .sub(user.getSub())
                .familyName(user.getLastName())
                .givenName(user.getFirstName())
                .name(user.getFullName())
                .role(user.getRole())
                .jobPosts(user.getJobPosts())
                .receivedReviews(user.getReceivedReviews())
                .writtenReviews(user.getWrittenReviews())
                .lastLoginDate(user.getLastLoginDate())
                .registrationDate(user.getRegistrationDate())
                .build();
    }

    public User mapToUser(UserInfoDto user) {
        return User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .sub(user.getSub())
                .lastName(user.getFamilyName())
                .firstName(user.getGivenName())
                .fullName(user.getName())
                .build();
    }
}
