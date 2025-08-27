package com.poseidon.services;

import com.poseidon.domain.Rating;
import com.poseidon.repositories.RatingRepository;
import com.poseidon.services.abstracts.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class RatingCrudService extends AbstractCrudService<Rating> {


    public RatingCrudService(RatingRepository ratingRepository){
        super(ratingRepository);
    }

}
