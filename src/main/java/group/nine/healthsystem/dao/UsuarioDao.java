package group.nine.healthsystem.dao;

import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;

public class UsuarioDao {
    private EntityManagerFactoryConnection em = new EntityManagerFactoryConnection();

    public EntityManagerFactoryConnection getEntityManager(){
        return em;
    }
}
