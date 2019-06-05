package edu.skku.dealistic.controller;


import edu.skku.dealistic.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import edu.skku.dealistic.service.ReviewService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {

    final
    ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getAll()
    {
        return reviewService.getAll();
    }

    @RequestMapping(value = "{author}", produces= MediaType.APPLICATION_JSON_VALUE)
    public Review getUser(@PathVariable("author") String author) {
        return reviewService.getReviews(author);
    }
}
