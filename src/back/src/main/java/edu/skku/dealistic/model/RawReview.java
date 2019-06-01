package edu.skku.dealistic.model;

/**
 * Review Item before analyzing
 *
 * @author Junhyun Kim
 */
public class RawReview {
    private Integer itemId;
    private String title;
    private String author;
    private String content;
    private Integer rating;
    private Boolean imageExists;
    private String date;
    private Reference reference;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getImageExists() {
        return imageExists;
    }

    public void setImageExists(Boolean imageExists) {
        this.imageExists = imageExists;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }
}
