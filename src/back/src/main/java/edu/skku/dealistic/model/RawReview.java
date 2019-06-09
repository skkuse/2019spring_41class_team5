package edu.skku.dealistic.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Review Item before analyzing
 *
 * @author Junhyun Kim
 */
@Data
@Builder
public class RawReview {
    private Item item;
    private String title;
    private String author;
    private String content;
    private Double rating;
    private Boolean imageExists;
    private LocalDate date;
    private Reference reference;
}
