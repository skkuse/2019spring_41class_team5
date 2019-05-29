package service;

import model.Review;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReviewService {
    ArrayList<Review> reviews = new ArrayList<Review>();
    //this is only for testing .. please connect with review crawler and data base
    public ReviewService() {
        Review r = new Review();
        r.setAuthor("Dahab");
        r.setContent("I love this product so much!");
        r.setRating(3.5);

        reviews.add(r);


    }
    //get review by author -> should be changed
    public Review getReview(String author) {
        for(Review review:reviews) {
            if(review.getAuthor().equalsIgnoreCase(author)) return review;
        }
        return null;
    }
    public ArrayList<Review> getAll() {
        return reviews;
    }
}
