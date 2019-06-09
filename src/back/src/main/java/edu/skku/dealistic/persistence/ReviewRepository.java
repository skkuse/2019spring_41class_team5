package edu.skku.dealistic.persistence;

import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.model.Review;
import edu.skku.dealistic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select avg(r.contentLength) from Review r")
    Integer getAverageContentLength();

    Review findReviewByAuthorAndItem(User user, Item item);

    List<Review> findReviewsByAuthor(User user);

    List<Review> findReviewsByItem(Item item);
}
