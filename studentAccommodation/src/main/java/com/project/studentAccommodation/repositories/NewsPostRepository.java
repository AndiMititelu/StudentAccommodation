package com.project.studentAccommodation.repositories;

import com.project.studentAccommodation.models.NewsPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsPostRepository extends JpaRepository<NewsPost, Long> {

    Optional<NewsPost> findById(Long id);
}
