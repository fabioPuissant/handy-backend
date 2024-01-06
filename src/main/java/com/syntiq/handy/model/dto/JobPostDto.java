package com.syntiq.handy.model.dto;

import com.syntiq.handy.model.entity.Bid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPostDto {
    private UUID id;
    private String title;
    private String description;
    private String imageUrl;
    private Set<Bid> bids;
    private LocalDateTime lastUpdated;
    private LocalDateTime endDate;
    private String email;
}
