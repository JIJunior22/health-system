package group.nine.healthsystem.dao;

import group.nine.healthsystem.domain.Glicose;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;

import java.util.List;

public class GlicoseDao {
    private EntityManagerFactoryConnection em = new EntityManagerFactoryConnection();

    public EntityManagerFactoryConnection getEm() {
        return em;
    }

    public void salvar(Glicose glicose) {
        getEm().getEntityManager().getTransaction().begin();
        getEm().getEntityManager().persist(glicose);
        getEm().getEntityManager().getTransaction().commit();
        System.out.println("Dados salvos no banco de dados");
        getEm().getEntityManager().close();
    }

    public List<Glicose> listar() {
        getEm().getEntityManager().getTransaction().begin();
        var query = getEm().getEntityManager().createNamedQuery("glicoses.listarTodos");
        return query.getResultList();
    }

    public void exibirDados() {
        List<Glicose> glicoses = listar();

        if (glicoses.isEmpty()) {
            System.out.println("Nenhuma glicose encontrada.");
        } else {
            System.out.println("Lista de glicose encontrada:");
            for (Glicose glicose : glicoses) {
                System.out.printf("""
                                Nível de Glicose: %.2f mg/dL
                                Data e Hora: %s
                                Em Jejum: %b
                                Observações: %s
                                --------------------------
                                """,
                        glicose.getNivelGlicose(),
                        glicose.getDataHora(),
                        glicose.isEmJejum(),
                        glicose.getObservacoes());
            }
        }
    }

    public Glicose findById(Long id) {
        getEm().getEntityManager().getTransaction().begin();
        return getEm().getEntityManager().find(Glicose.class, id);

    }

    public void deleteById(Long id) {
        var codigoUsuario = findById(id);

        getEm().getEntityManager().remove(id);
        getEm().getEntityManager().getTransaction().commit();
        getEm().getEntityManager().close();
        System.out.println("Dado removido com sucesso.");
    }

    public void atualizarGlicose(Glicose glicose) {
        getEm().getEntityManager().getTransaction().begin();
        getEm().getEntityManager().merge(glicose);
        getEm().getEntityManager().getTransaction().commit();
        getEm().getEntityManager().close();
        System.out.println("Atualização realizada.");
    }
}
