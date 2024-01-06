package com.syntiq.handy.model.dto;

import com.syntiq.handy.model.entity.Bid;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class SaveJobPostDto {
    private UUID id;
    private String title;
    private String description;
    private String imageUrl;
    private LocalDateTime endDate;
    private String email;
}
