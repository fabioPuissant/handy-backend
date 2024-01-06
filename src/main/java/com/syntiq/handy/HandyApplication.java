package com.syntiq.handy;

import com.syntiq.handy.model.UserRole;
import com.syntiq.handy.model.entity.JobPost;
import com.syntiq.handy.model.entity.User;
import com.syntiq.handy.repository.JobPostRepository;
import com.syntiq.handy.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@SpringBootApplication
public class HandyApplication {
	public static void main(String[] args) {
		SpringApplication.run(HandyApplication.class, args);
	}

	/**
	 * This method seeds the database with data
	 * @return CommandLineRunner
	 */
	@Bean
	public static CommandLineRunner run (UserRepository userRepo,
						   JobPostRepository jobPostRepo) {
		List<User> startUsers = createStartUsers();
		List<JobPost> startJobPosts = createStartJobPosts(startUsers.stream().limit(3).toList());

		return args -> {
			startUsers.get(0).addJobPost(startJobPosts.get(0), startJobPosts.get(1));
			userRepo.saveAllAndFlush(startUsers);
			startJobPosts.get(2).setOwner(startUsers.get(1));
			jobPostRepo.saveAllAndFlush(startJobPosts);
		};
	}

	private static List<JobPost> createStartJobPosts(List<User> list) {
		return Arrays.asList(
				JobPost.builder()
						.title("Leaky fosset")
						.description("I have a fosset that drips and makes a terrible sound. I am looking for a plumber to fix it!")
						.imageUrl("https://someImageUrl.com/img1.png")
						.lastUpdated(LocalDateTime.now().minusDays(14))
						.endDate(LocalDateTime.now().plusWeeks(2))
						.bids(ConcurrentHashMap.newKeySet())
						.build(),
				JobPost.builder()
						.title("Broken window")
						.description("I have a broken window that needs to be repaired ASAP! I will provide the window")
						.imageUrl("https://someImageUrl.com/img2.png")
						.lastUpdated(LocalDateTime.now().minusDays(2))
						.endDate(LocalDateTime.now().plusWeeks(1))
						.bids(ConcurrentHashMap.newKeySet())
						.build(),
				JobPost.builder()
						.title("Koi Pond")
						.description("I always wanted a Koi Pond in my backyard. I am looking for a handy man to dig, build, install, and maintain my koi pond")
						.imageUrl("https://someImageUrl.com/img3.png")
						.lastUpdated(LocalDateTime.now().minusDays(6))
						.endDate(LocalDateTime.now().plusWeeks(6))
						.bids(ConcurrentHashMap.newKeySet())
						.build()
		);
	}

	private static List<User> createStartUsers() {

		return Arrays.asList(
				User.builder()
						.fullName("John Doe")
						.firstName("John")
						.sub(UUID.randomUUID().toString())
						.lastName("Doe")
						.email("john.doe@contoso.com")
						.role(UserRole.PROFESSIONAL)
						.lastLoginDate(LocalDateTime.now())
						.registrationDate(LocalDateTime.now().minusMonths(2).minusDays(4))
						.writtenReviews(ConcurrentHashMap.newKeySet())
						.receivedReviews(ConcurrentHashMap.newKeySet())
						.build(),
				User.builder()
						.fullName("Jane Doe")
						.firstName("Jane")
						.sub(UUID.randomUUID().toString())
						.lastName("Doe")
						.email("jane.doe@gmail.com")
						.role(UserRole.CUSTOMER)
						.lastLoginDate(LocalDateTime.now())
						.registrationDate(LocalDateTime.now().minusMonths(9).minusDays(1))
						.writtenReviews(ConcurrentHashMap.newKeySet())
						.receivedReviews(ConcurrentHashMap.newKeySet())
						.build(),
				User.builder()
						.fullName("Fabio Noah")
						.firstName("Fabio")
						.sub(UUID.randomUUID().toString())
						.lastName("Noah")
						.email("admin@handy.com")
						.role(UserRole.ADMINISTRATOR)
						.lastLoginDate(LocalDateTime.now())
						.registrationDate(LocalDateTime.now().minusMonths(2).minusDays(4))
						.writtenReviews(ConcurrentHashMap.newKeySet())
						.receivedReviews(ConcurrentHashMap.newKeySet())
						.build()
		);
	}
}
