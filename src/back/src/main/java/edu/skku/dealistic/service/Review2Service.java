package edu.skku.dealistic.service;

import edu.skku.dealistic.persistence.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Review2Service {
    private final ReviewRepository reviewRepository;

}
