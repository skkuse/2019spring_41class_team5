package edu.skku.dealistic.persistence;

import edu.skku.dealistic.model.Bookmark;
import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {

    List<Bookmark> findBookmarksByUser(User user);

    List<Bookmark> findBookmarksByItem(Item item);

    Bookmark findBookmarkByUserAndItem(User user, Item item);

    void deleteBookmarkByUserAndItem(User user, Item item);
}
