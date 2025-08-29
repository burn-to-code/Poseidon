package com.poseidon.repositories;

import com.poseidon.domain.Rating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Boolean existsByMoodysRating(@Size(max = 125) @NotBlank(message = "moodys is mandatory") String moodysRating);
}
