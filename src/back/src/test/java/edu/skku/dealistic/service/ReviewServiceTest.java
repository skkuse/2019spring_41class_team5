package edu.skku.dealistic.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewServiceTest extends ReviewService {

    @Test
    public void getReview1() {
        this.getReview("Dealistic");
    }
}