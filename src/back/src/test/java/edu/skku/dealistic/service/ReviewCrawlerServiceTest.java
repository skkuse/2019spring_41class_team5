package edu.skku.dealistic.service;

import edu.skku.dealistic.model.RawReview;
import edu.skku.dealistic.persistence.VendorLinkRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewCrawlerServiceTest {

    @Autowired
    private ReviewCrawlerService reviewCrawlerService;

    @Autowired
    private VendorLinkRepository vendorLinkRepository;

    @Test
    public void testDriverPathGathering() {
        Assert.assertNotNull((getClass().getClassLoader().getResource("chromedriver").getPath()));
    }

    @Test
    public void testAmazonConnection() {
        WebDriver driver = reviewCrawlerService.getDriver();
        driver.get("https://www.amazon.com/product-reviews/B07CZCFHC6?reviewerType=all_reviews");
        Assert.assertNotNull(driver.getPageSource());
    }

    @Test
    public void testWalmartConnection() {
        WebDriver driver = reviewCrawlerService.getDriver();
        driver.get("https://www.walmart.com/reviews/product/802329710");
        Assert.assertNotNull(driver.getPageSource());
    }

    @Test
    @Transactional
    public void testAmazonCrawling() {
        List<RawReview> reviews = reviewCrawlerService.doCrawlingReviews(List.of(vendorLinkRepository.getOne(1)));
        Assert.assertNotEquals(reviews.size(), 0);
    }

    @Test
    @Transactional
    public void testWallmartCrawling() {
        List<RawReview> reviews = reviewCrawlerService.doCrawlingReviews(List.of(vendorLinkRepository.getOne(1)));
        Assert.assertNotEquals(reviews.size(), 0);
    }


}