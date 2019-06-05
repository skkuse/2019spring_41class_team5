package edu.skku.dealistic.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerServiceTest {

    @Autowired
    private CrawlerService crawlerService;

    @Test
    public void testDriverPathGathering() {
        Assert.assertNotNull((getClass().getClassLoader().getResource("chromedriver").getPath()));
    }

    @Test
    public void testBestBuyConnection() {
        WebDriver driver = crawlerService.getDriver();
        driver.get("https://www.bestbuy.com/site/reviews/hp-omen-15-6-gaming-laptop-intel-core-i7-12gb-memory-nvidia-geforce-gtx-1050-ti-1tb-hard-drive-128gb-solid-state-drive-shadow-black/6263203?variant=null");
        Assert.assertNotNull(driver.getPageSource());
    }

    @Test
    public void testAmazonConnection() {
        WebDriver driver = crawlerService.getDriver();
        driver.get("https://www.bestbuy.com/site/reviews/hp-omen-15-6-gaming-laptop-intel-core-i7-12gb-memory-nvidia-geforce-gtx-1050-ti-1tb-hard-drive-128gb-solid-state-drive-shadow-black/6263203?variant=null");
        Assert.assertNotNull(driver.getPageSource());
    }

    @Test
    public void testDoCrawling() {

    }

}