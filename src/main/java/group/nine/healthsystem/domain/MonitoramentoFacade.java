package group.nine.healthsystem.domain;


import lombok.Data;

@Data
public class MonitoramentoFacade {
    private Glicose glicose;
    private Hipertensao hipertensao;
    private Usuario usuarioIMC;


    public void pressaoArterial(double sistolica, double diastolica, String observacoes) {
        hipertensao.registrarPressao(sistolica, diastolica, observacoes);
    }

    public void glicose(double nivel, boolean jejum, String observacoes) {
        glicose.registrarNivel(nivel, jejum, observacoes);
    }

    public void imc(double altura, double peso) {
        usuarioIMC.registrarIMC(altura, peso);
    }

    public String relatorioFacade() {
        StringBuilder relatorio = new StringBuilder();

        relatorio.append("Relatório de Saúde\n");
        relatorio.append("===================\n\n");

        relatorio.append("1. Hipertensão:\n");
        relatorio.append(hipertensao.gerarRelatorioPressao()).append("\n\n");

        relatorio.append("2. Níveis de Glicose:\n");
        relatorio.append(glicose.gerarRelatorioGlicose()).append("\n\n");

        relatorio.append("3. Índice de Massa Corporal (IMC):\n");
        relatorio.append(usuarioIMC.gerarRelatorioIMC()).append("\n");

        return relatorio.toString();
    }



}


import group.nine.healthsystem.dao.GlicoseDao;
import group.nine.healthsystem.dao.HipertensaoDao;
import group.nine.healthsystem.dao.UsuarioDao;
import group.nine.healthsystem.service.ApiClient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


import java.util.List;

public class MonitoramentoFacade {

    private final GlicoseDao glicoseDao;
    private final HipertensaoDao hipertensaoDao;
    private final UsuarioDao usuarioDao;

    private final ApiClient geminiApiClient = new ApiClient();

    public MonitoramentoFacade() {
        this.glicoseDao = new GlicoseDao();
        this.hipertensaoDao = new HipertensaoDao();
        this.usuarioDao = new UsuarioDao();
    }

    public String carregarPressaoArterial(int idUsuario) {
        List<Hipertensao> pressoes = hipertensaoDao.exibirDadosPressao(idUsuario);

        if (pressoes != null && !pressoes.isEmpty()) {
            StringBuilder relatorio = new StringBuilder();
            for (Hipertensao hipertensao : pressoes) {
                relatorio.append(String.format("""
                                Paciente: %s
                                Pressão Sistólica: %.2f mmHg
                                Pressão Diastólica: %.2f mmHg
                                Data e Hora: %s
                                Observações: %s
                                """,
                        hipertensao.getUsuario().getNome(),
                        hipertensao.getPressaoSistolica(),
                        hipertensao.getPressaoDiastolica(),
                        hipertensao.getDataHora(),
                        hipertensao.getObservacoes()));
            }
            return relatorio.toString();
        }
        return "Dados de hipertensão não encontrados para o usuário com ID: " + idUsuario;
    }

    public String carregarGlicose(int idUsuario) {
        List<Glicose> glicoses = glicoseDao.listar(idUsuario);

        if (glicoses != null && !glicoses.isEmpty()) {
            StringBuilder relatorio = new StringBuilder();
            relatorio.append("Relatório de Glicose\n");
            relatorio.append("=====================\n\n");

            for (Glicose glicose : glicoses) {
                relatorio.append(String.format("""
                                Paciente: %s
                                Nível de Glicose: %.2f mg/dL
                                Data e Hora: %s
                                Em Jejum: %b
                                Observações: %s
                                """, glicose.getUsuario().getNome(),
                        glicose.getNivelGlicose(),
                        glicose.getDataHora(),
                        glicose.isEmJejum(),
                        glicose.getObservacoes()));
            }
            return relatorio.toString();
        }
        return "Dados de glicose não encontrados para o usuário com ID: " + idUsuario;
    }

    public String carregarIMC(int idUsuario) {
        Usuario usuario = usuarioDao.findById(idUsuario);
        if (usuario != null) {
            double imc = usuario.calcularIMC();
            String classificacao = usuario.classificarIMC(imc);
            return String.format("""
                    Paciente: %s
                    Peso: %.2f kg
                    Altura: %.2f m
                    IMC: %.2f
                    Classificação: %s
                    """, usuario.getNome(), usuario.getPeso(), usuario.getAltura(), imc, classificacao);
        }
        return "Dados de IMC não encontrados para o usuário com ID: " + idUsuario;
    }

    public String relatorioFacade(int idUsuario) {
        StringBuilder relatorio = new StringBuilder();

        // Cabeçalho do relatório
        relatorio.append("## Relatório de Saúde Consolidado - ").append(idUsuario).append("\n\n");
        relatorio.append("**Data:** ").append(java.time.LocalDate.now()).append("\n\n");

        // Seção 1: Hipertensão
        String pressao = carregarPressaoArterial(idUsuario);
        relatorio.append("**1. Hipertensão:**\n");
        if (!pressao.isEmpty()) {
            relatorio.append(pressao).append("\n\n");
        } else {
            relatorio.append("* Dados de hipertensão não encontrados.\n\n");
        }

        // Seção 2: Glicose
        String glicose = carregarGlicose(idUsuario);
        relatorio.append("**2. Níveis de Glicose:**\n");
        if (!glicose.isEmpty()) {
            relatorio.append(glicose).append("\n\n");
        } else {
            relatorio.append("* Dados de glicose não encontrados.\n\n");
        }

        // Seção 3: IMC
        String imc = carregarIMC(idUsuario);
        relatorio.append("**3. Índice de Massa Corporal (IMC):**\n");
        if (!imc.isEmpty()) {
            relatorio.append(imc).append("\n\n");
        } else {
            relatorio.append("* Dados de IMC não encontrados.\n\n");
        }

        return relatorio.toString();
    }

    public void enviarRelatorioGemini(int idUsuario) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("healthsystem");
        EntityManager em=emf.createEntityManager();

        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }

        String relatorio = relatorioFacade(idUsuario);
        System.out.println("Relatório Local:");
        System.out.println(relatorio);

        try {
            System.out.println("\nEnviando para a API do Gemini...");
            String resposta = geminiApiClient.sendRequest(relatorio);
            System.out.println("\nResposta da API do Gemini:");
            System.out.println(resposta);
        } catch (Exception e) {
            System.err.println("Erro inesperado ao se comunicar com a API do Gemini: " + e.getMessage());
        }
    }
}