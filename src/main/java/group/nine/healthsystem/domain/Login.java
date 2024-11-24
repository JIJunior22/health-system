package group.nine.healthsystem.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@NamedQueries({
        @NamedQuery(name = "getAll", query = "select l from Login l"),
        @NamedQuery(name = "removerLogin", query = "select l from Login l where l =:l")
})
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String login;
    String senha;
}
