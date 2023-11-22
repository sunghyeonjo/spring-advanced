package sh.springcore.test;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final DeliveryService deliveryService;

    public Book purchase(String title) {
        Book book = bookRepository.findByTitle(title);

        if (book == null) {
            log.warn("Purchase Book title : {} does not exist", title);
            throw new EntityNotFoundException("해당 도서는 존재하지 않습니다.");
        }

        if (book.getStock() <= 0) {
            log.warn("Purchase Book title : {} has no stock", title);
            throw new IllegalStateException("해당 도서의 재고가 부족합니다.");
        }

        // 비즈니스 로직
        // ...
        // 재고를 조정한다.
        BookService.updateStock(book);

        deliveryService.deliver();

        return book;
    }


    private static void updateStock(Book book) {
        // ...
    }

    public Book create(String title, String content) {
        return bookRepository.save(Book.builder().title(title).content(content).build());
    }

    public Book findByBookTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public void updateContent(String title, String content) {
        Book book = bookRepository.findByTitle(title);
        book.setContent(content);
        bookRepository.save(book);
    }
}
