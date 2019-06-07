package edu.skku.dealistic.persistence;

import edu.skku.dealistic.model.Review2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review2, Integer> {
}
