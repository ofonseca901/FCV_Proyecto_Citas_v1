package co.fcv.citas.application;

public class AuthFailure extends RuntimeException {
    public enum Kind { INVALID, DUPLICATE, UNAUTHORIZED }
    private final Kind kind;
    public AuthFailure(Kind kind, String message) { super(message); this.kind = kind; }
    public Kind kind() { return kind; }
    public static AuthFailure unauthorized() {
        return new AuthFailure(Kind.UNAUTHORIZED, "Credenciales o sesión inválidas.");
    }
}
