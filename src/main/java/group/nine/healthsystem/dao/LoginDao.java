package group.nine.healthsystem.dao;

import group.nine.healthsystem.domain.Login;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;
import jakarta.persistence.NoResultException;

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
            // Inicia a transação
            getEmc().getEntityManager().getTransaction().begin();

            // Query JPQL para buscar o usuário
            var query = getEmc().getEntityManager()
                    .createQuery("SELECT l FROM Login l WHERE l.email = :email AND l.senha = :senha", Login.class);
            query.setParameter("email", email);
            query.setParameter("senha", senha);

            // Obtém a lista de resultados
            List<Login> resultList = query.getResultList();

            // Verifica se encontrou algum resultado
            if (resultList.isEmpty()) {
                return null;  // Nenhum usuário encontrado
            }

            // Retorna o primeiro resultado (usuário)
            getEmc().getEntityManager().getTransaction().commit();
            return resultList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Rollback em caso de erro
            if (getEmc().getEntityManager().getTransaction().isActive()) {
                getEmc().getEntityManager().getTransaction().rollback();
            }
        }
    }


}
