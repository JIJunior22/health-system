package group.nine.healthsystem.dao;

import group.nine.healthsystem.domain.Glicose;
import group.nine.healthsystem.domain.Hipertensao;
import group.nine.healthsystem.domain.Usuario;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;
import jakarta.persistence.NoResultException;

import java.util.List;

public class HipertensaoDao {
    private EntityManagerFactoryConnection em = new EntityManagerFactoryConnection();

    public EntityManagerFactoryConnection getEmc() {
        return em;

    }

    public void salvarHipertencao(Hipertensao hipertensao, Usuario usuario) {
        hipertensao.setUsuario(usuario);
        getEmc().getEntityManager().getTransaction().begin();
        getEmc().getEntityManager().persist(hipertensao);
        getEmc().getEntityManager().getTransaction().commit();
        getEmc().getEntityManager().close();
        System.out.println("Dados salvos com sucesso.");
    }

    public List<Hipertensao> listarTodos() {
        getEmc().getEntityManager().getTransaction().begin();
        var query = getEmc().getEntityManager().createNamedQuery("hipertencoes.listarTodos");
        return query.getResultList();
    }

    public List<Hipertensao> exibirDadosPressao(int id) {

        //getEmc().getEntityManager().getTransaction().begin();
        var query = getEmc().getEntityManager()
                .createQuery("SELECT g FROM Hipertensao g WHERE g.usuario.cod = :idUsuario", Hipertensao.class);
        query.setParameter("idUsuario", id);
        return query.getResultList();
    }


    public Hipertensao findById(int id) {
        try{
            getEmc().getEntityManager().getTransaction().begin();
            return getEmc().getEntityManager().find(Hipertensao.class, id);
        }finally {
            getEmc().getEntityManager().close();
        }

    }


    public void deleteById(int id) {
        getEmc().getEntityManager().remove(id);
        getEmc().getEntityManager().getTransaction().commit();
        getEmc().getEntityManager().close();
        System.out.println("Dado removido com sucesso.");
    }

    public void atualizar(Hipertensao hipertensao) {
        getEmc().getEntityManager().getTransaction().begin();
        getEmc().getEntityManager().merge(hipertensao);
        getEmc().getEntityManager().getTransaction().commit();
        getEmc().getEntityManager().close();
    }

}