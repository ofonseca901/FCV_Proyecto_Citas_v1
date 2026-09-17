package co.fcv.citas.domain;

import java.util.Set;

public record User(Long id, String firstName, String lastName, String documentType,
                   String documentNumber, String email, String phone,
                   String passwordHash, boolean active, Set<String> roles) {
    public User { roles = Set.copyOf(roles); }
}
