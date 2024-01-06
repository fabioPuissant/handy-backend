package com.syntiq.handy.service;

import com.syntiq.handy.exception.UserNotFoundException;
import com.syntiq.handy.model.dto.JobPostDto;
import com.syntiq.handy.model.dto.SaveJobPostDto;
import com.syntiq.handy.model.entity.JobPost;
import com.syntiq.handy.model.entity.User;
import com.syntiq.handy.repository.JobPostRepository;
import com.syntiq.handy.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.assertj.core.api.Assertions;
import java.util.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class JobPostServiceTest {
    @Mock
    private UserRepository _userRepository;
    @Mock
    private JobPostRepository _jobPostRepository;
    @InjectMocks
    private JobPostService _jobPostService;


    @Test
    public void getAll_shouldReturn_collectionOfJobPosts(){
        // Arrange
        User someUser = User.builder().build();
        JobPost jp1 = JobPost.builder().title("JobPost 1").owner(someUser).build();
        JobPost jp2 = JobPost.builder().title("JobPost 2").owner(someUser).build();
        JobPost jp3 = JobPost.builder().title("JobPost 3").owner(someUser).build();
        List<JobPost> found = Arrays.asList(jp1, jp2, jp3);
        when(_jobPostRepository.findAll()).thenReturn(found);

        // Act
        Collection<JobPostDto> jobPostDtos = _jobPostService.getAll();


        // Assert
        Assertions.assertThat(jobPostDtos).isNotEmpty();
        Assertions.assertThat(jobPostDtos.size()).isEqualTo(found.size());
    }

    @Test
    public void createJobPost_shouldCreateNewJobPost_whenUserEmailIsFound() throws UserNotFoundException {
        // Arrange
        User mockUser = User.builder().id(UUID.randomUUID()).email("mock.user@handi.com").build();
        when(_userRepository.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(mockUser));

        JobPost mockJobPost= JobPost.builder().id(UUID.randomUUID()).title("Jobpost 1").owner(mockUser).build();
        when(_jobPostRepository.save(any(JobPost.class))).thenReturn(mockJobPost);

        SaveJobPostDto saveJobPostDtoMock = SaveJobPostDto.builder().build();

        // Act
        JobPostDto jobPostDto = _jobPostService.createJobPost(saveJobPostDtoMock, mockUser.getEmail());

        // Assert
        Assert.assertNotNull(jobPostDto);
        Assertions.assertThat(jobPostDto.getTitle()).isEqualTo(mockJobPost.getTitle());
        Assertions.assertThat(jobPostDto.getEmail()).isEqualTo(mockJobPost.getOwner().getEmail());
    }

}
