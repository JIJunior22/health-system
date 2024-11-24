package group.nine.healthsystem.domain;

import lombok.Data;

@Data
public class MonitoramentoFacade {
    private Glicose glicose;
    private Hipertensao hipertensao;
    private Usuario usuarioIMC;


    public void pressaoArterial(double sistolica, double diastolica, String observacoes) {
        hipertensao.registrarPressao(sistolica, diastolica, observacoes);
    }

    public void glicose(double nivel, boolean jejum, String observacoes) {
        glicose.registrarNivel(nivel, jejum, observacoes);
    }

    public void imc(double altura, double peso) {
        usuarioIMC.registrarIMC(altura, peso);
    }

    public String relatorioFacade() {
        StringBuilder relatorio = new StringBuilder();

        relatorio.append("Relatório de Saúde\n");
        relatorio.append("===================\n\n");

        relatorio.append("1. Hipertensão:\n");
        relatorio.append(hipertensao.gerarRelatorioPressao()).append("\n\n");

        relatorio.append("2. Níveis de Glicose:\n");
        relatorio.append(glicose.gerarRelatorioGlicose()).append("\n\n");

        relatorio.append("3. Índice de Massa Corporal (IMC):\n");
        relatorio.append(usuarioIMC.gerarRelatorioIMC()).append("\n");

        return relatorio.toString();
    }



}


