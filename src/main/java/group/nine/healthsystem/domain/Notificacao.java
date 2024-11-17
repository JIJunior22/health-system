package group.nine.healthsystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Usuario usuarioId;
    @OneToOne
    private IndicadorDeSaude tipoIndicador;
    @OneToOne
    private IndicadorDeSaude valorMedido;
    private double afericaoMaximaDoIndicador;
    private String msg;
    private LocalDateTime dataHora;
    private boolean status;
}
