package com.syntiq.handy.repository;

import com.syntiq.handy.model.entity.JobPost;
import com.syntiq.handy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, UUID> {
    Optional<List<JobPost>> findByOwner(User user);
}
