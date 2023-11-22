package sh.springcore.dummy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SpringBootBeanLoadingTest {

    @Autowired
    private ApplicationContext applicationContext;


    @Test
    public void bean_load_with_SpringBootTest_annotation() {
        Assertions.assertTrue(applicationContext.getBeanDefinitionCount() > 0);
    }
}
