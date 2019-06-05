package edu.skku.dealistic.service;

import edu.skku.dealistic.model.RawReview;
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
public class AnalyzeService {

    public List<Review> analyzeReviews(List<RawReview> rawReviews) {

        List<Review> results = new ArrayList<>();

        for (RawReview review : rawReviews) {
            StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();

            //Initializing the analysis document
            CoreDocument coreDocument = new CoreDocument(review.getContent());
            stanfordCoreNLP.annotate(coreDocument);
            List<CoreLabel> coreLabelList = coreDocument.tokens();
            List<CoreSentence> sentences = coreDocument.sentences();

            Review result = new Review();
            result.setKeywords(coreLabelList);
            result.setSentence(sentences);

            results.add(result);
        }

        return results;
    }
}
