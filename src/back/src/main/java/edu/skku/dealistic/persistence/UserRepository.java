package edu.skku.dealistic.persistence;

import edu.skku.dealistic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}