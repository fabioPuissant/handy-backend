package com.syntiq.handy.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobPost implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String description;
    private String imageUrl;
    @OneToMany
    @JoinColumn(name = "job_post")
    private Set<Bid> bids;
    private LocalDateTime lastUpdated;
    private LocalDateTime endDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;
    @OneToMany
    private Collection<Tag> tags = new ArrayList<>();
}
