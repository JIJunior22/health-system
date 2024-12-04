package group.nine.healthsystem.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import group.nine.healthsystem.domain.Login;
import group.nine.healthsystem.domain.Usuario;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LoginDao {
    private EntityManagerFactoryConnection em = new EntityManagerFactoryConnection();
    private EntityManagerFactory emf;

    public EntityManagerFactoryConnection getEmc() {
        this.emf = Persistence.createEntityManagerFactory("healthSystem");
        return em;
    }

    public void adicionarLogin(Login login, Usuario usuario) {
        EntityManager em = getEmc().getEntityManager();

        try {
            // Criptografa a senha do usuário
            String senhaCriptografada = BCrypt.withDefaults().hashToString(12, login.getSenha().toCharArray());
            login.setSenha(senhaCriptografada);

            // Associa o login ao usuário
            login.setUsuario(usuario);

            // Inicia a transação
            em.getTransaction().begin();
            em.persist(login);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void deletarLogin(String login) {
        getEmc().getEntityManager().getTransaction().begin();
        var query = getEmc().getEntityManager().createNamedQuery("getAll", Login.class);

        List<Login> logins = query.getResultList();

        for (Login l : logins) {
            if (l.getEmail().equals(login)) {
                getEmc().getEntityManager().remove(l);
                getEmc().getEntityManager().getTransaction().commit();
                getEmc().getEntityManager().close();
            }

        }

    }

    public void atualizarDadosLogin(Login login) {
        getEmc().getEntityManager().getTransaction().begin();
        getEmc().getEntityManager().merge(login);
        getEmc().getEntityManager().getTransaction().commit();
        getEmc().getEntityManager().close();

    }


    public List<Usuario> listar() {
        var query = getEmc().getEntityManager().createNamedQuery("usuarios.listar", Usuario.class);
        return query.getResultList();
    }

    public Login exibirLoginPorId(int id) {
        Login login = findById(id);

        if (login == null) {
            System.out.println("Usuário não encontrado.");
        } else {
            System.out.println(String.format("""
                            ╔══════════════════════════════════════╗
                            ║          Detalhes do Usuário         ║
                            ╠══════════════════════════════════════╣
                            ║                                      ║ 
                            ║ Nome: %s                             ║
                            ║ Email: %s                            ║
                            ║                                      ║
                            ║                                      ║
                            ╚══════════════════════════════════════╝
                            """,
                    login.getUsuario().getNome(), login.getEmail()
            ));
        }
        return login;
    }


    public Login findById(int id) {
        getEmc().getEntityManager().getTransaction().begin();
        return getEmc().getEntityManager().find(Login.class, id);
    }

    public Login findByEmailSenha(String email, String senha) {
        EntityManager em = getEmc().getEntityManager();

        try {

            var query = em.createQuery("SELECT l FROM Login l WHERE l.email = :email", Login.class);

            query.setParameter("email", email);

            Login login = query.getSingleResult();

            // Verifica se a senha fornecida corresponde ao hash armazenado
            if (BCrypt.verifyer().verify(senha.toCharArray(), login.getSenha()).verified) {
                return login;
            } else {
                System.out.println("Senha inválida.");
                return null;
            }
        } catch (NoResultException e) {
            System.out.println("Nenhum login encontrado para o email: " + email);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean validarLogin(String email, String senha) {

        if (email == null || senha == null || email.isEmpty() || senha.isEmpty()) {
            System.out.println("Email ou senha não podem ser vazios.");
            return false;
        }

        Login login = findByEmailSenha(email, senha);

        if (login == null) {
            System.out.println("Login ou senha inválidos.");
            return false;
        }

        System.out.println("Login bem-sucedido.");
        return true;
    }

    public Usuario validarLoginRetornandoUsuario(String email, String senha) {
        EntityManager em = getEmc().getEntityManager();
        try {
            var query = em.createQuery(
                    "SELECT u FROM Usuario u JOIN u.login l WHERE l.email = :email",
                    Usuario.class);
            query.setParameter("email", email);

            Usuario usuario = query.getSingleResult();
            if (BCrypt.verifyer().verify(senha.toCharArray(), usuario.getLogin().getSenha()).verified) {
                return usuario;
            }
        } catch (NoResultException e) {
            System.out.println("Usuário não encontrado");
        } finally {
            em.close();
        }
        return null;
    }


}




