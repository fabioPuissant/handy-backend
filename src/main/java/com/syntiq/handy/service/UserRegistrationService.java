package com.syntiq.handy.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syntiq.handy.exception.UserNotFoundException;
import com.syntiq.handy.model.dto.UserInfoDto;
import com.syntiq.handy.model.entity.User;
import com.syntiq.handy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class UserRegistrationService {
    private final UserRepository userRepository;
    @Autowired
    private UserService userService;
    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("${auth0.userinfo.endpoint}")
    private String userInfoEndpoint;

    public UserInfoDto registerUser(String tokenValue) {

        // Make a call to the user info endpoint
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userInfoEndpoint))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        UserInfoDto dto = null;
        UserInfoDto found = null;
        try {
            HttpResponse<String> rawResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = rawResponse.body();
            System.out.println("Body was: " + rawResponse.headers().toString());
            ObjectMapper objectMapper = new ObjectMapper();
            // Ignore the other properties that we did not configure in the UserInfoDto object but are received from the Issuer OPENID/Oauth
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            dto = objectMapper.readValue(body, UserInfoDto.class);

            // if no exception is thrown -> user exists already!
            found = this.userService.getUserInfo(dto.getEmail());


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Exception occurred while registering user", e);
        } catch (UserNotFoundException e) {
            if(dto != null) {
                User user = new User();
                user.setFirstName(dto.getGivenName());
                user.setLastName(dto.getFamilyName());
                user.setFullName(dto.getName());
                user.setEmail(dto.getEmail());
                user.setSub(dto.getSub());
                User saved = userRepository.save(user);
                return userService.mapToUserInfoDto(user);
            }
        }

        return found;
    }

}
