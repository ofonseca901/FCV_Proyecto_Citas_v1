package co.fcv.citas.adapter.web;

import co.fcv.citas.application.AuthService;
import co.fcv.citas.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthFacade {
    private final AuthService service;
    public AuthFacade(AuthService service) { this.service = service; }
    @Transactional public User register(AuthService.Registration r) { return service.register(r); }
    @Transactional public AuthService.Login login(String email, String password) { return service.login(email, password); }
    @Transactional public AuthService.Login refresh(String token) { return service.refresh(token); }
    @Transactional(readOnly = true) public User identity(Long userId, String sessionId) { return service.identity(userId, sessionId); }
    @Transactional public void logout(Long userId, String sessionId) { service.logout(userId, sessionId); }
}
