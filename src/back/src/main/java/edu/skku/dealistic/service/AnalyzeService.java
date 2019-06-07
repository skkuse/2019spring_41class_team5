package edu.skku.dealistic.service;

import edu.skku.dealistic.model.RawReview;
import edu.skku.dealistic.model.Review;
import edu.skku.dealistic.nlp.Pipeline;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AnalyzeService {

    private final StanfordCoreNLP pipeline = Pipeline.getPipeline();

    private final ReviewService reviewService;

    public List<Review> analyzeReviews(List<RawReview> rawReviews) {
        return rawReviews
                .stream()
                //.parallel()
                .map(rawReview -> {
                    //Initializing the analysis document
                    CoreDocument coreDocument = new CoreDocument(rawReview.getContent());
                    pipeline.annotate(coreDocument);
                    List<CoreLabel> coreLabelList = coreDocument.tokens();
                    List<CoreSentence> sentences = coreDocument.sentences();

                    Review review = new Review();
                    review.setKeywords(coreLabelList);
                    review.setSentence(sentences);

                    return review;
                })
                .collect(Collectors.toList());
    }

/*    private Double analyzeSentiment(){
        return null;
    }

    private Double analyzeImportance(RawReview rawReview) {
        reviewService.getReviews(rawReview.getItemId());
    }*/
}

