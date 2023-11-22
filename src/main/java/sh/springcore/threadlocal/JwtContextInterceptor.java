package sh.springcore.threadlocal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtContextInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_NAME = "X-AUTH";

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(AUTHORIZATION_NAME);
        JwtContext jwtContext = new JwtContext(token);
        JwtContextHolder.setJwtContext(jwtContext);

        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 @Nullable Exception ex) {

        JwtContextHolder.clear();
    }
}
