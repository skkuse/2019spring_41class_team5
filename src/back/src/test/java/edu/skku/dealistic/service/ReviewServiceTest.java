package edu.skku.dealistic.service;

import edu.skku.dealistic.model.Review;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ReviewServiceTest extends ReviewService {

    @Before
    public void setUp() throws Exception {
        reviews = new ArrayList<>();

        //Once the crawler is made all data will be fetched from there
        Review r = new Review();
        r.setAuthor("Dealistic");
        r.setContent("I hate this product");
        r.setOriginalRating(3.5);

        reviews.add(r);
    }

    @Test
    public void getReviewTest() {
        this.getReviews("Dealistic");
    }
}