package group.nine.healthsystem.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Boleto {

    private Long id;

    private Long contratoId;

    private String codigo;


    private LocalDate data_vencimento;

    public Boleto(Long id, Long contratoId, String codigo, LocalDate data_vencimento) {
        this.id = id;
        this.contratoId = contratoId;
        this.codigo = codigo;
        this.data_vencimento = data_vencimento;
    }

    public Boleto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContratoId() {
        return contratoId;
    }

    public void setContratoId(Long contratoId) {
        this.contratoId = contratoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(LocalDate data_vencimento) {
        this.data_vencimento = data_vencimento;
    }


    public String gerarCodigo() {
        Random random = new Random();

        // Identificação do Banco (3 dígitos)
        String banco = String.format("%03d", random.nextInt(1000));

        // Moeda (1 dígito) - geralmente "9" para reais
        String moeda = "9";

        // Fator de Vencimento (4 dígitos)
        LocalDate dataBase = LocalDate.of(1997, 10, 7);
        long diasDesdeBase = ChronoUnit.DAYS.between(dataBase, this.data_vencimento);
        String fatorVencimento = String.format("%04d", diasDesdeBase);

        // Valor (10 dígitos) - valor do boleto em centavos
        String valor = String.format("%010d", random.nextInt(100000000)); // até R$ 1.000.000,00

        // Nosso Número (25 dígitos)
        String nossoNumero = String.format("%025d", Math.abs(random.nextLong()) % 1000000000000000000L);

        // Dígito Verificador (1 dígito)
        String digitoVerificador = String.valueOf(random.nextInt(10));

        // Código de Barras
        String codigoBarras = banco + moeda + fatorVencimento + valor + nossoNumero + digitoVerificador;

        return codigoBarras;
    }

}