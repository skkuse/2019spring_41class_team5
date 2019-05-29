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
        //this should be crawled from web
        r.setAuthor("Dahab");
        r.setContent("I love this product so much!");
        r.setRating(3.5);
        //do tokenization and analysis here -> set the sentiment



        reviews.add(r);


    }
    //get review by author -> should be changed

    //we should return a java object
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
