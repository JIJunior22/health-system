package group.nine.healthsystem.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Objects;

public class EntityManagerFactoryConnection {
    private EntityManagerFactory em = Persistence.createEntityManagerFactory("healthsystem");

    // Declara o EntityManager, que será inicializado quando necessário.
    private EntityManager entityManager;

    // Retorna a instância do EntityManager, criando-a se necessário.
    public EntityManager getEntityManager() {
        // Cria o entityManager se ainda não foi inicializado.
        if (Objects.isNull(entityManager)) {
            entityManager = em.createEntityManager();
        }
        return entityManager;
    }
}
