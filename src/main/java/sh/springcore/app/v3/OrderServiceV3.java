package sh.springcore.app.v3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sh.springcore.trace.TraceId;
import sh.springcore.trace.TraceStatus;
import sh.springcore.trace.logtrace.LogTrace;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {
    private final OrderRepositoryV3 orderRepository;
    private final LogTrace trace;

    public void orderItem(TraceId traceId, String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderService.orderItem()");
            orderRepository.save(status.getTraceId(), itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }


}
