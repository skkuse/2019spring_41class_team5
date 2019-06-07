package edu.skku.dealistic.task;

// This class should implement scheudling https://spring.io/guides/gs/scheduling-tasks/
//This class automatically crawls reviews and analyzes it and stores it in the data base

import edu.skku.dealistic.model.RawReview;
import edu.skku.dealistic.model.Review;
import edu.skku.dealistic.model.VendorLink;
import edu.skku.dealistic.persistence.VendorLinkRepository;
import edu.skku.dealistic.service.AnalyzeService;
import edu.skku.dealistic.service.CrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

//this will crawl data and analyze it and store to data base
@Component
@RequiredArgsConstructor
public class ReviewCrawlingTask {

    private final CrawlerService crawlerService;

    private final AnalyzeService analyzeService;

    private final VendorLinkRepository vendorLinkRepository;

    //private final ReviewRepository reviewRepository;

    @Scheduled(fixedRate = 100000)
    @Transactional
    public void doCrawling() {
        List<VendorLink> vendorLinks = vendorLinkRepository.findAll();
        List<RawReview> rawReviews = crawlerService.doCrawling(vendorLinks);
        List<Review> reviews = analyzeService.analyzeReviews(rawReviews);

        //reviewRepository.saveAll(reviews);
    }

    @Transactional
    public void updateDatabase(List<Review> reviews) {
        
    }
}
