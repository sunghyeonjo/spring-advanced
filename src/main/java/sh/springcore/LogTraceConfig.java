package sh.springcore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sh.springcore.trace.logtrace.FieldLogTrace;
import sh.springcore.trace.logtrace.LogTrace;
import sh.springcore.trace.logtrace.ThreadLocalLogTrace;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}

