package com.syntiq.handy.model.entity;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "reviewee_id")
    private User reviewee;
    @OneToOne
    @NotNull
    @JoinColumn(name = "jobpost_id")
    private JobPost jobPost;
    private String title;
    private String description;

    private double rating;
    private LocalDateTime lastUpdated;

}
