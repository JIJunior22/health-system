package group.nine.healthsystem.dao;

import at.favre.lib.crypto.bcrypt.BCrypt;
import group.nine.healthsystem.domain.Login;
import group.nine.healthsystem.domain.Usuario;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

public class LoginDao {
    private EntityManagerFactoryConnection em = new EntityManagerFactoryConnection();
    private EntityManagerFactory emf;


    public EntityManagerFactoryConnection getEmc() {
        this.emf = Persistence.createEntityManagerFactory("healthsystem");
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


    public List<Usuario> listar() {
        var query = getEmc().getEntityManager().createNamedQuery("usuarios.listar", Usuario.class);
        return query.getResultList();
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

    public boolean updatePassword(int id, String novaSenha) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("healthsystem");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            // Busca o usuário pelo ID
            Login login = em.find(Login.class, id);

            if (login == null) {
                System.out.println("Usuário não encontrado.");
                return false;
            }

            String senhaHash = BCrypt.withDefaults().hashToString(12, novaSenha.toCharArray());

            login.setSenha(senhaHash);
            em.merge(login);
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void atualizarSenha(int userId, String novaSenha) {

        boolean sucesso = updatePassword(userId, novaSenha);
        if (sucesso) {
            System.out.println("Senha alterada com sucesso.");
        } else {
            System.out.println("Falha ao alterar a senha.");
        }


    }


}



