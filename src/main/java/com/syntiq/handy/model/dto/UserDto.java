package com.syntiq.handy.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.syntiq.handy.model.UserRole;
import com.syntiq.handy.model.entity.JobPost;
import com.syntiq.handy.model.entity.Review;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Builder
public class UserDto {

    private UUID id;
    @JsonProperty("sub")
    private String sub;


    @JsonProperty("picture")
    private String picture;
    @JsonProperty("email")
    private String email;


    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("name")
    private String name;

    private UserRole role;

    private LocalDateTime registrationDate;

    private LocalDateTime lastLoginDate;

    private Set<Review> receivedReviews = ConcurrentHashMap.newKeySet();

    private Set<Review> writtenReviews = ConcurrentHashMap.newKeySet();
    private Set<JobPost> jobPosts = ConcurrentHashMap.newKeySet();
}
