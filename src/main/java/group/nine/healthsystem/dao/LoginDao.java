package group.nine.healthsystem.dao;

import group.nine.healthsystem.domain.Login;
import group.nine.healthsystem.domain.Usuario;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.List;

public class LoginDao {
    private EntityManagerFactoryConnection em = new EntityManagerFactoryConnection();
    private EntityManagerFactory emf;

    public EntityManagerFactoryConnection getEmc() {
        this.emf = Persistence.createEntityManagerFactory("healthsystem");
        return em;
    }

    public void adicionarLogin(Login login, Usuario usuario) {
        login.setUsuario(usuario);
        getEmc().getEntityManager().getTransaction().begin();
        getEmc().getEntityManager().persist(login);
        getEmc().getEntityManager().getTransaction().commit();
        getEmc().getEntityManager().close();

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

    public Usuario exibirUsuarioPorId(int id) {
        Usuario usuario = findById(id);

        if (usuario == null) {
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
                    usuario.getNome(), usuario.getEmail()
            ));
        }
        return usuario;
    }


    public Usuario findById(int id) {
        getEmc().getEntityManager().getTransaction().begin();
        return getEmc().getEntityManager().find(Usuario.class, id);
    }
    public Login findByEmailSenha(String email, String senha) {
        try {
            var query = getEmc().getEntityManager()
                    .createQuery("SELECT l FROM Login l WHERE l.login = :email AND l.senha = :senha", Login.class);
            query.setParameter("email", email);
            query.setParameter("senha", senha);

            Login login = query.getSingleResult();

            if (login != null) {
                System.out.println("Login encontrado para o usuario: " + login.getLogin());
            }

            return login;
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



}




