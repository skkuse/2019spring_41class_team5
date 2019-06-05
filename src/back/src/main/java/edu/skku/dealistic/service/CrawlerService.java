package edu.skku.dealistic.service;

import edu.skku.dealistic.model.RawReview;
import edu.skku.dealistic.model.Vendor;
import edu.skku.dealistic.model.VendorLink;
import edu.skku.dealistic.task.ReviewCrawlingTask;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Crawling websites. called by {@link ReviewCrawlingTask#doCrawling()}.
 *
 * @author Junhyun Kim, Doyeong Yoo
 * @see ReviewCrawlingTask
 */
@Service
public class CrawlerService {

    private static WebDriver driver;

    private static List<Crawler> crawlers = new ArrayList<>();

    /**
     * Because the structure of website is different, it is necessary to implement crawling & parsing website differently.<br>
     * This abstract class already provides website connection by selenium.<br>
     * PLEASE INSTALL CORRECT VERSION OF BROWSER ON YOUR MACHINE BEFORE RUN. See /resources/README.md
     */
    abstract class Crawler {
        protected Vendor vendor;

        public Crawler(Vendor vendor) {
            this.vendor = vendor;
        }

        public List<RawReview> doCrawling(VendorLink link) {
            if (!link.getVendor().equals(this.vendor))
                throw new IllegalArgumentException("Vendor Type is incompatible");

            driver.get(link.getReviewDetailUrl());
            String pageSource = driver.getPageSource();
            if (pageSource.isEmpty())
                throw new RuntimeException(); // TODO Make more suitable exception

            return parse(pageSource);
        }

        public Boolean isCompatible(VendorLink link) {
            return this.vendor.equals(link.getVendor());
        }

        public abstract List<RawReview> parse(String pageSource);
    }

    public CrawlerService() {
        initializeDriver();
        initializeCrawlers();
    }

    private void initializeCrawlers() {
        Vendor amazon = Vendor.builder()
                .id(1)
                .name("Amazon")
                .url("https://amazon.com")
                .itemDetailPrefix("/dp/")
                .reviewDetailPrefix("/product-reviews/")
                .reviewDetailPostfix("reviewerType=all_reviews&sortBy=recent")
                .pageParam("pageNumber")
                .build();

        Vendor bestbuy = Vendor.builder()
                .id(2)
                .name("Bestbuy")
                .url("https://bestbuy.com")
                .itemDetailPrefix("")
                .reviewDetailPrefix("")
                .reviewDetailPostfix("")
                .pageParam("")
                .build();

        crawlers.add(new Crawler(amazon) {
            @Override
            public List<RawReview> parse(String pageSource) {
                return null;
            }
        });

        crawlers.add(new Crawler(bestbuy) {
            @Override
            public List<RawReview> parse(String pageSource) {
                return null;
            }
        });
    }

    private void initializeDriver() {
        System.setProperty("webdriver.chrome.driver",
                getClass()
                        .getClassLoader()
                        .getResource("chromedriver")
                        .getPath());

        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
        options.setHeadless(true);

        driver = new ChromeDriver(options);
    }

    /**
     * Do crawling. iterates with item list and website list.
     *
     * @param vendorLinks Object that includes item id used in our system,
     *                    and item id used in each websites.
     * @return
     */
    public List<RawReview> doCrawling(List<VendorLink> vendorLinks) {
        List<RawReview> rawReviews = new ArrayList<>();

        vendorLinks.forEach(link -> crawlers.forEach(crawler -> {
            if (crawler.isCompatible(link)) rawReviews.addAll(crawler.doCrawling(link));
        }));

        return rawReviews;
    }

    public static WebDriver getDriver() {
        return driver;
    }
}

