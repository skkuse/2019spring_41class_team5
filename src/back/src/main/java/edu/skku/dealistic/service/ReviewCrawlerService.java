package edu.skku.dealistic.service;

import edu.skku.dealistic.exception.NotFoundException;
import edu.skku.dealistic.model.RawReview;
import edu.skku.dealistic.model.Vendor;
import edu.skku.dealistic.model.VendorLink;
import edu.skku.dealistic.persistence.VendorRepository;
import edu.skku.dealistic.task.ReviewCrawlingTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Crawling websites. called by {@link ReviewCrawlingTask#doCrawling()}.
 *
 * @author Junhyun Kim, Doyeong Yoo
 * @see ReviewCrawlingTask
 */
@Service
public class ReviewCrawlerService {

    private final WebDriver driver;

    private final List<Crawler> crawlers;

    private final VendorRepository vendorRepository;

    private final String os;
    private final Boolean enableCrawler;
    private final Boolean headless;
    private final Integer maxPage;

    public ReviewCrawlerService(VendorRepository vendorRepository,
                                @Value("${environment.os}") String os,
                                @Value("${debug.crawler.enable}") Boolean enableCrawler,
                                @Value("${debug.crawler.headless}") Boolean headless,
                                @Value("${debug.crawler.maxpage}") Integer maxPage) {
        this.vendorRepository = vendorRepository;
        this.os = os;
        this.enableCrawler = enableCrawler;
        this.headless = headless;
        this.maxPage = maxPage;

        if (enableCrawler) {
            driver = initializeDriver();
            crawlers = initializeCrawlers();
        } else {
            driver = null;
            crawlers = null;
        }
    }

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

            List<RawReview> rawReviews = new ArrayList<>();
            synchronized (driver) {
                driver.get(link.getReviewDetailUrl());
                Integer pageSize = Integer.min(this.getPageSize(), maxPage); // limit review size
                int currentPage = 1;
                while (currentPage++ < pageSize) {
                    try {
                        rawReviews.addAll(parse(link, driver.getPageSource()));
                        goNextPage();
                    } catch (Exception e) {
                        break;
                    }
                }

                rawReviews.addAll(parse(link, driver.getPageSource()));
            }

            return rawReviews;
        }

        public Boolean isCompatible(VendorLink link) {
            return this.vendor.equals(link.getVendor());
        }

        public abstract void goNextPage();

        public abstract Integer getPageSize();

        public abstract List<RawReview> parse(VendorLink link, String pageSource);
    }

    private List<Crawler> initializeCrawlers() {
        List<Vendor> vendors = vendorRepository.findAll();
        Vendor amazon = vendors
                .stream()
                .filter(vendor -> vendor.getName().equals("Amazon"))
                .findFirst()
                .orElseThrow(NotFoundException::new);
        Vendor walmart = vendors
                .stream()
                .filter(vendor -> vendor.getName().equals("Walmart"))
                .findFirst()
                .orElseThrow(NotFoundException::new);

        List<Crawler> result = new ArrayList<>();
        result.add(new Crawler(amazon) {
            @Override
            public Integer getPageSize() {
                String reviewSizeString = driver.findElement(By.cssSelector("#filter-info-section span")).getText();
                Integer reviewSize = Integer.parseInt(reviewSizeString.split(" ")[3]);
                return (reviewSize / 10) + 1;
            }

            @Override
            public void goNextPage() {
                WebElement nextPageButton = new WebDriverWait(driver, 10).until(
                        ExpectedConditions.elementToBeClickable(By.cssSelector(".a-pagination .a-last a")));

                nextPageButton.click();
                new WebDriverWait(driver, 10).until(
                        ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".reviews-loading")));
            }

            @Override
            public List<RawReview> parse(VendorLink link, String pageSource) {
                Document doc = Jsoup.parse(pageSource);
                return doc
                        .select(".review")
                        .stream()
                        .map(element -> RawReview.builder()
                                .item(link.getItem())
                                .reference(link.getVendor().referenece())
                                .title(element.select(".review-title-content span").text())
                                .author(element.select(".a-profile-name").text())
                                .content(element.select(".review-text-content span").text())
                                .date(LocalDate.parse(
                                        element.select(".review-date").text().replaceAll("^.+on ", ""),
                                        DateTimeFormatter
                                                .ofLocalizedDate(FormatStyle.LONG)
                                                .withLocale(Locale.US)))
                                .rating(Double.parseDouble(element.select(".review-rating span").text().substring(0, 3)))
                                .imageExists(!element.select(".review-image-tile").isEmpty())
                                .build())
                        .collect(Collectors.toList());
            }
        });

        result.add(new Crawler(walmart) {
            @Override
            public void goNextPage() {
                WebElement pagination = new WebDriverWait(driver, 10).until(
                        ExpectedConditions.presenceOfElementLocated(By.cssSelector(".paginator")));

                pagination.findElement(By.cssSelector(".paginator-btn-next")).click();
                driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            }

            @Override
            public Integer getPageSize() {
                WebElement pagination = new WebDriverWait(driver, 10).until(
                        ExpectedConditions.presenceOfElementLocated(By.cssSelector(".paginator-list")));

                String pageString = pagination
                        .findElement(By.cssSelector("li.active button"))
                        .getAttribute("aria-label");

                return Integer.parseInt(pageString.split(" of ")[1].split(" ")[0]);
            }

            @Override
            public List<RawReview> parse(VendorLink link, String pageSource) {
                Document doc = Jsoup.parse(pageSource);
                return doc
                        .select(".review")
                        .stream()
                        .map(element -> RawReview.builder()
                                .item(link.getItem())
                                .reference(link.getVendor().referenece())
                                .title(element.select(".review-title").text())
                                .author(element.select(".review-footer-userNickname").text())
                                .content(element.select(".review-body-text > div > div").first().text())
                                .date(LocalDate.parse(element.select(".review-footer-submissionTime").text(),
                                        DateTimeFormatter
                                                .ofLocalizedDate(FormatStyle.LONG)
                                                .withLocale(Locale.US)))
                                .rating(Double.parseDouble(element.select(".stars-container").attr("aria-label").substring(0, 1)))
                                .imageExists(!element.select(".review-media-thumbnail").isEmpty())
                                .build())
                        .collect(Collectors.toList());
            }
        });
        return result;
    }

    private ChromeDriver initializeDriver() {
        String driverFile = os.equals("windows") ? "chromedriver.exe" : "chromedriver";
        System.setProperty("webdriver.chrome.driver",
                Objects.requireNonNull(getClass()
                        .getClassLoader()
                        .getResource(driverFile), "Invalid chromedriver path")
                        .getPath());

        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
        if (headless)
            options.setHeadless(true);

        return new ChromeDriver(options);
    }

    /**
     * Do crawling. iterates with item list and website list.
     *
     * @param vendorLinks Object that includes item id used in our system,
     *                    and item id used in each websites.
     * @return
     */
    public synchronized List<RawReview> doCrawlingReviews(List<VendorLink> vendorLinks) {
        List<RawReview> rawReviews = new ArrayList<>();

        if (enableCrawler)
            vendorLinks.parallelStream().forEach(link -> crawlers.forEach(crawler -> {
                if (crawler.isCompatible(link)) rawReviews.addAll(crawler.doCrawling(link));
            }));

        return rawReviews;
    }

    /* Only On Test TODO Remove these on production */

    public WebDriver getDriver() {
        return driver;
    }
}

