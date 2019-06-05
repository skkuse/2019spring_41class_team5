package edu.skku.dealistic.service;

import edu.skku.dealistic.model.Review;
import edu.skku.dealistic.nlp.Pipeline;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    protected List<Review> reviews; // replaces persistence layer (TODO change to real database)

    // we should return a java object
    // this function returns based on author's name
    // Junhyun: I think this should return list
    public Review getReviews(String author) {
        for (Review review : reviews) {

            if (review.getAuthor().equalsIgnoreCase(author)) {
                StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

                //Initializing the analysis document
                CoreDocument coreDocument = new CoreDocument(review.getContent());
                stanfordCoreNLP.annotate(coreDocument);
                List<CoreLabel> coreLabelList = coreDocument.tokens();
                List<CoreSentence> sentences = coreDocument.sentences();

                review.setKeywords(coreLabelList);
                review.setSentence(sentences);

               /* for(CoreSentence sentence:sentences)
                {
                    String Test=sentence.sentiment();
                    System.out.println(Test);
                }
*/

                return review;
            }
        }
        return null;
    }

    public List<Review> getAll() {
        return reviews;
    }
}
