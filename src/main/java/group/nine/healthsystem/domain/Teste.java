package group.nine.healthsystem.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Teste {
    public LocalDate prazoProjeto(LocalDate inicioProjeto, int tempoRealizacao) {
        return inicioProjeto.plusMonths(tempoRealizacao);
    }

    public void definirPrazo(LocalDate dataInicio, int tempoDeExecucao) {

        LocalDate dataLimite = prazoProjeto(dataInicio, tempoDeExecucao);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("======= DADOS PARA REALIZAÇÃO DE SERVIÇOS ==============");
        System.out.println("Data de início: " + dataInicio.format(formatter));
        System.out.println("Prazo para execução: " + tempoDeExecucao + " meses");
        System.out.println("Data limite: " + dataLimite.format(formatter));
    }

    public static void main(String[] args) {
        Teste prazo = new Teste();
        prazo.definirPrazo(LocalDate.now(), 2);


    }
}
