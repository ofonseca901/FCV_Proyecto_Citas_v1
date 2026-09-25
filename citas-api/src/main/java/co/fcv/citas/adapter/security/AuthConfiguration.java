package co.fcv.citas.adapter.security;

import co.fcv.citas.application.*;
import java.time.Clock;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AuthConfiguration {
    @Bean Clock clock() { return Clock.systemUTC(); }
    @Bean AuthPorts.Passwords passwords() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        return new AuthPorts.Passwords() {
            public String hash(String raw) { return encoder.encode(raw); }
            public boolean matches(String raw, String hash) { return encoder.matches(raw, hash); }
        };
    }
    @Bean AuthService authService(AuthPorts.Users users, AuthPorts.Sessions sessions, AuthPorts.Passwords passwords,
                                 AuthPorts.Tokens tokens, Clock clock, @Value("${app.jwt.refresh-days}") long days,
                                 @Value("${app.session.inactivity-minutes}") long inactivityMinutes) {
        if (days < 1 || days > 30) throw new IllegalArgumentException("JWT_REFRESH_DAYS debe estar entre 1 y 30.");
        if (inactivityMinutes < 1 || inactivityMinutes > 60) throw new IllegalArgumentException("SESSION_INACTIVITY_MINUTES debe estar entre 1 y 60.");
        return new AuthService(users, sessions, passwords, tokens, clock, Duration.ofDays(days), Duration.ofMinutes(inactivityMinutes));
    }
}
