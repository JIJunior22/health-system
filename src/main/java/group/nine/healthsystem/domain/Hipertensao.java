package group.nine.healthsystem.domain;


import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;

@Entity
@Data

@NamedQueries({@NamedQuery(name = "hipertencoes.getByName", query = "select  h from Hipertensao h where h.pressaoSistolica = :pressaoSistolica"),
        @NamedQuery(name = "hipertencoes.listarTodos", query = "SELECT ps FROM Hipertensao ps"),
        @NamedQuery(name = "hipertensoes.getById", query = "select h from Hipertensao h where h.id = :id")
})


public class Hipertensao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double pressaoSistolica;//(Pressão máxima)
    private double pressaoDiastolica;//(Pressão mínima)
    private double frequenciaCardiaca;
    private String dataHora;//(Data e hora do registro)
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private String observacoes;


    public void registrarPressao(double sistolica, double diastolica, String observacoes) {
        this.pressaoSistolica = sistolica;
        this.pressaoDiastolica = diastolica;
        this.dataHora = java.time.LocalDateTime.now().toString();
        this.observacoes = observacoes;

    }

    public boolean verificarRisco() {
        return pressaoSistolica >= 180 || pressaoDiastolica >= 120;


    }

    public String gerarRelatorioPressao() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Relatório de Pressão Arterial:\n")
                .append(String.format("Pressão Sistólica: %.2f mmHg\n", pressaoSistolica))
                .append(String.format("Pressão Diastólica: %.2f mmHg\n", pressaoDiastolica))
                .append(String.format("Data e Hora do Registro: %s\n", dataHora))
                .append(String.format("Observações: %s\n", observacoes));

        if (verificarRisco()) {
            relatorio.append("Atenção: Valores indicam risco elevado! Procure um médico imediatamente.\n");
        } else {
            relatorio.append("Pressão dentro de limites aceitáveis.\n");
        }

        return relatorio.toString();
    }
}


