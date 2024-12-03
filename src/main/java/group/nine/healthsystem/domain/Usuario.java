package group.nine.healthsystem.domain;

import group.nine.healthsystem.dao.GlicoseDao;
import group.nine.healthsystem.dao.HipertensaoDao;
import group.nine.healthsystem.dao.LoginDao;
import group.nine.healthsystem.dao.UsuarioDao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
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
        @NamedQuery(name = "usuarios.logar", query = "SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha")
})
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cod;
    private String nome;
    @Column(unique = true)
    private String email;
    @ToString.Exclude
    private String senha;
    private LocalDate dataNascimento;
    private String sexo;
    private double peso;
    private double altura;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Glicose> glicose = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Hipertensao> hipertensao = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Hipertensao> login = new ArrayList<>();


    public double calcularIMC() {
        if (altura <= 0 || peso <= 0) {
            throw new IllegalStateException("Altura ou peso inválido para cálculo do IMC.");
        }
        return peso / (altura * altura);
    }

    public void registrarIMC(double peso, double altura) {
        if (peso <= 0 || altura <= 0) {
            throw new IllegalArgumentException("Peso e altura devem ser valores positivos.");
        }
        this.peso = peso;
        this.altura = altura;
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

    public void addGlicose(Glicose glicose) {
        this.glicose.add(glicose);
    }

    public void addHipertensao(Hipertensao hipertensao) {
        this.hipertensao.add(hipertensao);
    }

    public static void associarDados(UsuarioDao userDao,
                                     Usuario usuario, int sistolica,
                                     int diastolica, double glicoseNivel,
                                     String hipertensaoObs, String glicoseObs,
                                     String email, String senha) {
        // Associar hipertensão
        HipertensaoDao hipertensaoDao = new HipertensaoDao();
        Hipertensao hipertensao = new Hipertensao();
        hipertensao.setPressaoSistolica(sistolica);
        hipertensao.setPressaoDiastolica(diastolica);
        hipertensao.setDataHora("2024-12-01 10:00:00");
        hipertensao.setObservacoes(hipertensaoObs);
        hipertensao.setUsuario(usuario);
        hipertensaoDao.salvarHipertencao(hipertensao, usuario);

        // Associar glicose
        Glicose glicose = new Glicose();
        GlicoseDao glicoseDao = new GlicoseDao();
        glicose.setNivelGlicose(glicoseNivel);
        glicose.setDataHora("2024-12-01 08:00:00");
        glicose.setEmJejum(true);
        glicose.setObservacoes(glicoseObs);
        glicose.setUsuario(usuario);
        glicoseDao.salvar(glicose, usuario);

        // Associar Email e senha
        Login login = new Login();
        login.setEmail(email);
        login.setSenha(senha);

        LoginDao loginDao = new LoginDao();
        loginDao.adicionarLogin(login, usuario);
    }
    }

