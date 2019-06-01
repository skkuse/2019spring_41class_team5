package edu.skku.dealistic.task;

// This class should implement scheudling https://spring.io/guides/gs/scheduling-tasks/
//This class automatically crawls reviews and analyzes it and stores it in the data base

import edu.skku.dealistic.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//this will crawl data and analyze it and store to data base
@Component
public class ReviewCrawlingTask {

    @Autowired
    private CrawlerService crawlerService;

    @Scheduled(fixedRate = 10000)
    public void doCrawling() {
        //crawlerService.doCrawling();
    }
}
