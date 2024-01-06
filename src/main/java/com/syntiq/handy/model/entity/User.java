package com.syntiq.handy.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syntiq.handy.model.UserRole;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User  implements Serializable {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;

    @Column(unique = true)
    private String sub;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @OneToMany(mappedBy = "reviewee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<Review> receivedReviews = ConcurrentHashMap.newKeySet();

    @OneToMany(mappedBy = "reviewer", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<Review> writtenReviews = ConcurrentHashMap.newKeySet();
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<JobPost> jobPosts = ConcurrentHashMap.newKeySet();

    public void addJobPost(JobPost... jobPost){
        if(this.jobPosts == null)
            this.jobPosts = ConcurrentHashMap.newKeySet();
        for (JobPost jp: jobPost) {
            this.jobPosts.add(jp);
            jp.setOwner(this);
        }
    }
}
