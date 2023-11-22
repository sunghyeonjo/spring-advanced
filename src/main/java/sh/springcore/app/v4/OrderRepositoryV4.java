package sh.springcore.app.v4;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sh.springcore.trace.TraceId;
import sh.springcore.trace.TraceStatus;
import sh.springcore.trace.logtrace.LogTrace;
import sh.springcore.trace.template.AbstractTemplate;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {
    private final LogTrace trace;

    public void save(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                // 저장 로직
                if (itemId.equals("ex")) {
                    throw new IllegalStateException("Exception!");
                }
                sleep(1000);
                return null;
            }
        };
        template.execute("OrderRepository.save()");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
