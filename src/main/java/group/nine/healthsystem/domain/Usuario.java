package group.nine.healthsystem.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor

@NamedQueries({@NamedQuery(name = "usuarios.getByName", query = "select  n from Usuario n where n.nome = :nome"),
        @NamedQuery(name = "usuarios.listar", query = "SELECT u FROM Usuario u"),

})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod;

    private String nome;
    private LocalDate dataNascimento;
    private String sexo;
    private double peso;
    private double altura;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Glicose> glicose = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Hipertensao> hipertensao = new ArrayList<>();

    @SuppressWarnings("JpaAttributeTypeInspection")
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Login login;

    // Métodos relacionados diretamente ao usuário
    public double calcularIMC() {
        if (altura <= 0 || peso <= 0) {
            throw new IllegalStateException("Altura ou peso inválido para cálculo do IMC.");
        }
        return peso / (altura * altura);
    }

    public String gerarRelatorioIMC() {
        double imc = calcularIMC();
        String classificacao = classificarIMC(imc);

        return String.format("""
                Relatório IMC:
                Nome: %s
                Peso: %.2f kg
                Altura: %.2f m
                IMC: %.2f
                Classificação: %s
                """, nome, peso, altura, imc, classificacao);
    }

    public String classificarIMC(double imc) {
        if (imc < 18.5) return "Abaixo do peso";
        if (imc < 24.9) return "Peso normal";
        if (imc < 29.9) return "Sobrepeso";
        return "Obesidade";
    }

    public void addGlicose(Glicose glicose) {
        this.glicose.add(glicose);
    }

    public void addHipertensao(Hipertensao hipertensao) {
        this.hipertensao.add(hipertensao);
    }

}

