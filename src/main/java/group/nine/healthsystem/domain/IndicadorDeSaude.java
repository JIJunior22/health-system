package group.nine.healthsystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class IndicadorDeSaude {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double frequenciaCardiaca;
    private double pressaoSistolica;
    private double pressaoDiastolica;
    private double dataHora;
    private int saturacaoOxigenioNosangue;

}
