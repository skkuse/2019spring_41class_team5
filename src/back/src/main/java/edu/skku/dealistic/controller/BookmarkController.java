package edu.skku.dealistic.controller;

import edu.skku.dealistic.model.Bookmark;
import edu.skku.dealistic.model.Item;
import edu.skku.dealistic.model.User;
import edu.skku.dealistic.persistence.BookmarkRepository;
import edu.skku.dealistic.persistence.ItemRepository;
import edu.skku.dealistic.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final BookmarkRepository bookmarkRepository;

    @GetMapping("/bookmarks")
    public List<Bookmark> getBookmarks(
            @RequestParam(required = false) Integer itemId,
            @RequestParam(required = false) String userId
    ) {
        Optional<User> user = userId == null ? Optional.empty() : userRepository.findById(userId);
        Optional<Item> item = itemId == null ? Optional.empty() : itemRepository.findById(itemId);

        if (user.isPresent() && item.isPresent())
            return List.of(bookmarkRepository.findBookmarkByUserAndItem(user.get(), item.get()));
        if (user.isPresent())
            return bookmarkRepository.findBookmarksByUser(user.get());
        if (item.isPresent())
            return bookmarkRepository.findBookmarksByItem(item.get());
        else return List.of();
    }

    @PostMapping("/bookmarks")
    public Bookmark createBookmark(@RequestBody Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @DeleteMapping("/bookmarks/{id}")
    public void deleteBookmark(@PathVariable Integer id) {
        bookmarkRepository.deleteById(id);
    }
}
