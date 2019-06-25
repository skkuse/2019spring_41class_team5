package edu.skku.dealistic.service;

import edu.skku.dealistic.model.Keyword;
import edu.skku.dealistic.model.RawReview;
import edu.skku.dealistic.model.Review;
import edu.skku.dealistic.model.ReviewKeyword;
import edu.skku.dealistic.nlp.Pipeline;
import edu.skku.dealistic.persistence.ReviewRepository;
import edu.skku.dealistic.util.SentenceUtil;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Analyzer Service.
 * Main Features:
 * - Analyze raw reviews, and return review list
 *
 * @author Muhammad Dahab Shakil, Junhyun Kim
 */
@RequiredArgsConstructor
@Service
public class AnalyzeService {

    private static final Double REVIEW_RATING_FEEDBACK_RATIO = 0.3;
    private static final Double REVIEW_IMPORTANCE_DEFAULT = 2.5;
    private static final Double REVIEW_IMPORTANCE_SIMILARITY_FEEDBACK_RATIO = 0.3;
    private static final Double REVIEW_IMPORTANCE_LENGTH_FEEDBACK_RATIO = 0.5;
    private static final Double REVIEW_IMPORTANCE_IMAGE_FEEDBACK_RATIO = 0.5;

    private final StanfordCoreNLP pipeline = Pipeline.getPipeline();

    private final ReviewRepository reviewRepository;

    public List<Review> analyzeReviews(List<RawReview> rawReviews) {
        return rawReviews
                .parallelStream()
                .map(rawReview -> {
                    CoreDocument annotatedReview = new CoreDocument(rawReview.getContent());
                    pipeline.annotate(annotatedReview);

                    Review review = Review.builder()
                            .item(rawReview.getItem())
                            .authorName(rawReview.getAuthor())
                            .content(rawReview.getContent())
                            .contentLength(rawReview.getContent().length())
                            .title(rawReview.getTitle())
                            .date(rawReview.getDate())
                            .reference(rawReview.getReference())
                            .originalRating(rawReview.getRating())
                            .build();

                    analyzeKeywordsAndRatings(review, annotatedReview);
                    analyzeImportance(review, rawReview, annotatedReview);

                    return review;
                })
                .collect(Collectors.toList());
    }

    private void analyzeImportance(Review review, RawReview rawReview, CoreDocument annotatedReview) {
        List<CoreSentence> sentences = annotatedReview.sentences();

        // Similarity Factor: If similar sentences are repeated, maybe it is spam.
        // Calculate sentence similarity between all sentences
        double similarityFactor = 0.0; // min: 0.0, max: 1.0
        for (int i = 0; i < sentences.size(); i++) {
            for (int j = 0; j < sentences.size(); j++) {
                if (i == j) continue;
                similarityFactor +=
                        SentenceUtil.similarity(sentences.get(i).text(), sentences.get(j).text())
                                / (sentences.size() * (sentences.size() - 1));
            }
        }

        // Image Factor: If image exists in original review, more important
        double imageFactor = rawReview.getImageExists() ? 1 : 0;

        // Length Factor: If the review is long enough, more important
        // Calculate ratio of review length compare to average length
        int reviewContentAverageLength = Optional.ofNullable(reviewRepository.getAverageContentLength()).orElse(1000);
        double lengthFactor = Double.max(-1, Double.min(1, Math.log10(review.getContentLength() / reviewContentAverageLength)));

        double importance = Double.min(
                5.0, REVIEW_IMPORTANCE_DEFAULT
                        * (1 - REVIEW_IMPORTANCE_SIMILARITY_FEEDBACK_RATIO * similarityFactor)
                        * (1 + REVIEW_IMPORTANCE_LENGTH_FEEDBACK_RATIO * lengthFactor)
                        * (1 + REVIEW_IMPORTANCE_IMAGE_FEEDBACK_RATIO * imageFactor)
        );

        review.setImportance(importance);
    }

    private void analyzeKeywordsAndRatings(Review review, CoreDocument annotatedReview) {
        List<CoreSentence> sentences = annotatedReview.sentences();
        List<Keyword> keywords = review.getItem().getCategory().getKeywords();

        // Initialize review keywords
        Map<Keyword, ReviewKeyword> reviewKeywordMap = new ConcurrentHashMap<>(); // THIS WAS THE PROBLEM! Changed to ConcurrentHashMap.
        keywords.forEach(keyword -> {
            ReviewKeyword reviewKeyword = new ReviewKeyword();
            reviewKeyword.setKeyword(keyword);
            reviewKeyword.setReview(review);

            reviewKeywordMap.put(keyword, reviewKeyword);
        });

        AtomicReference<Double> totalSentiment = new AtomicReference<>(0.0);
        sentences
                .forEach(sentence -> {
                    double sentiment = SentenceUtil.sentiment(sentence);
                    totalSentiment.updateAndGet(v -> v + sentiment);

                    // Update review keywords ratings (with sentence's sentiment)
                    sentence.tokens().stream()
                            // lemma: original form of a word, replace word to lemma for verbs
                            .map(word -> Optional.of(word.lemma()).orElse(word.value()))
                            // remove redundant words
                            .distinct()
                            // for each words, if the word is in keywords map, update keyword map.
                            .forEach(word ->
                                    keywords.stream()
                                            // find matching keyword (or synonyms) for word
                                            .filter(keyword -> keyword.contains(word))
                                            .findFirst()
                                            .ifPresent(keyword -> reviewKeywordMap.get(keyword).addRating(sentiment))
                            );
                });
        if (sentences.size() > 0)
            totalSentiment.updateAndGet(v -> v / sentences.size()); // calculate average of sentiment
        else totalSentiment.updateAndGet(v -> 2.5);
        // Mix with original rating, for correctness.
        review.setRating(
                review.getOriginalRating() * (1 - REVIEW_RATING_FEEDBACK_RATIO)
                        + totalSentiment.get() * REVIEW_RATING_FEEDBACK_RATIO
        );
        review.setKeywords(new ArrayList<>(reviewKeywordMap.values()));
    }
/*
    public Double analyzeImportance(RawReview review, List<CoreSentence> sentences) {
        Integer i,j,k = 0;
        Integer similarity=0;
        String[] str;
        str = review.getContent().split("\\n");

        Double importance = 0.0;
        i = str.length-1;
        for ( j = 0; j < i;j++ ){
            for(k = j+1; k<i; k++){
                if(str[j].equalsIgnoreCase(str[k])){
                    importance += 1.0;
                }
                else
                    importance += 5.0;
            }
        }

        if(review.getImageExists() ==true){
            importance += 5.0;
        }
        else
            importance += 1.0;


        Integer avgLength = avgLength();//인자로 item

        sentences.size()


        if()

        //전체 string문장이 반복되는 경우가 있는지 확인
    }*/

}
