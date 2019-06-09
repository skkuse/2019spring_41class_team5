package edu.skku.dealistic.service;

import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.model.RawReview;
import edu.skku.dealistic.model.Review;
import edu.skku.dealistic.model.Vendor;
import edu.skku.dealistic.persistence.ItemRepository;
import edu.skku.dealistic.persistence.VendorRepository;
import edu.stanford.nlp.simple.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnalyzeServiceTest {

    @Autowired
    private AnalyzeService analyzeService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private VendorRepository vendorRepository;

    private List<RawReview> rawReviews = new ArrayList<>();

    @Before
    @Transactional
    public void setUp() {
        Item item = itemRepository.findById(1).get();
        Vendor amazon = vendorRepository.findById(1).get();
        rawReviews.add(RawReview.builder()
                .item(item)
                .reference(amazon.referenece())
                .author("Junhyun Kim")
                .title("Title")
                .imageExists(true)
                .date(LocalDate.now())
                .rating(1.0)
                .content("I have had this computer for 4 days and it is driving me crazy. it takes over 10 seconds to pull up a web browser or any program. Instead of having a processor it feels like it runs on quicksand. Slowly I wait for programs to launch as I feel my life slipping away. I regret this purchase. I'm going to use this one to use and look at the pretty lights and I guess I will start using my old laptop again. I was so excited but now I'm completely disappointed.")
                .build());
    }

    @Test
    @Transactional
    public void testAnalyzeReviews() {

        List<Review> reviews = analyzeService.analyzeReviews(rawReviews);

        Assert.assertSame(reviews.size(), 1);

    }
}
