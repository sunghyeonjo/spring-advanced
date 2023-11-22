package sh.springcore.threadlocal;

public class JwtContext {
    private String jwt;

    public JwtContext(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return this.jwt;
    }

}
