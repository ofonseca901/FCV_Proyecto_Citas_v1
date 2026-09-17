package co.fcv.citas.adapter.persistence;

import co.fcv.citas.application.AuthPorts;
import co.fcv.citas.application.AuthFailure;
import co.fcv.citas.domain.User;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
public class UserPersistenceAdapter implements AuthPorts.Users {
    private final UserJpaRepository repository;
    public UserPersistenceAdapter(UserJpaRepository repository) { this.repository = repository; }
    public Optional<User> byEmail(String email) { return repository.findByEmail(email).map(this::map); }
    public Optional<User> byId(Long id) { return repository.findById(id).map(this::map); }
    public User create(User user) {
        UserEntity entity = new UserEntity();
        entity.firstName = user.firstName(); entity.lastName = user.lastName();
        entity.documentType = user.documentType(); entity.documentNumber = user.documentNumber();
        entity.email = user.email(); entity.phone = user.phone(); entity.passwordHash = user.passwordHash();
        entity.active = user.active(); entity.roles.addAll(user.roles());
        try { return map(repository.saveAndFlush(entity)); }
        catch (DataIntegrityViolationException e) {
            throw new AuthFailure(AuthFailure.Kind.DUPLICATE, "El correo o documento ya están registrados.");
        }
    }
    private User map(UserEntity e) {
        return new User(e.id, e.firstName, e.lastName, e.documentType, e.documentNumber,
                e.email, e.phone, e.passwordHash, e.active, e.roles);
    }
}
