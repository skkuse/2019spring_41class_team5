package edu.skku.dealistic.model;

import edu.skku.dealistic.nlp.Pipeline;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Review {

    String author;
    String itemID;
    String reviewID;
    String content;
    Double originalRating;
    Double newRating;

    String title;
    String date;
    String reference;
    String importance;
    List<String> sentenceSentiments = new ArrayList<>(); //change this to hashmap<String,String> and store the sentence+sentiment
    Map<String, String> keywords = new HashMap<>();


/*
    class keyword{
        String keyword_data;
        String keyword_sentiment;
        String keyword_magnitude;
    }
*/

    public List<String> getSentence() {
        return sentenceSentiments;
    }

    public void setSentence(List<CoreSentence> sentences) {
        for (CoreSentence sentence : sentences) {
            String Test = sentence.sentiment();
            this.sentenceSentiments.add(Test);
        }
    }

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

    public Double getOriginalRating() {
        return originalRating;
    }

    public void setOriginalRating(Double originalRating) {
        this.originalRating = originalRating;
    }

    public Double getNewRating() {
        return newRating;
    }

    public void setNewRating(Double newRating) {
        this.newRating = newRating;
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

    public Map<String, String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<CoreLabel> coreLabelList) {
        for (CoreLabel coreLabel : coreLabelList) {
            //find setiment of the token/keyword //run analysis on the token
            String temp = coreLabel.originalText();
            //corelabel.originaltext()=keyword;
            StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
            //Initializing the analysis document
            CoreDocument coreDocument = new CoreDocument(temp);
            stanfordCoreNLP.annotate(coreDocument);
            List<CoreSentence> sentences = coreDocument.sentences();
            String keyword_sentiment = null;
            for (CoreSentence sentence : sentences) {
                keyword_sentiment = sentence.sentiment();
            }
        }
    }
}
