package edu.skku.dealistic.service;

import edu.skku.dealistic.model.ItemReference;
import edu.skku.dealistic.model.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerServiceTest {

    @Autowired
    private CrawlerService crawlerService;

    @Test
    public void doCrawling() {
        List<ItemReference> itemReferences = new ArrayList<>();
        Map<Reference, String> refMap = new HashMap<>();
        refMap.put(new Reference(1, "Amazon", "https://amazon.com"), "B07CZCFGX8");

        itemReferences.add(new ItemReference(1, refMap));
        crawlerService.doCrawling(itemReferences);
    }

    @Test
    public void getPath() {
        System.out.println(getClass().getClassLoader().getResource("chromedriver").getPath());
    }

}