package domain.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@NamedEntityGraph(
        name = "user.role",
        attributeNodes = @NamedAttributeNode("roles")
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @ElementCollection
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_uuid"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private final List<Role> roles;

    public User() {
        roles = new ArrayList<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
