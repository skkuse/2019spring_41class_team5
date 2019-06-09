package edu.skku.dealistic.controller;


import edu.skku.dealistic.exception.NotFoundException;
import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.model.Review;
import edu.skku.dealistic.model.User;
import edu.skku.dealistic.payload.Request;
import edu.skku.dealistic.persistence.ItemRepository;
import edu.skku.dealistic.persistence.ReviewRepository;
import edu.skku.dealistic.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    @GetMapping("/reviews")
    public List<Review> getReviews(
            @RequestParam(required = false) String authorId,
            @RequestParam(required = false) Integer itemId
    ) {
        Optional<User> author = authorId == null ? Optional.empty() : userRepository.findById(authorId);
        Optional<Item> item = itemId == null ? Optional.empty() : itemRepository.findById(itemId);

        if (author.isPresent() && item.isPresent())
            return List.of(reviewRepository.findReviewByAuthorAndItem(author.get(), item.get()));
        if (author.isPresent())
            return reviewRepository.findReviewsByAuthor(author.get());
        if (item.isPresent())
            return reviewRepository.findReviewsByItem(item.get());
        else return List.of();
    }

    @PostMapping("/reviews")
    public void createReview(@RequestBody Review review) {
        reviewRepository.save(review);
    }

    @GetMapping("/reviews/{id}")
    public Review getReview(@PathVariable Integer id) {
        return reviewRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @PatchMapping("/reviews/{id}")
    public void updateReview(@PathVariable Integer id, @RequestBody Review review) {
        Review originalReview = reviewRepository.findById(id).orElseThrow(NotFoundException::new);
        originalReview.setContent(review.getContent());

        reviewRepository.save(originalReview);
    }

    @DeleteMapping("/reviews/{id}")
    public void deleteReview(@PathVariable Integer id) {
        reviewRepository.deleteById(id);
    }
}
