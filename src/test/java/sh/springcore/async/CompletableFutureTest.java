package sh.springcore.async;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CompletableFutureTest {

    @Test
    public void run_async_test() {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
    }

    @Test
    public void run_async_with_executor_test() {
        Executor executor = Executors.newFixedThreadPool(30);

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": hi");
        }, executor);
    }
}
