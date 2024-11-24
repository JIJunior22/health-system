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
            // Realiza a persistencia na tabela
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

    public Usuario findById(Long id) {
        getEmc().getEntityManager().getTransaction().begin();
        return getEmc().getEntityManager().find(Usuario.class, id);

    }

    public void deleteById(Long id) {
        var usuario = findById(id);
        getEmc().getEntityManager().getTransaction().begin();
        getEmc().getEntityManager().remove(usuario);
        getEmc().getEntityManager().getTransaction().commit();
        getEmc().getEntityManager().close();
    }

    public void atualizarUsuario(Usuario usuario) {
        getEmc().getEntityManager().getTransaction().begin();
        getEmc().getEntityManager().merge(usuario);
        getEmc().getEntityManager().getTransaction().commit();
        getEmc().getEntityManager().close();
    }


}
