package group.nine.healthsystem.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import group.nine.healthsystem.persistence.EntityManagerFactoryConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroService {

    public Map<String, Double> calcularMediasUsuario(Long usuarioId) {
        Map<String, Double> medias = new HashMap<>();
        EntityManager em = new EntityManagerFactoryConnection().getEntityManager();

        try {
            // Média de Pressão e Frequência
            String jpqlPressao = """
                SELECT 
                    AVG(h.pressaoSistolica),
                    AVG(h.pressaoDiastolica),
                    AVG(h.frequenciaCardiaca)
                FROM Hipertensao h
                WHERE h.usuario.id = :usuarioId
            """;
            TypedQuery<Object[]> queryPressao = em.createQuery(jpqlPressao, Object[].class);
            queryPressao.setParameter("usuarioId", usuarioId);
            Object[] resultPressao = queryPressao.getSingleResult();

            if (resultPressao[0] != null && resultPressao[1] != null) {
                medias.put("pressao_sistolica", (Double) resultPressao[0]);
                medias.put("pressao_diastolica", (Double) resultPressao[1]);
            }
            if (resultPressao[2] != null) {
                medias.put("frequencia", (Double) resultPressao[2]);
            }

            // Média de Glicose
            String jpqlGlicose = """
                SELECT AVG(g.nivelGlicose)
                FROM Glicose g
                WHERE g.usuario.id = :usuarioId
            """;
            TypedQuery<Double> queryGlicose = em.createQuery(jpqlGlicose, Double.class);
            queryGlicose.setParameter("usuarioId", usuarioId);
            Double mediaGlicose = queryGlicose.getSingleResult();

            if (mediaGlicose != null) {
                medias.put("glicemia", mediaGlicose);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return medias;
    }

    public List<Map<String, Object>> buscarRegistrosPressao(Long usuarioId, LocalDate dataInicio, LocalDate dataFim) {
        List<Map<String, Object>> registros = new ArrayList<>();
        EntityManager em = new EntityManagerFactoryConnection().getEntityManager();

        try {
            String jpql = """
                SELECT h.dataHora, h.pressaoSistolica, h.pressaoDiastolica
                FROM Hipertensao h
                WHERE h.usuario.id = :usuarioId
                AND CAST(h.dataHora AS LocalDate) BETWEEN :dataInicio AND :dataFim
                ORDER BY h.dataHora
            """;

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("usuarioId", usuarioId);
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);

            List<Object[]> resultados = query.getResultList();
            System.out.println("Resultados encontrados: " + resultados.size());

            for (Object[] resultado : resultados) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("data", resultado[0]);
                registro.put("sistolica", resultado[1]);
                registro.put("diastolica", resultado[2]);
                registros.add(registro);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar registros de pressão: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }

        return registros;
    }

    public List<Map<String, Object>> buscarRegistrosGlicose(Long usuarioId, LocalDate dataInicio, LocalDate dataFim) {
        List<Map<String, Object>> registros = new ArrayList<>();
        EntityManager em = new EntityManagerFactoryConnection().getEntityManager();

        try {
            String jpql = """
                SELECT g.dataHora, g.nivelGlicose
                FROM Glicose g
                WHERE g.usuario.id = :usuarioId
                AND CAST(g.dataHora AS LocalDate) BETWEEN :dataInicio AND :dataFim
                ORDER BY g.dataHora
            """;

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("usuarioId", usuarioId);
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);

            List<Object[]> resultados = query.getResultList();

            for (Object[] resultado : resultados) {
                Map<String, Object> registro = new HashMap<>();
                registro.put("data", resultado[0]);
                registro.put("valor", resultado[1]);
                registros.add(registro);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar registros de glicose: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }

        return registros;
    }
}
