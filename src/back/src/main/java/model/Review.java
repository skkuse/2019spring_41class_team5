package model;

import edu.stanford.nlp.ling.CoreLabel;

import java.util.Collections;
import java.util.List;

public class Review {

    String author;
    String itemID;
    String reviewID;
    String content;
    Double original_rating;
    Double new_rating;
    String sentiment;
    String title;
    String date;
    String reference;
    String importance;
    List<CoreLabel>keywords;





/*
    class keyword{
        String keyword_data;
        String keyword_sentiment;
        String keyword_magnitude;
    }
*/
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getOriginal_rating() {
        return original_rating;
    }

    public void setOriginal_rating(Double original_rating) {
        this.original_rating = original_rating;
    }

    public Double getNew_rating() {
        return new_rating;
    }

    public void setNew_rating(Double new_rating) {
        this.new_rating = new_rating;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public List<CoreLabel> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<CoreLabel> coreLabelList)
    {
     //  Collections.copy(keywords, coreLabelList);  -> doesn't work
        for(CoreLabel coreLabel:coreLabelList)
        {
            System.out.println(coreLabel.originalText());

        }


    }
}
