package group.nine.healthsystem.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "getAll", query = "select l from Login l"),
        @NamedQuery(name = "removerLogin", query = "select l from Login l where l =:l")
})
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(unique = true)
    String email;
    String senha;
}
  

