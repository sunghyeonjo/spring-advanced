package sh.springcore.trace;


import org.junit.jupiter.api.Test;

public class TraceV1Test {

    @Test
    void begin_end() {
        TraceV1 trace = new TraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

    @Test
    void begin_exception() {
        TraceV1 trace = new TraceV1();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException());

    }
}
