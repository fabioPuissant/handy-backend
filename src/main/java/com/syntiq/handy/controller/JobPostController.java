package com.syntiq.handy.controller;

import com.syntiq.handy.exception.JobPostNotFoundException;
import com.syntiq.handy.exception.UserNotFoundException;
import com.syntiq.handy.model.dto.JobPostDto;
import com.syntiq.handy.model.dto.SaveJobPostDto;
import com.syntiq.handy.service.JobPostService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@Controller
@RequestMapping("/api/jobpost")
public class JobPostController {
    @Autowired
    private JobPostService jobPostService;

    @PostMapping
    public ResponseEntity<Object> createJobPost(@RequestBody SaveJobPostDto dto) {
        try {
            var d = this.jobPostService.createJobPost(dto, dto.getEmail());
            return ResponseEntity.ok(d);

        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    @RequestMapping("/user")
    public ResponseEntity<Collection<JobPostDto>> getJobPostsOfUser(@RequestParam String email){
        Collection<JobPostDto> jobPosts = this.jobPostService.getJobPostsOfUser(email);
        return ResponseEntity.ok(jobPosts);
    }

    @PutMapping
    public HttpStatus updateJobPost(JobPostDto dto) {
        try {
            this.jobPostService.updateJobPost(dto);
            return HttpStatus.CREATED;
        } catch (UserNotFoundException | JobPostNotFoundException e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping
    public HttpStatus deleteJobPost(JobPostDto dto) {
        try {
            this.jobPostService.deleteJobPost(dto);
            return HttpStatus.OK;
        } catch (UserNotFoundException | JobPostNotFoundException e) {
            e.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        }
    }

    @GetMapping
    public ResponseEntity<Collection<JobPostDto>> getAllJobPosts() {
        Collection<JobPostDto> allJobPostsAsDto = this.jobPostService.getAll();
        return ResponseEntity.ok(allJobPostsAsDto);
    }

    @GetMapping("/tag")
    public ResponseEntity<Collection<JobPostDto>> getJobPostsByTag(@RequestParam String tag) throws ExecutionControl.NotImplementedException {
        Collection<JobPostDto> allJobPostsWithTag = this.jobPostService.getAllWithTag(tag);
        return ResponseEntity.ok(allJobPostsWithTag);
    }
}
