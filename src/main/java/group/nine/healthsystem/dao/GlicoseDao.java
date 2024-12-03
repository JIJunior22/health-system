package group.nine.healthsystem.dao;

import group.nine.healthsystem.domain.Glicose;
import group.nine.healthsystem.domain.Login;
import group.nine.healthsystem.domain.Usuario;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class GlicoseDao {
    private EntityManagerFactoryConnection em = new EntityManagerFactoryConnection();

    public EntityManagerFactoryConnection getEm() {
        return em;
    }

    public void salvar(Glicose glicose, Usuario usuario) {
        // Associa a glicose ao usuário
        glicose.setUsuario(usuario);
        getEm().getEntityManager().getTransaction().begin();
        getEm().getEntityManager().persist(glicose);
        getEm().getEntityManager().getTransaction().commit();
        System.out.println("Dados salvos no banco de dados");
        getEm().getEntityManager().close();
    }

    public List<Glicose> listar(int idUsuario) {
        getEm().getEntityManager().getTransaction().begin();
        var query = getEm().getEntityManager()
                .createQuery("SELECT g FROM Glicose g WHERE g.usuario.cod = :idUsuario", Glicose.class);
        query.setParameter("idUsuario", idUsuario);

        return query.getResultList();
    }



    public void exibirDados(int usuario) {
        List<Glicose> glicoses = listar(usuario);

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

    public Glicose findById(int id) {
        getEm().getEntityManager().getTransaction().begin();
        getEm().getEntityManager().close();
        return getEm().getEntityManager().find(Glicose.class, id);

    }

    public void deleteById(int usuario) {
        var codigoUsuario = findById(usuario);

        getEm().getEntityManager().remove(usuario);
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
