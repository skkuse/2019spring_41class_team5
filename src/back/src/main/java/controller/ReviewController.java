package controller;


import model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ReviewService;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    ReviewService ps;

    @RequestMapping(value="/all", produces= MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Review> getAll()
    {
        return ps.getAll();
    }

    @RequestMapping(value = "{author}", produces= MediaType.APPLICATION_JSON_VALUE)
    public Review getUser(@PathVariable("author") String author) {
        return ps.getReview(author);
    }
}
