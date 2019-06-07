package edu.skku.dealistic.controller;


import edu.skku.dealistic.model.Review;
import edu.skku.dealistic.service.ReviewService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
@RolesAllowed({"USER", "MANAGER"})
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Review> getAll() {
        return reviewService.getAll();
    }

    @RequestMapping(value = "/{author}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Review getUser(@PathVariable("author") String author) {
        return reviewService.getReviews(author);
    }
}
