package edu.skku.dealistic.model;

import lombok.Builder;
import lombok.Data;

/**
 * Review Item before analyzing
 *
 * @author Junhyun Kim
 */
@Data
@Builder
public class RawReview {
    private Integer itemId;
    private String title;
    private String author;
    private String content;
    private Integer rating;
    private Boolean imageExists;
    private String date;
    private Reference reference;
}
