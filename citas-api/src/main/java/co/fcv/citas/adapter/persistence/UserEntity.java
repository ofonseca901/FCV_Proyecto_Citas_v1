package co.fcv.citas.adapter.persistence;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "app_users")
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @Column(nullable = false, length = 100) public String firstName;
    @Column(nullable = false, length = 100) public String lastName;
    @Column(nullable = false, length = 10) public String documentType;
    @Column(nullable = false, length = 30) public String documentNumber;
    @Column(nullable = false, length = 254) public String email;
    @Column(nullable = false, length = 25) public String phone;
    @Column(nullable = false, length = 100) public String passwordHash;
    @Column(nullable = false) public boolean active;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role_code", length = 20, nullable = false)
    public Set<String> roles = new HashSet<>();
}
