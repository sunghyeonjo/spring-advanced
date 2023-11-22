package sh.springcore.threadlocal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ThreadLocalTest {

    private JwtContextInterceptor interceptor;

    @Mock
    private Object handler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        interceptor = new JwtContextInterceptor();
    }


    @Test
    @DisplayName("withInitial 메서드를 통해 쓰레드로컬 변수 초기값을 설정할 수 있다.")
    void threadlocal_withInitial_returns_initialized_threadlocal_var() {
        ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

        Integer value = threadLocal.get();

        assertEquals(0, value);
    }

    @Test
    @DisplayName("remove 메서드를 호출하면 threadLocal에 할당된 값이 제거된다.")
    void threadlocal_remove_test() {
        ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 1);

        threadLocal.remove();

        Integer threadLocalValue = threadLocal.get();
        Assertions.assertNull(threadLocalValue);
    }

    @Test
    void jwt_token_interceptor_working_test() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.addHeader("X-AUTH", "your-jwt-token-here");

        boolean result = interceptor.preHandle(request, response, handler);

        assertTrue(result);

        JwtContext jwtContext = JwtContextHolder.getJwtContext();
        assertNotNull(jwtContext);

        assertEquals("your-jwt-token-here", jwtContext.getJwt());
    }

    @Test
    void jwt_token_interceptor_with_threadpool_working_test() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int threadCount = 0; threadCount < 5; threadCount++) {
            int token_postfix = threadCount;
            executorService.execute(() -> {
                System.out.println("jwt_before_request = " + JwtContextHolder.getJwtContext().getJwt());

                String token = "my-token" + "-" + token_postfix;
                MockHttpServletRequest request = new MockHttpServletRequest();
                MockHttpServletResponse response = new MockHttpServletResponse();
                request.addHeader("X-AUTH", token);
                interceptor.preHandle(request, response, handler);
                System.out.println("jwt_after_request = " + JwtContextHolder.getJwtContext().getJwt());
                interceptor.afterCompletion(request, response, handler, null);
            });
        }

        executorService.shutdown();

        // 스레드 풀 종료 대기
        while (true) {
            try {
                if (executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                    break;
                }
            } catch (InterruptedException e) {
                System.err.println("Error: " + e);
                executorService.shutdownNow();
            }
        }
        System.out.println("All threads are finished");
    }

    @Test
    void threadlocal_with_parent_and_child_thread() {
        JwtContext jwtContext = new JwtContext("myToken");
        JwtContextHolder.setJwtContext(jwtContext);

        // Main 쓰레드에서의 ThreadLocal
        Thread currentThread = Thread.currentThread();
        System.out.println("currentThread = " + currentThread);
        System.out.println("currentThread jwt = " + JwtContextHolder.getJwtContext().getJwt());

        // ThreadPool 에서 ThreadLocal
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.execute(() -> {
            Thread executorThread = Thread.currentThread();
            System.out.println("executorThread = " + executorThread);
            System.out.println("currentThread jwt = " + JwtContextHolder.getJwtContext().getJwt());
        });
    }
}
