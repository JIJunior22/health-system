package group.nine.healthsystem.service;

import group.nine.healthsystem.dao.UsuarioDao;
import group.nine.healthsystem.dao.GlicoseDao;
import group.nine.healthsystem.dao.HipertensaoDao;
import group.nine.healthsystem.dao.LoginDao;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;
import group.nine.healthsystem.domain.Glicose;
import group.nine.healthsystem.domain.Hipertensao;
import group.nine.healthsystem.domain.Login;
import group.nine.healthsystem.domain.Usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.EntityManager;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class UsuarioService {

    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final HipertensaoDao hipertensaoDao = new HipertensaoDao();
    private final GlicoseDao glicoseDao = new GlicoseDao();
    private final LoginDao loginDao = new LoginDao();

    public void associarDados(
            Usuario usuario, int sistolica, int diastolica, double glicoseNivel, Boolean jejum, Double frequenciaCardiaca,
            String hipertensaoObs, String glicoseObs, String email, String senha) {

        // Formata a data e hora atual
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
        String dataHoraAtual = LocalDateTime.now().format(formatter);

        // Associar hipertensão
        Hipertensao hipertensao = new Hipertensao();
        hipertensao.setPressaoSistolica(sistolica);
        hipertensao.setPressaoDiastolica(diastolica);
        hipertensao.setFrequenciaCardiaca(frequenciaCardiaca);
        hipertensao.setDataHora(dataHoraAtual);
        hipertensao.setObservacoes(hipertensaoObs);
        hipertensao.setUsuario(usuario);
        hipertensaoDao.salvarHipertencao(hipertensao, usuario);

        // Associar glicose
        Glicose glicose = new Glicose();
        glicose.setNivelGlicose(glicoseNivel);
        glicose.setDataHora(dataHoraAtual);
        glicose.setEmJejum(jejum);
        glicose.setObservacoes(glicoseObs);
        glicose.setUsuario(usuario);
        glicoseDao.salvar(glicose, usuario);

        // Criar login
        Login login = new Login();
        login.setEmail(email);
        login.setSenha(senha);
        login.setUsuario(usuario);
        loginDao.adicionarLogin(login,usuario);
    }

    public void salvarUsuario(Usuario usuario) {
        usuarioDao.criar(usuario);
    }

    public boolean verificarSenha(String email, String senha) {
        Login login = loginDao.buscarPorEmail(email);
        if (login == null) {
            System.out.println("Login não encontrado para o email: " + email);
            return false;
        }
        String senhaArmazenada = login.getSenha();
        System.out.println("Hash armazenado: " + senhaArmazenada);
        System.out.println("Senha fornecida: " + senha);

        // Verifica se o hash começa com $2a$ ou $2b$ (formato BCrypt)
        if (!senhaArmazenada.startsWith("$2")) {
            System.out.println("Hash não está no formato BCrypt!");
            return false;
        }

        BCrypt.Result result = BCrypt.verifyer().verify(senha.toCharArray(), senhaArmazenada);
        System.out.println("Resultado da verificação: " + result);
        boolean verified = result.verified;
        System.out.println("Verificação: " + verified);
        return verified;
    }

    public void atualizarUsuario(Usuario usuario) {
        EntityManager em = new EntityManagerFactoryConnection().getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Login buscarLoginPorUsuario(Usuario usuario) {
        return loginDao.buscarPorUsuario(usuario);
    }

    public void atualizarUsuarioELogin(Usuario usuario, Login login) {
        EntityManager em = new EntityManagerFactoryConnection().getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.merge(login);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
