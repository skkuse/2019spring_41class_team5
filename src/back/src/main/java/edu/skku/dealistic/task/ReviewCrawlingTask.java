package edu.skku.dealistic.task;

// This class should implement scheudling https://spring.io/guides/gs/scheduling-tasks/
//This class automatically crawls reviews and analyzes it and stores it in the data base

import edu.skku.dealistic.model.*;
import edu.skku.dealistic.persistence.ItemRepository;
import edu.skku.dealistic.persistence.KeywordRepository;
import edu.skku.dealistic.persistence.ReviewRepository;
import edu.skku.dealistic.persistence.VendorLinkRepository;
import edu.skku.dealistic.service.AnalyzeService;
import edu.skku.dealistic.service.ReviewCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//this will crawl data and analyze it and store to data base
@Component
@RequiredArgsConstructor
public class ReviewCrawlingTask {

    private static final long MIN_CRAWLING_DURATION_SECONDS = 86400;

    private final ReviewCrawlerService reviewCrawlerService;

    private final AnalyzeService analyzeService;

    private final VendorLinkRepository vendorLinkRepository;

    private final ReviewRepository reviewRepository;

    private final ItemRepository itemRepository;

    private final KeywordRepository keywordRepository;


    //@Scheduled(fixedDelay = 100000)
    @Transactional
    public void doCrawling() {
        List<VendorLink> vendorLinks = vendorLinkRepository
                .findAll()
                .stream()
                .filter(this::checkCrawlingDuration)
                .collect(Collectors.toList());
        List<RawReview> rawReviews = reviewCrawlerService.doCrawlingReviews(vendorLinks);
        List<Review> reviews = analyzeService.analyzeReviews(rawReviews);

        updateReviews(reviews);
        updateVendorLinks(vendorLinks);

        List<Item> updatedItems = reviews
                .stream()
                .map(Review::getItem)
                .distinct()
                .collect(Collectors.toList());
        updateItems(updatedItems);
    }

    @Transactional
    public void updateReviews(List<Review> reviews) {
        reviewRepository.saveAll(reviews);
    }

    @Transactional
    public void updateVendorLinks(List<VendorLink> vendorLinks) {
        LocalDateTime now = LocalDateTime.now();

        vendorLinks.forEach(vendorLink -> vendorLink.setLastCrawledDateTime(now));
        vendorLinkRepository.saveAll(vendorLinks);
    }

    @Transactional
    public void updateItems(List<Item> updatedItems) {
        updatedItems
                .forEach(item -> {
                    List<Review> reviews = reviewRepository.findReviewsByItem(item);

                    // Update Item Rating
                    double importanceSum = reviews
                            .stream()
                            .mapToDouble(Review::getImportance)
                            .sum();
                    double weightedAverageRating = reviews
                            .stream()
                            .mapToDouble(review -> review.getImportance() * review.getRating() / importanceSum)
                            .sum();

                    item.setRating(weightedAverageRating);

                    // If item keywords are not exist
                    if (item.getKeywords() == null || item.getKeywords().isEmpty()) {
                        item.setKeywords(
                                keywordRepository
                                        .getKeywordsByCategory(item.getCategory())
                                        .stream()
                                        .map(keyword -> ItemKeyword.builder().item(item).keyword(keyword).build())
                                        .collect(Collectors.toList()));

                        itemRepository.save(item);
                    }

                    // Update Item keywords
                    item.getKeywords().forEach(iKeyword -> {
                        double keywordImportanceSum = reviews
                                .stream()
                                .filter(review -> review.getKeyword(iKeyword).getCount() != 0)
                                .mapToDouble(Review::getImportance)
                                .sum();
                        double keywordWeightedAverageRating = reviews
                                .stream()
                                .filter(review -> review.getKeyword(iKeyword).getCount() != 0)
                                .mapToDouble(review -> review.getImportance() * review.getKeyword(iKeyword).getRating() / keywordImportanceSum)
                                .sum();

                        long keywordCount = reviews
                                .stream()
                                .filter(review -> review.getKeyword(iKeyword).getCount() != 0)
                                .count();

                        iKeyword.setRating(keywordWeightedAverageRating);
                        iKeyword.setCount(keywordCount);
                    });
                });

        itemRepository.saveAll(updatedItems);
    }

    private boolean checkCrawlingDuration(VendorLink link) {
        if (link.getLastCrawledDateTime() == null) return true;
        return Duration.between(link.getLastCrawledDateTime(), LocalDateTime.now()).getSeconds() > MIN_CRAWLING_DURATION_SECONDS;
    }
}
