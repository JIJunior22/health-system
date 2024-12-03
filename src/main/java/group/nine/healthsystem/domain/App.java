package group.nine.healthsystem.domain;

import group.nine.healthsystem.dao.LoginDao;
import group.nine.healthsystem.dao.UsuarioDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static group.nine.healthsystem.domain.Usuario.associarDados;

public class App {
    public static void main(String[] args) {
        MonitoramentoFacade facade = new MonitoramentoFacade();
        UsuarioDao userDao = new UsuarioDao();
        List<Usuario> usuarios = new ArrayList<>();


        // ========================= CRIAÇÃO DE USUARIOS ===============================

        // Criar e associar dados para Samuel Dantas
//        Usuario samuelDantas = new Usuario();
//        samuelDantas.setNome("Samuel Dantas");
//        samuelDantas.setPeso(70.5);
//        samuelDantas.setAltura(1.78);
//        samuelDantas.setEmail("samuel.dantas@gmail.com");
//        samuelDantas.setSexo("M");
//
//        samuelDantas.setDataNascimento(LocalDate.of(2000, 2, 10));
//        userDao.criar(samuelDantas);
//        usuarios.add(samuelDantas);
//
//        associarDados(userDao, samuelDantas, 130, 85, 100.0, "Pressão aferida em repouso.", "Glicose medida em jejum.","samuel.dantas@gmail.com","123");
//
//        // Criar e associar dados para Yan Kennedy
//        Usuario yanKennedy = new Usuario();
//        yanKennedy.setNome("Yan Kennedy");
//        yanKennedy.setPeso(74.0);
//        yanKennedy.setAltura(1.75);
//        yanKennedy.setEmail("kennedy.yan@gmail.com");
//        yanKennedy.setSexo("M");
//        yanKennedy.setSenha("senhaYan");
//        yanKennedy.setDataNascimento(LocalDate.of(2006, 1, 18));
//        userDao.criar(yanKennedy);
//        usuarios.add(yanKennedy);
//
//        associarDados(userDao, yanKennedy, 120, 80, 95.0, "Pressão aferida no consultório.", "Glicose medida em jejum.");
//
//        // Criar e associar dados para Lucas Pereira
//        Usuario lucasPereira = new Usuario();
//        lucasPereira.setNome("Lucas Pereira");
//        lucasPereira.setPeso(78.0);
//        lucasPereira.setAltura(1.72);
//        lucasPereira.setEmail("lucas.pereira@gmail.com");
//        lucasPereira.setSexo("M");
//        lucasPereira.setSenha("senhaLucas");
//        lucasPereira.setDataNascimento(LocalDate.of(1992, 5, 14));
//        userDao.criar(lucasPereira);
//        usuarios.add(lucasPereira);
//
//        associarDados(userDao, lucasPereira, 125, 82, 110.0, "Pressão aferida após café.", "Glicose medida após o café da manhã.");
//
//        // Criar e associar dados para Luke
//        Usuario luke = new Usuario();
//        luke.setNome("Luke");
//        luke.setPeso(75.0);
//        luke.setAltura(1.80);
//        luke.setEmail("luke.skywalker@gmail.com");
//        luke.setSexo("M");
//        luke.setSenha("senhaLuke");
//        luke.setDataNascimento(LocalDate.of(1990, 5, 15));
//        userDao.criar(luke);
//        usuarios.add(luke);
//
//        associarDados(userDao, luke, 140, 90, 105.0, "Pressão aferida após exercício físico.", "Glicose medida em jejum.");
//
//        // Criar e associar dados para Amora
//        Usuario amora = new Usuario();
//        amora.setNome("Amora");
//        amora.setPeso(58.5);
//        amora.setAltura(1.65);
//        amora.setEmail("amora.lima@gmail.com");
//        amora.setSexo("F");
//        amora.setSenha("senhaAmora");
//        amora.setDataNascimento(LocalDate.of(1995, 11, 8));
//        userDao.criar(amora);
//        usuarios.add(amora);
//
//        associarDados(userDao, amora, 120, 78, 90.0, "Pressão aferida em repouso.", "Glicose medida em jejum.");
//
//        // Criar e associar dados para Junior
//        Usuario junior = new Usuario();
//        junior.setNome("Junior");
//        junior.setPeso(80.0);
//        junior.setAltura(1.75);
//        junior.setEmail("junior@gmail.com");
//        junior.setSexo("M");
//        junior.setSenha("senhaJunior");
//        junior.setDataNascimento(LocalDate.of(1985, 3, 10));
//        userDao.criar(junior);
//        usuarios.add(junior);
//
//        associarDados(userDao, junior, 130, 85, 100.0, "Pressão aferida no consultório.", "Glicose medida em jejum.");
//
//        // Criar e associar dados para João Victor
//        Usuario joaoVictor = new Usuario();
//        joaoVictor.setNome("João Victor");
//        joaoVictor.setPeso(68.5);
//        joaoVictor.setAltura(1.75);
//        joaoVictor.setEmail("joao.victor@gmail.com");
//        joaoVictor.setSexo("M");
//        joaoVictor.setSenha("senhaJoao");
//        joaoVictor.setDataNascimento(LocalDate.of(1997, 11, 12));
//        userDao.criar(joaoVictor);
//        usuarios.add(joaoVictor);
//
//        associarDados(userDao, joaoVictor, 118, 77, 90.0, "Pressão aferida após caminhada.", "Glicose medida em jejum.");
//
//        // Criar e associar dados para dois jogadores fictícios do Corinthians
//        Usuario jogador1 = new Usuario();
//        jogador1.setNome("Ronaldo Fenômeno");
//        jogador1.setPeso(94.0);
//        jogador1.setAltura(1.84);
//        jogador1.setEmail("ronaldo.corinthians@gmail.com");
//        jogador1.setSexo("M");
//        jogador1.setSenha("senhaRonaldo");
//        jogador1.setDataNascimento(LocalDate.of(1976, 9, 22));
//        userDao.criar(jogador1);
//        usuarios.add(jogador1);
//
//        associarDados(userDao, jogador1, 145, 95, 120.0, "Pressão aferida em repouso.", "Glicose medida após almoço.");
//
//        Usuario jogador2 = new Usuario();
//        jogador2.setNome("Sócrates");
//        jogador2.setPeso(85.0);
//        jogador2.setAltura(1.90);
//        jogador2.setEmail("socrates.corinthians@gmail.com");
//        jogador2.setSexo("M");
//        jogador2.setSenha("senhaSocrates");
//        jogador2.setDataNascimento(LocalDate.of(1954, 2, 19));
//        userDao.criar(jogador2);
//        usuarios.add(jogador2);
//
//        associarDados(userDao, jogador2, 135, 88, 95.0, "Pressão aferida após exercício físico.", "Glicose medida em jejum.");


//       USUARIO INCLUIDONDO LOGIN E SENHA
        Usuario diuare = new Usuario();
        diuare.setNome("Lucas Silva");
        diuare.setPeso(70);
        diuare.setAltura(1.90);
        diuare.setEmail("lucas@gmail.com");
        diuare.setSenha("123");
        diuare.setSexo("M");
        diuare.setDataNascimento(LocalDate.of(1992, 3, 19));
        usuarios.add(diuare);
//
        userDao.criar(diuare);
//
        associarDados(userDao, diuare, 135, 88, 95.0, "Pressão aferida após exercício físico.", "Glicose medida em jejum.","lucas@gmail.com","123");

        //   ========================= FIM  CRIAÇÃO DE USUARIOS ===============================
//
//
//

        //GERA UM RELATORIO DE USUARIO  partir do id
       // facade.enviarRelatorioGemini(3);



        //TESTES DE VALIDAÇÃO DE LOGIN
//        LoginDao loginDao = new LoginDao();
//        userDao.exibirTodosUsuarios();
//        loginDao.validarLogin("lukaspersy@yahoo.com","shadal00");
    }

}








