package com.poseidon.services;

import com.poseidon.domain.Rating;
import com.poseidon.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(rating == null) throw new IllegalArgumentException("Rating Must Not Be Null");

        return ratingRepository.save(rating);
    }

    @Override
    public Rating findById(Integer id) {
        if(id == null) throw new IllegalArgumentException("Id Must Not Be Null");
        if(id < 0) throw new IllegalArgumentException("Id Must Be Greater Than Zero");

        return ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Rating Id:" + id));
    }

    @Override
    public Rating updateRatingById(Integer id, Rating rating) {
        if(id == null) throw new IllegalArgumentException("Id Must Not Be Null");
        if(id < 0) throw new IllegalArgumentException("Id Must Be Greater Than Zero");
        if(rating == null) throw new IllegalArgumentException(("Rating Must Not Be Null"));

        Rating ratingUpdated = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Rating Id:" + id));

        ratingUpdated.setMoodysRating(rating.getMoodysRating());
        ratingUpdated.setSandPRating(rating.getSandPRating());
        ratingUpdated.setFitchRating(rating.getFitchRating());
        ratingUpdated.setOrderNumber(rating.getOrderNumber());
        return ratingRepository.save(ratingUpdated);
    }

    @Override
    public void deleteRatingById(Integer id) {
        if (id == null) throw new IllegalArgumentException("Id Must Not Be Null");
        if (id < 0) throw new IllegalArgumentException("Id Must Be Greater Than Zero");

        ratingRepository.deleteById(id);
    }
}
