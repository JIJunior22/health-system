package group.nine.healthsystem.dao;

import group.nine.healthsystem.domain.Hipertensao;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;

import java.util.List;

public class HipertensaoDao {
    private EntityManagerFactoryConnection em = new EntityManagerFactoryConnection();

    public EntityManagerFactoryConnection getEmc() {
        return em;

    }

    public void salvarHipertencao(Hipertensao hipertensao) {
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

    public void exibirDadosPressao() {

        List<Hipertensao> pressoes = listarTodos();

        if (pressoes.isEmpty()) {
            System.out.println("Nenhum registro de pressão arterial encontrado.");
        } else {
            System.out.println("Lista de registros de pressão arterial:");
            for (Hipertensao pressao : pressoes) {
                System.out.printf("""
                                Pressão Sistólica: %.2f mmHg
                                Pressão Diastólica: %.2f mmHg
                                Data e Hora: %s
                                Observações: %s
                                --------------------------
                                """,
                        pressao.getPressaoSistolica(),
                        pressao.getPressaoDiastolica(),
                        pressao.getDataHora(),
                        pressao.getObservacoes());
            }
        }

    }

    public Hipertensao findById(Long id) {
        getEmc().getEntityManager().getTransaction().begin();
        return getEmc().getEntityManager().find(Hipertensao.class, id);
    }

    public void deleteById(Long id) {
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
