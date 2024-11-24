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
  
public class Login {
    private int id;

    private String login;

    private String senha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
