package group.nine.healthsystem.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@NamedQueries({@NamedQuery(name = "usuarios.getByName", query = "select  n from Usuario n where n.nome = :nome"),
        @NamedQuery(name="usuarios.listarTodos",query="SELECT u FROM Usuario u")
        })
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod;
    private String nome;
    @Column(unique = true)
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private String sexo;
    private double peso;
    private double altura;


    public double calcularIMC() {
        return peso / (altura * altura);
    }

    public void registrarIMC(double peso, double altura) {
        this.peso = peso;
        this.altura = altura;

    }

    public String gerarRelatorioIMC() {
        double imc = calcularIMC();
        String classificacao = classificarIMC(imc);


        return String.format("""
                ╔══════════════════════════════════════╗
                ║          Relatório de IMC            ║
                ╚══════════════════════════════════════╝
                Nome           : %s
                Peso           : %.2f kg
                Altura         : %.2f m
                IMC            : %.2f
                Classificação  : %s
                ═══════════════════════════════════════
                """, nome, peso, altura, imc, classificacao);


    }

    public void exibirUserInfo() {
        String resultadoImc = gerarRelatorioIMC();
        System.out.println(resultadoImc);
    }

    private String classificarIMC(double imc) {
        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc < 24.9) {
            return "Peso normal";
        } else if (imc < 29.9) {
            return "Sobrepeso";
        } else {
            return "Obesidade";
        }
    }
}
