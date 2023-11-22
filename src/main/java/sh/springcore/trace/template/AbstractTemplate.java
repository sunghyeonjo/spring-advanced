package sh.springcore.trace.template;

import sh.springcore.trace.TraceStatus;
import sh.springcore.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> {

    public final LogTrace trace;

    public AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            // 비즈니스 로직 실행
            T result = call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();

}
