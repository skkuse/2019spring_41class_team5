package model;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import nlp.Pipeline;

import java.util.*;

public class Review {

    String author;
    String itemID;
    String reviewID;
    String content;
    Double original_rating;
    Double new_rating;

    String title;
    String date;
    String reference;
    String importance;
    List<String> sentences_sentiment=new ArrayList<>(); //change this to hashmap<String,String> and store the sentence+sentiment
   // List<String> keywords=new ArrayList<>();  //you
    Map<String, String> keywords = new HashMap<>();


/*
    class keyword{
        String keyword_data;
        String keyword_sentiment;
        String keyword_magnitude;
    }
*/

    public List<String> getSentence() {
        return sentences_sentiment;
    }

    public void setSentence(List<CoreSentence> sentences)
    {
        for(CoreSentence sentence:sentences)
        {
            String Test=sentence.sentiment();
            this.sentences_sentiment.add(Test);
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

    public Map<String,String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<CoreLabel> coreLabelList)
    {
         for (CoreLabel coreLabel : coreLabelList)
         {
             //find setiment of the token/keyword //run analysis on the token
             String temp=coreLabel.originalText();
             //corelabel.originaltext()=keyword;
             StanfordCoreNLP stanfordCoreNLP= Pipeline.getPipeline();
             //Initializing the analysis document
             CoreDocument coreDocument= new CoreDocument(temp);
             stanfordCoreNLP.annotate(coreDocument);
             List<CoreSentence> sentences=coreDocument.sentences();
             String keyword_sentiment = null;
             for(CoreSentence sentence:sentences)
             {
                  keyword_sentiment=sentence.sentiment();
             }

                this.keywords.put(temp,keyword_sentiment);
         }
    }
}
