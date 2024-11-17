package group.nine.healthsystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class RelatorioEstadoDeSaude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDate dataHora;
    @OneToOne
    private Usuario usuarioID;
    private LocalDate periodoInicio;
    private LocalDate periodoFim;
    private String pathFile;


}
