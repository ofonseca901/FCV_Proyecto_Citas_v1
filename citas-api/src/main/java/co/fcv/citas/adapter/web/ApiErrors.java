package co.fcv.citas.adapter.web;

import co.fcv.citas.application.AuthFailure;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiErrors {
    static final class RequestFailure extends RuntimeException {
        final int status; final String code;
        RequestFailure(int status, String code, String message) { super(message); this.status = status; this.code = code; }
    }
    @ExceptionHandler(AuthFailure.class)
    ResponseEntity<Map<String, String>> auth(AuthFailure e) {
        int status = switch (e.kind()) { case INVALID -> 400; case DUPLICATE -> 409; case UNAUTHORIZED -> 401; };
        return ResponseEntity.status(status).cacheControl(CacheControl.noStore()).body(Map.of("code", e.kind().name(), "message", e.getMessage()));
    }
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    ResponseEntity<Map<String, String>> validation(Exception e) {
        return ResponseEntity.badRequest().body(Map.of("code", "INVALID", "message", "Revisa los campos obligatorios y sus formatos. No se permiten campos adicionales."));
    }
    @ExceptionHandler(RequestFailure.class)
    ResponseEntity<Map<String, String>> request(RequestFailure e) {
        return ResponseEntity.status(e.status).body(Map.of("code", e.code, "message", e.getMessage()));
    }
}
