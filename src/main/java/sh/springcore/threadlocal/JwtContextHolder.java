package sh.springcore.threadlocal;

public class JwtContextHolder {

    private static final ThreadLocal<JwtContext> jwtContextHolder = new InheritableThreadLocal<>();

    public static void setJwtContext(JwtContext jwtContext) {
        jwtContextHolder.set(jwtContext);
    }

    public static JwtContext getJwtContext() {
        JwtContext jwtContext = jwtContextHolder.get();
        if (jwtContext == null) {
            return new JwtContext("No token!");
        }
        return jwtContext;
    }

    public static void clear() {
        jwtContextHolder.remove();
    }
}
