package edu.skku.dealistic.util;

import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.CoreSentence;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class SentenceUtil {

    public static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) {
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
        }

        return (longerLength - LevenshteinDistance.getDefaultInstance().apply(longer, shorter)) / (double) longerLength;
    }

    // min: 0.0, max: 5.0
    public static Double sentiment(CoreSentence sentence) {
        Double score = Integer.valueOf(RNNCoreAnnotations.getPredictedClass(sentence.sentimentTree())).doubleValue();

        return Double.min(score * 1.2, 5.0);
    }
}
