package com.poseidon.services;

import com.poseidon.domain.Rating;

import java.util.List;

public interface RatingService {

    List<Rating> findAll();

    Rating saveRating(Rating rating);

    Rating findById(Integer id);

    Rating updateRatingById(Integer id, Rating rating);

    void deleteRatingById(Integer id);
}
