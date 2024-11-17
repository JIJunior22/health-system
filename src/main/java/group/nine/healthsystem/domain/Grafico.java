package group.nine.healthsystem.domain;

import lombok.Data;

import java.util.Map;

@Data
public class Grafico {
    private String tipoGrafico;
    private String titulo;
    private Map<String, Double> dados; // Indicador como chave e valor como métrica
    private String configuracao; // JSON ou string para ajustes visuais
}
