package sh.springcore.test;

import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
//@Transactional
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookRepository bookRepository;

    @Before
    public void before() {
        Book book = new Book();
        book.setTitle("title");
        book.setContent("before");
        bookRepository.save(book);
    }

    @Test
    @DisplayName("Cacheable 테스트")
    public void cacheableTest() throws Exception {
        mockMvc.perform(post("/create").param("title", "test").param("content", "before"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/get").param("title", "test"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/get").param("title", "test")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("CacheEvict 테스트")
    public void cacheEvictTest() throws Exception {
        mockMvc.perform(post("/create")
                        .param("title", "test")
                        .param("content", "before"))
                .andExpect(status().isOk());

        mockMvc.perform(put("/update")
                        .param("title", "test")
                        .param("content", "after"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/get")
                        .param("title", "test"))
                .andExpect(jsonPath("$.content").value("after"))
                .andExpect(status().isOk());

    }

    @Test
    public void updateTest() {
        Book book = new Book();
        book.setTitle("title");
        book.setContent("before");
        bookRepository.save(book);
        book.setContent("after");
        Book byId = bookRepository.findByTitle("title");

        Assertions.assertEquals("after", byId.getContent());

    }
}