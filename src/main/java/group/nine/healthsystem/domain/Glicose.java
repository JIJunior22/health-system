package group.nine.healthsystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Glicose {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double nivelGlicose;// (Valor em mg/dL ou mmol/L)
    private String dataHora;// (Data e hora do registro)
    private boolean emJejum;// (Indica se o registro foi em jejum)
    private String observacoes;//


    public void registrarNivel(double nivel, boolean jejum, String observacoes) {
        this.nivelGlicose = nivel;
        this.dataHora = java.time.LocalDateTime.now().toString();
        this.emJejum = jejum;
        this.observacoes = observacoes;
    }

    public boolean verificarHipoGlicemia() {
        if (emJejum) {
            return nivelGlicose < 70; // Valor crítico em jejum
        } else {
            return nivelGlicose < 80; // Valor crítico não em jejum
        }


    }

    public boolean verificarHiperGlicemia() {
        if (emJejum) {
            return nivelGlicose > 100; // Limite superior em jejum
        } else {
            return nivelGlicose > 140; // Limite superior após alimentação
        }
    }


    public String gerarRelatorioGlicose() {

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Relatório de Glicose:\n")
                .append(String.format("Nível de Glicose: %.2f mg/dL\n", nivelGlicose))
                .append(String.format("Data e Hora do Registro: %s\n", dataHora))
                .append(emJejum ? "Registro feito em jejum.\n" : "Registro feito após alimentação.\n")
                .append(String.format("Observações: %s\n", observacoes));

        if (verificarHipoGlicemia()) {
            relatorio.append("Atenção: Hipoglicemia detectada! Consulte um médico imediatamente.\n");
        } else if (verificarHiperGlicemia()) {
            relatorio.append("Atenção: Hiperglicemia detectada! Consulte um médico.\n");
        } else {
            relatorio.append("Nível de glicose dentro dos limites normais.\n");
        }

        return relatorio.toString();
    }

}

