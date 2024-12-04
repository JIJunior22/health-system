package group.nine.healthsystem.view;

import group.nine.healthsystem.domain.SessaoUsuario;
import group.nine.healthsystem.domain.Usuario;
import group.nine.healthsystem.service.RegistroService;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import java.time.LocalDate;
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
    private StackedBarChart<String, Number> glicoseChart;

    private RegistroService registroService;
    private Usuario usuarioAtual;

    @FXML
    public void initialize() {
        registroService = new RegistroService();
        usuarioAtual = SessaoUsuario.getInstance().getUsuarioLogado();

        if (usuarioAtual != null) {
            atualizarMedias();
            atualizarGraficos();
        }
    }

    private void atualizarMedias() {
        try {
            // Busca todas as médias do usuário
            Map<String, Double> medias = registroService.calcularMediasUsuario((long) usuarioAtual.getCod()); // Alterado de getId para getIdUsuario

            // Atualiza os labels com as médias formatadas, com tratamento para valores nulos
            if (medias.get("pressao") != null) {
                pressaoMediaLabel.setText(String.format("%.0f", medias.get("pressao")));
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

            // Como não temos IMC no modelo atual, podemos remover ou deixar como "-"
            imcMediaLabel.setText("-");
        } catch (Exception e) {
            e.printStackTrace();
            // Em caso de erro, exibe "-" em todos os campos
            pressaoMediaLabel.setText("-");
            glicemiaMediaLabel.setText("-");
            freqCardiacaMediaLabel.setText("-");
            imcMediaLabel.setText("-");
        }
    }

    private void atualizarGraficos() {
        try {
            // Limpa os gráficos antes de adicionar novos dados
            pressaoChart.getData().clear();
            glicoseChart.getData().clear();

            // Busca dados dos últimos 7 dias
            LocalDate dataFim = LocalDate.now();
            LocalDate dataInicio = dataFim.minusDays(6);

            // Dados de pressão
            XYChart.Series<String, Number> seriePressaoSistolica = new XYChart.Series<>();
            seriePressaoSistolica.setName("Sistólica");
            XYChart.Series<String, Number> seriePressaoDiastolica = new XYChart.Series<>();
            seriePressaoDiastolica.setName("Diastólica");

            List<Map<String, Object>> dadosPressao = registroService.buscarRegistrosPressao((long)
                    usuarioAtual.getCod(), dataInicio, dataFim);

            for (Map<String, Object> registro : dadosPressao) {
                String data = registro.get("data").toString();
                seriePressaoSistolica.getData().add(
                        new XYChart.Data<>(data, (Number)registro.get("sistolica")));
                seriePressaoDiastolica.getData().add(
                        new XYChart.Data<>(data, (Number)registro.get("diastolica")));
            }

            pressaoChart.getData().addAll(seriePressaoSistolica, seriePressaoDiastolica);

            // Dados de glicose
            XYChart.Series<String, Number> serieGlicose = new XYChart.Series<>();
            serieGlicose.setName("Glicose");

            List<Map<String, Object>> dadosGlicose = registroService.buscarRegistrosGlicose( (long)
                    usuarioAtual.getCod(), dataInicio, dataFim); // Alterado de getId para getIdUsuario

            for (Map<String, Object> registro : dadosGlicose) {
                String data = registro.get("data").toString();
                serieGlicose.getData().add(
                        new XYChart.Data<>(data, (Number)registro.get("valor")));
            }

            glicoseChart.getData().add(serieGlicose);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}