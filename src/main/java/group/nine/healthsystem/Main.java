package group.nine.healthsystem;

import group.nine.healthsystem.dao.GlicoseDao;
import group.nine.healthsystem.dao.HipertensaoDao;
import group.nine.healthsystem.dao.UsuarioDao;
import group.nine.healthsystem.domain.Glicose;
import group.nine.healthsystem.domain.Hipertensao;
import group.nine.healthsystem.domain.Usuario;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        UsuarioDao userDao = new UsuarioDao();
        var junior=new Usuario();

        junior.setNome("Junior");
        junior.setPeso(74.5);
        junior.setAltura(1.78);
        junior.setEmail("junior@gmail.com");
        junior.setSexo("M");
        junior.setSenha("12323");
        junior.setDataNascimento(LocalDate.of(1990,12,22));
       // userDao.criar(junior);

//
//        userDao.criar(junior);
////        String usuario= junior.gerarRelatorioIMC();
////        System.out.println(usuario);
//        junior.exibirUserInfo();


        Usuario luke = new Usuario();
        luke.setNome("Luke");
        luke.setPeso(75.0);
        luke.setAltura(1.80);
        luke.setEmail("luke.skywalker@gmail.com");
        luke.setSexo("M");
        luke.setSenha("1234");
        luke.setDataNascimento(LocalDate.of(1990, 5, 15));
       // userDao.criar(luke);


        Usuario amora = new Usuario();
        amora.setNome("Amora");
        amora.setPeso(58.5);
        amora.setAltura(1.65);
        amora.setEmail("amora.lima@gmail.com");
        amora.setSexo("F");
        amora.setSenha("senha123");
        amora.setDataNascimento(LocalDate.of(1995, 11, 8));
        //  userDao.criar(amora);


        Usuario francisco = new Usuario();
        francisco.setNome("Francisco");
        francisco.setPeso(83.0);
        francisco.setAltura(1.75);
        francisco.setEmail("francisco.santos@gmail.com");
        francisco.setSexo("M");
        francisco.setSenha("franpass");
        francisco.setDataNascimento(LocalDate.of(1995, 2, 24));
        //  userDao.criar(francisco);


        Usuario samuel = new Usuario();
        samuel.setNome("Samuel Dantas");
        samuel.setPeso(69.0);
        samuel.setAltura(1.78);
        samuel.setEmail("samuel.alves@gmail.com");
        samuel.setSexo("M");
        samuel.setSenha("sam123");
        samuel.setDataNascimento(LocalDate.of(2000, 2, 10));
        //userDao.criar(samuel);


        Usuario yanKennedy = new Usuario();
        yanKennedy.setNome("Yan Kennedy");
        yanKennedy.setPeso(74.0);
        yanKennedy.setAltura(1.75);
        yanKennedy.setEmail("kennedy.yan8@gmail.com");
        yanKennedy.setSexo("M");
        yanKennedy.setSenha("ykpass");
        yanKennedy.setDataNascimento(LocalDate.of(2006, 1, 18));
        //userDao.criar(yanKennedy);



        Usuario joaoVictor = new Usuario();
        joaoVictor.setNome("João Victor");
        joaoVictor.setPeso(68.5);
        joaoVictor.setAltura(1.75);
        joaoVictor.setEmail("joao.victor@gmail.com");
        joaoVictor.setSexo("M");
        joaoVictor.setSenha("joao123");
        joaoVictor.setDataNascimento(LocalDate.of(1997, 11, 12));

//       userDao.criar(joaoVictor);


        Usuario everton = new Usuario();
//        everton.setNome("Everton");
//        everton.setPeso(82.3);
//        everton.setAltura(1.80);
//        everton.setEmail("everton.silva@gmail.com");
//        everton.setSexo("M");
//        everton.setSenha("everton123");
//        everton.setDataNascimento(LocalDate.of(1990, 4, 25));

        // userDao.criar(everton);


        //EXIBIÇÃO DO IMC
//      yanKennedy.exibirUserInfo();
//        luke.exibirUserInfo();
        // everton.exibirUserInfo();
        // samuel.exibirUserInfo();


        //TESTE DO METODO EXCLUIR
        //  userDao.deleteById(8L);


//        TESTE DE ATUALIZAÇÃO

        everton.setCod(9);
        everton.setNome("Everton Candido");
        everton.setPeso(82.3);
        everton.setAltura(1.80);
        everton.setEmail("everton.silva@gmail.com");
        everton.setSexo("M");
        everton.setSenha("everton123");
        everton.setDataNascimento(LocalDate.of(1990, 4, 25));
//
//          userDao.atualizarDadosComEntrada(everton);
//         userDao.atualizarDadosComEntrada(9L);
//        userDao.exibirTodosUsuarios();
//        userDao.update(everton);


//        EntityManagerFactory em = Persistence.createEntityManagerFactory("healthsystem");
//        EntityManager emc = em.createEntityManager();
//        Usuario user = emc.find(Usuario.class, 14L);
//        userDao.exibirTodosUsuarios();
//        System.out.println(user.getNome());
//        System.out.println(user.getEmail());
//        user.setNome("Jonh Doe");
//
//        emc.getTransaction().begin();
//        emc.merge(user);
//        emc.getTransaction().commit();
//        System.out.println(user.getNome());
//        System.out.println(user.getEmail());
//        userDao.exibirTodosUsuarios();




        //TESTES DE GLICOSE
        //SALVAR
        GlicoseDao glicoseDao = new GlicoseDao();
        Glicose glicose = new Glicose();

        glicose.setNivelGlicose(120.5);
        glicose.setDataHora("2024-11-26 08:30:00");
        glicose.setEmJejum(true);
        glicose.setObservacoes("Medido em jejum, logo ao acordar.");

        //glicoseDao.exibirDados();

        //TESTES HIPERTENÇÃO
//        HipertensaoDao hipertensaoDao=new HipertensaoDao();
//        Hipertensao hipertensao=new Hipertensao();
//
//        hipertensao.


        Usuario jenny = new Usuario();
       // jenny.setCod(10);
//        jenny.setNome("Jenny Doe");
//        jenny.setPeso(74.6);
//        jenny.setAltura(1.60);
//        jenny.setEmail("john.doe@gmail.com");
//        jenny.setSexo("F");
        jenny.setSenha("jennygatinha");
        jenny.setDataNascimento(LocalDate.of(1995, 11, 22));

      // userDao.exibirTodosUsuarios();

    //userDao.criar(jenny);
       userDao.update(jenny,50);


    }
}
