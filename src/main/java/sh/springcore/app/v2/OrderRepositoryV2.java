package sh.springcore.app.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sh.springcore.trace.TraceId;
import sh.springcore.trace.TraceStatus;
import sh.springcore.trace.TraceV2;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

    private final TraceV2 trace;

    public void save(TraceId traceId, String itemId) {
        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId, "OrderRepository.save()");

            // 저장 로직
            if (itemId.equals("ex")) {
                throw new IllegalStateException("Exception!");
            }
            sleep(1000);

            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
