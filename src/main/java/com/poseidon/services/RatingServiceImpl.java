package com.poseidon.services;

import com.poseidon.domain.Rating;
import com.poseidon.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService{

    private final RatingRepository ratingRepository;

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating findById(Integer id) {
        assertNotNull(id, "Id Must Not Be Null");
        return ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Rating Id:" + id));
    }

    @Override
    public Rating updateRatingById(Integer id, Rating rating) {
        assertNotNull(id, "Id Must Not Be Null:");
        assertNotNull(rating, "Rating Must Not Be Null:");

        return ratingRepository.save(rating);
    }

    @Override
    public void deleteRatingById(Integer id) {
        assertNotNull(id, "Id Must Not Be Null");

        ratingRepository.deleteById(id);
    }
}
