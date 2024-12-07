package group.nine.healthsystem.view;

import group.nine.healthsystem.domain.SessaoUsuario;
import group.nine.healthsystem.service.RelatorioService;
import group.nine.healthsystem.service.RegistroService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class RelatorioController {
    @FXML
    private TextArea resumoTextArea;

    @FXML
    private Button gerarPDFButton;

    private RelatorioService relatorioService;
    private RegistroService registroService;

    @FXML
    public void initialize() {
        relatorioService = new RelatorioService();
        registroService = new RegistroService();

        carregarResumo();
    }

    private void carregarResumo() {
        try {
            var usuario = SessaoUsuario.getInstance().getUsuarioLogado();
            var medias = registroService.calcularMediasUsuario((long) usuario.getCod());

            String prompt = relatorioService.gerarPromptResumo(usuario, medias);
            String analise = relatorioService.getApiClient().sendRequest(prompt);
            resumoTextArea.setText(analise);

        } catch (Exception e) {
            e.printStackTrace();
            resumoTextArea.setText("Erro ao gerar resumo: " + e.getMessage());
        }
    }

    @FXML
    private void handleGerarPDF() {
        try {
            var usuario = SessaoUsuario.getInstance().getUsuarioLogado();
            var medias = registroService.calcularMediasUsuario((long) usuario.getCod());
            var registrosPressao = registroService.buscarRegistrosPressao(
                    (long) usuario.getCod(),
                    java.time.LocalDate.now().minusDays(30),
                    java.time.LocalDate.now()
            );
            var registrosGlicose = registroService.buscarRegistrosGlicose(
                    (long) usuario.getCod(),
                    java.time.LocalDate.now().minusDays(30),
                    java.time.LocalDate.now()
            );

            String caminhoArquivo = relatorioService.gerarRelatorioPDF(
                    usuario, medias, registrosPressao, registrosGlicose, null, null);

            resumoTextArea.setText("Relatório PDF gerado com sucesso!\nSalvo em: " + caminhoArquivo);
        } catch (Exception e) {
            e.printStackTrace();
            resumoTextArea.setText("Erro ao gerar PDF: " + e.getMessage());
        }
    }
}