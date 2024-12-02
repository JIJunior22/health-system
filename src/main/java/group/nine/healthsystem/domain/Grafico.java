package group.nine.healthsystem.domain;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.Map;

@Data
public class Grafico {
    private String tipoGrafico;
    private String titulo;
    private Map<String, Double> dados; // Indicador como chave e valor como métrica
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private String configuracao; // JSON ou string para ajustes visuais
}
