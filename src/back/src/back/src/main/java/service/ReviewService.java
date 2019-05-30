package service;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import model.Review;
import nlp.Pipeline;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    ArrayList<Review> reviews = new ArrayList<Review>();
    //this is only for testing .. please move this part to "reviewCrawling_Analysis.java" class
    public ReviewService() {
        Review r = new Review();
        r.setAuthor("Dahab");
        r.setContent("I hate this product");
        r.setOriginal_rating(3.5);

        StanfordCoreNLP stanfordCoreNLP=Pipeline.getPipeline();
        //initializating the analysis document
        CoreDocument coreDocument= new CoreDocument(r.getContent());
        stanfordCoreNLP.annotate(coreDocument);
       // List<CoreLabel> coreLabelList=coreDocument.tokens();
        List<CoreSentence> sentences=coreDocument.sentences();
        //r.setKeywords(coreLabelList);

      /*  for(CoreLabel coreLabel:coreLabelList)
        {
            System.out.println(coreLabel.originalText());
        }*/
       for(CoreSentence sentence:sentences)
        {
            String Test=sentence.sentiment();
            System.out.println(Test);
        }



        reviews.add(r);

    }
    //get review by author -> should be changed

    //we should return a java object
    public Review getReview(String author) {
        for(Review review:reviews) {
            if(review.getAuthor().equalsIgnoreCase(author)) return review;
        }
        return null;
    }
    public ArrayList<Review> getAll() {
        return reviews;
    }
}
