package group.nine.healthsystem.view;

import group.nine.healthsystem.domain.SessaoUsuario;
import group.nine.healthsystem.domain.Usuario;
import group.nine.healthsystem.service.RegistroService;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class HomeController {

    @FXML
    private Label pressaoMediaLabel;

    @FXML
    private Label glicemiaMediaLabel;

    @FXML
    private Label freqCardiacaMediaLabel;

    @FXML
    private Label imcMediaLabel;

    @FXML
    private BarChart<String, Number> pressaoChart;

    @FXML
    private BarChart<String, Number> glicoseChart;

    private RegistroService registroService;
    private Usuario usuarioAtual;

    @FXML
    public void initialize() {
        registroService = new RegistroService();
        usuarioAtual = SessaoUsuario.getInstance().getUsuarioLogado();

        if (usuarioAtual != null) {
            System.out.println("Usuário logado: " + usuarioAtual.getCod() + " - " + usuarioAtual.getNome());
            atualizarMedias();
            atualizarGraficos();
        } else {
            System.err.println("Nenhum usuário logado!");
        }
    }

    private void atualizarMedias() {
        try {
            System.out.println("Atualizando médias para usuário: " + usuarioAtual.getCod());
            Map<String, Double> medias = registroService.calcularMediasUsuario((long) usuarioAtual.getCod());

            System.out.println("Médias recebidas: " + medias);

            // Atualiza os labels com as médias formatadas
            if (medias.get("pressao_sistolica") != null && medias.get("pressao_diastolica") != null) {
                String pressaoFormatada = String.format("%.0f/%.0f",
                        medias.get("pressao_sistolica"),
                        medias.get("pressao_diastolica"));
                pressaoMediaLabel.setText(pressaoFormatada);
            } else {
                pressaoMediaLabel.setText("-");
            }

            if (medias.get("glicemia") != null) {
                glicemiaMediaLabel.setText(String.format("%.0f", medias.get("glicemia")));
            } else {
                glicemiaMediaLabel.setText("-");
            }

            if (medias.get("frequencia") != null) {
                freqCardiacaMediaLabel.setText(String.format("%.0f", medias.get("frequencia")));
            } else {
                freqCardiacaMediaLabel.setText("-");
            }

            // Calcula e atualiza o IMC
            try {
                double imc = usuarioAtual.calcularIMC();
                usuarioAtual.setImc(imc);
                imcMediaLabel.setText(String.format("%.1f", imc));
            } catch (IllegalStateException e) {
                System.err.println("Erro ao calcular IMC: " + e.getMessage());
                imcMediaLabel.setText("-");
            }

        } catch (Exception e) {
            System.err.println("Erro ao atualizar médias: " + e.getMessage());
            e.printStackTrace();
            pressaoMediaLabel.setText("-");
            glicemiaMediaLabel.setText("-");
            freqCardiacaMediaLabel.setText("-");
            imcMediaLabel.setText("-");
        }
    }

    private void atualizarGraficos() {
        try {
            // Verificar se os gráficos foram injetados corretamente
            if (pressaoChart == null || glicoseChart == null) {
                System.err.println("Erro: Gráficos não foram inicializados corretamente");
                System.err.println("pressaoChart: " + (pressaoChart == null ? "nulo" : "ok"));
                System.err.println("glicoseChart: " + (glicoseChart == null ? "nulo" : "ok"));
                return;
            }

            System.out.println("Iniciando atualização dos gráficos...");

            // Limpa os gráficos antes de adicionar novos dados
            try {
                pressaoChart.getData().clear();
                System.out.println("Gráfico de pressão limpo com sucesso");
            } catch (Exception e) {
                System.err.println("Erro ao limpar gráfico de pressão: " + e.getMessage());
            }

            // Busca dados dos últimos 2 dias
            LocalDate dataFim = LocalDate.now();
            LocalDate dataInicio = dataFim.minusDays(1);

            System.out.println("Buscando dados de " + dataInicio + " até " + dataFim);

            // Dados de pressão
            List<Map<String, Object>> dadosPressao = registroService.buscarRegistrosPressao(
                    (long) usuarioAtual.getCod(), dataInicio, dataFim);

            System.out.println("Dados de pressão encontrados: " + dadosPressao.size());
            System.out.println("Dados de pressão: " + dadosPressao);

            XYChart.Series<String, Number> seriePressaoSistolica = new XYChart.Series<>();
            seriePressaoSistolica.setName("Sistólica");
            XYChart.Series<String, Number> seriePressaoDiastolica = new XYChart.Series<>();
            seriePressaoDiastolica.setName("Diastólica");

            for (Map<String, Object> registro : dadosPressao) {
                try {
                    // Formatar a data para mostrar dia e hora
                    Object dataObj = registro.get("data");
                    String dataFormatada;

                    if (dataObj instanceof LocalDateTime) {
                        dataFormatada = ((LocalDateTime) dataObj).format(DateTimeFormatter.ofPattern("dd/MM HH:mm"));
                    } else {
                        System.out.println("Data em formato inesperado: " + dataObj.getClass().getName());
                        dataFormatada = dataObj.toString();
                    }

                    Number sistolica = (Number) registro.get("sistolica");
                    Number diastolica = (Number) registro.get("diastolica");

                    System.out.println("Adicionando pressão - Data: " + dataFormatada +
                            ", Sistólica: " + sistolica +
                            ", Diastólica: " + diastolica);

                    seriePressaoSistolica.getData().add(new XYChart.Data<>(dataFormatada, sistolica));
                    seriePressaoDiastolica.getData().add(new XYChart.Data<>(dataFormatada, diastolica));
                } catch (Exception e) {
                    System.err.println("Erro ao processar registro: " + registro);
                    e.printStackTrace();
                }
            }

            pressaoChart.getData().add(seriePressaoSistolica);
            pressaoChart.getData().add(seriePressaoDiastolica);

            // Após criar as séries
            System.out.println("Série Sistólica criada: " + seriePressaoSistolica.getName());
            System.out.println("Série Diastólica criada: " + seriePressaoDiastolica.getName());

            // Após adicionar ao gráfico
            System.out.println("Total de séries no gráfico: " + pressaoChart.getData().size());
            System.out.println("Pontos na série sistólica: " + seriePressaoSistolica.getData().size());
            System.out.println("Pontos na série diastólica: " + seriePressaoDiastolica.getData().size());

            // Dados de glicose
            List<Map<String, Object>> dadosGlicose = registroService.buscarRegistrosGlicose(
                    (long) usuarioAtual.getCod(), dataInicio, dataFim);

            System.out.println("Dados de glicose encontrados: " + dadosGlicose.size());
            System.out.println("Dados de glicose: " + dadosGlicose);

            XYChart.Series<String, Number> serieGlicose = new XYChart.Series<>();
            serieGlicose.setName("Glicose");

            for (Map<String, Object> registro : dadosGlicose) {
                String data = registro.get("data").toString();
                Number valor = (Number) registro.get("valor");

                System.out.println("Adicionando glicose - Data: " + data + ", Valor: " + valor);

                serieGlicose.getData().add(new XYChart.Data<>(data, valor));
            }

            glicoseChart.getData().add(serieGlicose);

        } catch (Exception e) {
            System.err.println("Erro ao atualizar gráficos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}