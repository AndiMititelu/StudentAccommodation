package com.project.studentAccommodation.repositories;

import com.project.studentAccommodation.models.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccommodationRepository extends JpaRepository<Accommodation, Integer> {
    //Optional<Accommodation> findBy(Integer integer);
}
