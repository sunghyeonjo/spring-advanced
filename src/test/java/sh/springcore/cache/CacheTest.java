package sh.springcore.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sh.springcore.test.Book;
import sh.springcore.test.BookRepository;
import sh.springcore.test.BookService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;


}
