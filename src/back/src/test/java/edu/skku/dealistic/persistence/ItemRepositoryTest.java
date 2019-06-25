package edu.skku.dealistic.persistence;

import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.model.ItemCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;

    @Test
    public void testFindAll() {
        List<Item> items = itemRepository.findAll();
        assertNotNull(items);
    }

    @Test
    public void findAllByNameLike() {
        List<Item> items = itemRepository.findByNameContaining("OMEN");
        Assert.assertNotEquals(items.size(), 0);
    }

    @Test
    public void findByCategory() {
        ItemCategory category = itemCategoryRepository.findById(1).get();

        List<Item> items = itemRepository.findItemsByCategory(category);
        Assert.assertNotEquals(items.size(), 0);
    }
}