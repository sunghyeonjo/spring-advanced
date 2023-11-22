package sh.springcore.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    @PostMapping("/create")
    @ResponseBody
//    @CacheEvict(value = "book", key = "#title")
    public Book create(@RequestParam String title, @RequestParam String content) {
        log.info("create method call");
        return bookService.create(title, content);
    }

    @PutMapping("/update")
//    @CacheEvict(value = "book", key = "#title")
    public void update(@RequestParam String title, @RequestParam String content) {
        log.info("update method call");
        bookService.updateContent(title, content);
    }

    @GetMapping("/get")
//    @Cacheable(value = "book", key = "#title")
    public Book getBook(@RequestParam String title) {
        log.info("get method call");
        return bookService.findByBookTitle(title);
    }
}
