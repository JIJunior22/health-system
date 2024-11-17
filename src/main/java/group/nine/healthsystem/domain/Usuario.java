package group.nine.healthsystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nome;
    @Column(unique = true)
    String email;
    String senha;
    LocalDate dataNascimento;
    String sexo;
    double peso;
    double altura;
}
