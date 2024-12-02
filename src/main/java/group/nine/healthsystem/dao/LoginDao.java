package group.nine.healthsystem.dao;

import group.nine.healthsystem.domain.Login;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class LoginDao {
    private EntityManagerFactoryConnection em = new EntityManagerFactoryConnection();

    public EntityManagerFactoryConnection getEmc() {
        return em;
    }

    public void adicionarLogin(Login login) {
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

    public Login verificarLogin(String email, String senha) {
        try {
            // Criando uma query JPQL para buscar o login pelo email
            String queryString = "SELECT l FROM Login l WHERE l.email = :email AND l.senha = :senha";
            TypedQuery<Login> query = getEmc().getEntityManager().createQuery(queryString, Login.class);
            query.setParameter("email", email);
            query.setParameter("senha", senha);

            // Executando a query e retornando o resultado
            return query.getResultList().stream().findFirst().orElse(null); // Retorna o primeiro resultado ou null se não encontrar
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
