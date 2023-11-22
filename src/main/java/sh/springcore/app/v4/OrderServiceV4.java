package sh.springcore.app.v4;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sh.springcore.trace.TraceId;
import sh.springcore.trace.TraceStatus;
import sh.springcore.trace.logtrace.LogTrace;
import sh.springcore.trace.template.AbstractTemplate;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {
    private final OrderRepositoryV4 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };
        template.execute("OrderService.orderItem()");
    }


}
