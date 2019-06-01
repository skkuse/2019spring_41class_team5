package edu.skku.dealistic.service;

import edu.skku.dealistic.exception.NoItemReferenceException;
import edu.skku.dealistic.model.ItemReference;
import edu.skku.dealistic.model.RawReview;
import edu.skku.dealistic.model.Reference;
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

    private static List<Crawler> crawlers;

    private static String driverPath;

    /**
     * Because the structure of website is different, it is necessary to implement crawling & parsing website differently.<br>
     * This abstract class already provides website connection by selenium.<br>
     * PLEASE INSTALL CORRECT VERSION OF BROWSER ON YOUR MACHINE BEFORE RUN. See /resources/README.md
     */
    private abstract class Crawler {
        protected Reference ref;

        public Crawler(Reference ref) {
            this.ref = ref;
        }

        public List<RawReview> doCrawling(ItemReference itemRef) throws NoItemReferenceException {
            String itemRefId = itemRef.getItemReferenceId(this.ref);

            if (itemRefId.isEmpty())
                throw new NoItemReferenceException(this.ref);

            driver.get(this.getReviewUrl(itemRefId));
            String pageSource = driver.getPageSource();
            if (pageSource.isEmpty())
                throw new RuntimeException(); // TODO Make more suitable exception

            return parse(pageSource);
        }

        public abstract String getReviewUrl(String itemRefId);

        public abstract List<RawReview> parse(String pageSource);
    }

    public CrawlerService() {
        // Driver Initialization
        driverPath = getClass().getClassLoader().getResource("chromedriver").getPath();
        System.setProperty("webdriver.chrome.driver", driverPath);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless", "window-size=1920x1080", "disable-gpu");
        driver = new ChromeDriver(options);

        // Crawler Setting
        crawlers = new ArrayList<>();
        crawlers.add(new Crawler(new Reference(1, "Amazon", "https://amazon.com")) {
            @Override
            public String getReviewUrl(String itemRefId) {
                return ref.getUrl() + "/product-reviews/" + itemRefId + "/reviewerType=all_reviews";
            }

            @Override
            public List<RawReview> parse(String pageSource) {
                System.out.println(pageSource); // TODO Implement parsing
                return new ArrayList<>();
            }
        });
        /*crawlers.add(new Crawler("https://ebay.com") {
            @Override
            public List<RawReview> doCrawling(ItemReference itemRef) {

            }
        });*/
        // TODO Implement another website, like eBay
    }

    /**
     * Do crawling. iterates with item list and website list.
     *
     * @param itemReferences Object that includes item id used in our system, and item id used in each websites.
     * @return
     */
    public List<RawReview> doCrawling(List<ItemReference> itemReferences) {
        List<RawReview> rawReviews = new ArrayList<>();
        for (Crawler crawler : crawlers) {
            for (ItemReference ref : itemReferences) {
                rawReviews.addAll(crawler.doCrawling(ref));
            }
        }

        return rawReviews;
    }
}

