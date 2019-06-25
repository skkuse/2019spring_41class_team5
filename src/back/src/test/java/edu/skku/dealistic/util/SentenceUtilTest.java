package edu.skku.dealistic.util;

import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SentenceUtilTest {

    @Test
    public void similarity() {
        String s1 = "Our new mobile-friendly web app provides a simple beautiful emoji copy and paste interface WITH search and auto-copy technology.";
        String s2 = "Our new mobile-friendly web app provides a simple not beautiful emoji copy and paste interface WITH search and auto-copy technology.";

        Assert.assertTrue(SentenceUtil.similarity(s1, s2) > 0.9);
    }
}