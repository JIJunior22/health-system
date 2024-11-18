package group.nine.healthsystem.dao;

import group.nine.healthsystem.domain.Usuario;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;

public class UsuarioDao {
    private EntityManagerFactoryConnection em = new EntityManagerFactoryConnection();

    public EntityManagerFactoryConnection getEmc() {
        return em;
    }

    public void criar(Usuario usuario) {
        try {
            // Inicia a transação com o BD
            getEmc().getEntityManager().getTransaction().begin();
            // Realiza a percistencia na tabela
            getEmc().getEntityManager().persist(usuario);
            // Confirmação da transação
            getEmc().getEntityManager().getTransaction().commit();
        } finally {
            getEmc().getEntityManager().close();
        }
    }

    public Usuario buscarPorNome(String nome) {
        getEmc().getEntityManager().getTransaction().begin();
        var query = getEmc().getEntityManager().createNamedQuery("usuarios.getByName");
        query.setParameter("nome", nome);
        return (Usuario) query.getSingleResult();

    }

}
