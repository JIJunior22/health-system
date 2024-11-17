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
    Long id;
    double frequenciaCardiaca;
    double pressaoSistolica;
    double pressaoDiastolica;
    double dataHora;
    int saturacaoOxigenioNosangue;

}
