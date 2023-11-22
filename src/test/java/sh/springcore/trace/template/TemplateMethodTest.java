package sh.springcore.trace.template;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import sh.springcore.trace.template.code.AbstractTemplate;
import sh.springcore.trace.template.code.SubClassLogic1;
import sh.springcore.trace.template.code.SubClassLogic2;

@Slf4j
public class TemplateMethodTest {

    @Test
    public void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long currentTime = System.currentTimeMillis();

        // 비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        // 비즈니스 로직 종료

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - currentTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long currentTime = System.currentTimeMillis();

        // 비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        // 비즈니스 로직 종료

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - currentTime;
        log.info("resultTime={}", resultTime);
    }

    @Test
    public void templateMethodV1() {
        AbstractTemplate t1 = new SubClassLogic1();
        t1.execute();

        AbstractTemplate t2 = new SubClassLogic2();
        t2.execute();
    }

    @Test
    public void templateMethodV2() {
        AbstractTemplate t1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info(("비즈니스 로직1 실행"));
            }
        };

        log.info("클래스 이름={}", t1.getClass());
        t1.execute();

        AbstractTemplate t2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info(("비즈니스 로직2 실행"));
            }
        };
        log.info("클래스 이름={}", t2.getClass());
        t2.execute();
    }
}
