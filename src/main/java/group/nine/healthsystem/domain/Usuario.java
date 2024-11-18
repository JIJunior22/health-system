package group.nine.healthsystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@NamedQueries({@NamedQuery(name = "usuarios.getByName", query = "select  n from Usuario n where n.nome = :nome")})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private String sexo;
    private double peso;
    private double altura;
}
