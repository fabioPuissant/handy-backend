package com.syntiq.handy.service;

import com.syntiq.handy.exception.JobPostNotFoundException;
import com.syntiq.handy.exception.UserNotFoundException;
import com.syntiq.handy.model.dto.JobPostDto;
import com.syntiq.handy.model.dto.SaveJobPostDto;
import com.syntiq.handy.model.entity.JobPost;
import com.syntiq.handy.model.entity.User;
import com.syntiq.handy.repository.JobPostRepository;
import com.syntiq.handy.repository.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class JobPostService {
    private final UserRepository userRepository;
    private final JobPostRepository jobPostRepository;

    public JobPostDto createJobPost(SaveJobPostDto dto, String email) throws UserNotFoundException {
        User owner = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        JobPost newJobPost = JobPost.builder()
                .bids(ConcurrentHashMap.newKeySet())
                .owner(owner)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .lastUpdated(LocalDateTime.now())
                .imageUrl(dto.getImageUrl())
                .endDate(dto.getEndDate())
                .build();

       var saved = jobPostRepository.save(newJobPost);
       return JobPostDto.builder()
               .id(saved.getId())
               .title(saved.getTitle())
               .description(saved.getDescription())
               .email(saved.getOwner().getEmail())
               .lastUpdated(saved.getLastUpdated())
               .endDate(saved.getEndDate())
               .imageUrl(saved.getImageUrl())
               .bids(saved.getBids())
               .build();
    }

    public Collection<JobPostDto> getJobPostsOfUser(String email) {
        return jobPostRepository.findAll().stream().filter(jp -> jp.getOwner().getEmail().equals(email))
                .map(jobPost -> JobPostDto.builder()
                        .bids(jobPost.getBids())
                        .title(jobPost.getTitle())
                        .imageUrl(jobPost.getImageUrl())
                        .description(jobPost.getDescription())
                        .id(jobPost.getId())
                        .email(jobPost.getOwner().getEmail())
                        .lastUpdated(jobPost.getLastUpdated())
                        .endDate(jobPost.getEndDate())
                        .build())
                .toList();
    }

    /**
     * Updates description, lastUpdated, imageUrl, endDate
     *
     * @param dto
     * @throws UserNotFoundException
     */
    public JobPostDto updateJobPost(JobPostDto dto) throws UserNotFoundException, JobPostNotFoundException {
        // See if user exists
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(UserNotFoundException::new);

        // See If Found User is the owner
        this.isOwnerOfJobPostOrElseThrow(user, dto);

        // Map to jobPost and thereby update to allowed values
        JobPost jobPost = JobPost.builder()
                .owner(user)
                .id(dto.getId())
                .lastUpdated(LocalDateTime.now())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .endDate(dto.getEndDate())
                .build();

        // Update found
        JobPost updated = jobPostRepository.save(jobPost);
        return JobPostDto.builder()
                .id(updated.getId())
                .title(updated.getTitle())
                .description(updated.getDescription())
                .email(user.getEmail())
                .imageUrl(updated.getImageUrl())
                .lastUpdated(updated.getLastUpdated())
                .endDate(updated.getEndDate())
                .bids(updated.getBids())
                .build();
    }

    public void deleteJobPost(JobPostDto dto) throws UserNotFoundException, JobPostNotFoundException {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(UserNotFoundException::new);
        isOwnerOfJobPostOrElseThrow(user, dto);
    }

    public boolean isOwnerOfJobPostOrElseThrow(User user, JobPostDto dto) throws JobPostNotFoundException {
        this.getJobPostsOfUser(user.getEmail())
                .stream().filter(s -> s.getTitle().equals(dto.getTitle()))
                .findAny()
                .orElseThrow(JobPostNotFoundException::new);

        return true;
    }

    public Collection<JobPostDto> getAll() {
        return this.jobPostRepository.findAll().stream().map(
                jp -> JobPostDto.builder()
                        .id(jp.getId())
                        .title(jp.getTitle())
                        .bids(jp.getBids())
                        .email(jp.getOwner().getEmail())
                        .imageUrl(jp.getImageUrl())
                        .endDate(jp.getEndDate())
                        .lastUpdated(jp.getLastUpdated())
                        .description(jp.getDescription())
                        .build()
        ).toList();
    }

    public Collection<JobPostDto> getAllWithTag(String tag) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Not yet implemented");
    }
}
