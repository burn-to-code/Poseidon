package com.poseidon.ratingtests;

import com.poseidon.domain.Rating;
import com.poseidon.repositories.RatingRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;

	@Test
	public void ratingTest() {
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Save
		rating = ratingRepository.save(rating);
		Assert.assertNotNull(rating.getId());
        Assert.assertEquals(Integer.valueOf(10), rating.getOrderNumber());

		// Update
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
        Assert.assertEquals(Integer.valueOf(20), rating.getOrderNumber());

		// Find
		List<Rating> listResult = ratingRepository.findAll();
        Assert.assertFalse(listResult.isEmpty());

		// Delete
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		Assert.assertFalse(ratingList.isPresent());
	}
}
