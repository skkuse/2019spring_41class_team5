package edu.skku.dealistic.service;

import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.persistence.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemCrawlerServiceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @Rollback(false)
    @Transactional
    public void imageInsertTest() throws IOException {
        Map<Integer, byte[]> imageMap = new HashMap<>();
        imageMap.put(1, Files.readAllBytes(new File("/home/junbread/Downloads/images/1.jpg").toPath()));
        imageMap.put(11, Files.readAllBytes(new File("/home/junbread/Downloads/images/11.jpg").toPath()));
        imageMap.put(13, Files.readAllBytes(new File("/home/junbread/Downloads/images/13.jpg").toPath()));
        imageMap.put(14, Files.readAllBytes(new File("/home/junbread/Downloads/images/14.jpg").toPath()));
        imageMap.put(15, Files.readAllBytes(new File("/home/junbread/Downloads/images/15.jpg").toPath()));
        imageMap.put(16, Files.readAllBytes(new File("/home/junbread/Downloads/images/16.jpg").toPath()));
        imageMap.put(17, Files.readAllBytes(new File("/home/junbread/Downloads/images/17.jpg").toPath()));
        imageMap.put(18, Files.readAllBytes(new File("/home/junbread/Downloads/images/18.jpg").toPath()));
        imageMap.put(19, Files.readAllBytes(new File("/home/junbread/Downloads/images/19.jpg").toPath()));
        imageMap.put(20, Files.readAllBytes(new File("/home/junbread/Downloads/images/20.jpg").toPath()));

        imageMap.forEach((index, image) -> {
            Item item = itemRepository.getOne(index);
            item.setImage(image);

            itemRepository.save(item);
        });
    }
}