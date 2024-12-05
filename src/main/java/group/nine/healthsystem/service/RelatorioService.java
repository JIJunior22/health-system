package group.nine.healthsystem.service;

import group.nine.healthsystem.domain.Usuario;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import javafx.scene.chart.BarChart;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.List;

public class RelatorioService {
    private final ApiClient apiClient;

    public RelatorioService() {
        this.apiClient = new ApiClient();
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public String gerarPromptResumo(Usuario usuario, Map<String, Double> medias) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Faça uma análise breve e direta dos seguintes valores médios:\n\n");

        if (medias.get("pressao_sistolica") != null && medias.get("pressao_diastolica") != null) {
            prompt.append("Pressão Arterial: ")
                    .append(String.format("%.0f/%.0f mmHg",
                            medias.get("pressao_sistolica"),
                            medias.get("pressao_diastolica")))
                    .append("\n");
        }
        if (medias.get("glicemia") != null) {
            prompt.append("Glicemia: ")
                    .append(String.format("%.0f mg/dL", medias.get("glicemia")))
                    .append("\n");
        }
        if (medias.get("frequencia") != null) {
            prompt.append("Frequência Cardíaca: ")
                    .append(String.format("%.0f bpm", medias.get("frequencia")))
                    .append("\n");
        }

        prompt.append("\nIndique apenas se os valores estão normais ou alterados e qual a principal recomendação.");
        return prompt.toString();
    }

    public String gerarPromptDetalhado(Usuario usuario, Map<String, Double> medias,
                                       List<Map<String, Object>> registrosPressao,
                                       List<Map<String, Object>> registrosGlicose) {

        StringBuilder prompt = new StringBuilder();
        prompt.append("Atue como um profissional de saúde e faça uma análise detalhada para auxiliar no diagnóstico médico:\n\n");

        // Dados do paciente
        prompt.append("PACIENTE: ").append(usuario.getNome()).append("\n\n");

        // Médias
        prompt.append("VALORES MÉDIOS:\n");
        if (medias.get("pressao_sistolica") != null && medias.get("pressao_diastolica") != null) {
            prompt.append("- Pressão Arterial média: ")
                    .append(String.format("%.0f/%.0f mmHg",
                            medias.get("pressao_sistolica"),
                            medias.get("pressao_diastolica")))
                    .append("\n");
        }
        if (medias.get("glicemia") != null) {
            prompt.append("- Glicemia média: ")
                    .append(String.format("%.0f mg/dL", medias.get("glicemia")))
                    .append("\n");
        }
        if (medias.get("frequencia") != null) {
            prompt.append("- Frequência Cardíaca média: ")
                    .append(String.format("%.0f bpm", medias.get("frequencia")))
                    .append("\n");
        }

        // Histórico de registros
        prompt.append("\nHISTÓRICO DE REGISTROS:\n");
        prompt.append("Pressão Arterial:\n");
        for (Map<String, Object> registro : registrosPressao) {
            prompt.append(String.format("- %s: %s/%s mmHg\n",
                    registro.get("data"),
                    registro.get("sistolica"),
                    registro.get("diastolica")));
        }

        prompt.append("\nGlicemia:\n");
        for (Map<String, Object> registro : registrosGlicose) {
            prompt.append(String.format("- %s: %s mg/dL\n",
                    registro.get("data"),
                    registro.get("valor")));
        }

        prompt.append("\nPor favor, forneça:\n")
                .append("1. Análise dos valores médios e sua variação\n")
                .append("2. Identificação de padrões nos registros\n")
                .append("3. Possíveis riscos à saúde\n")
                .append("4. Recomendações específicas para acompanhamento médico\n")
                .append("5. Sugestões de exames complementares se necessário\n");

        return prompt.toString();
    }

    public String gerarRelatorioPDF(Usuario usuario, Map<String, Double> medias,
                                    List<Map<String, Object>> registrosPressao,
                                    List<Map<String, Object>> registrosGlicose,
                                    BarChart<String, Number> pressaoChart,
                                    BarChart<String, Number> glicoseChart) throws Exception {

        // Criar diretório para relatórios se não existir
        String diretorio = "relatorios";
        java.io.File dir = new java.io.File(diretorio);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Gerar nome do arquivo com timestamp
        String nomeArquivo = String.format("%s/relatorio_%s.pdf",
                diretorio,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        try {
            float margin = 50;
            float width = page.getMediaBox().getWidth() - 2 * margin;
            float startY = page.getMediaBox().getHeight() - margin;
            float yPosition = startY;
            float fontSize = 12;
            float leading = 1.5f * fontSize;

            // Título
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(FontName.HELVETICA_BOLD), 16);
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Relatório de Saúde");
            contentStream.endText();
            yPosition -= leading;

            // Data e nome do paciente
            contentStream.beginText();
            contentStream.setFont(new PDType1Font(FontName.HELVETICA), fontSize);
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Data: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            contentStream.endText();
            yPosition -= leading;

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(FontName.HELVETICA), fontSize);
            contentStream.newLineAtOffset(margin, yPosition);
            contentStream.showText("Paciente: " + usuario.getNome());
            contentStream.endText();
            yPosition -= leading * 2; // Espaço extra antes da análise

            // Análise da IA
            String analiseIA = apiClient.sendRequest(gerarPromptDetalhado(usuario, medias, registrosPressao, registrosGlicose));
            String[] linhas = analiseIA.split("\n");

            PDType1Font font = new PDType1Font(FontName.HELVETICA);

            for (String linha : linhas) {
                // Verifica se precisa de uma nova página
                if (yPosition < margin + leading) {
                    contentStream.close();
                    PDPage newPage = new PDPage();
                    document.addPage(newPage);
                    contentStream = new PDPageContentStream(document, newPage);
                    yPosition = startY;
                }

                // Quebra linha se for muito longa
                if (linha.length() > 0) {
                    // Limita o tamanho da linha baseado na largura da página
                    int maxCharsPerLine = 90; // Ajuste este valor conforme necessário
                    for (int i = 0; i < linha.length(); i += maxCharsPerLine) {
                        int endIndex = Math.min(i + maxCharsPerLine, linha.length());
                        String subLinha = linha.substring(i, endIndex);

                        contentStream.beginText();
                        contentStream.setFont(font, fontSize);
                        contentStream.newLineAtOffset(margin, yPosition);
                        contentStream.showText(subLinha.trim());
                        contentStream.endText();

                        yPosition -= leading;

                        // Verifica novamente se precisa de nova página
                        if (yPosition < margin + leading) {
                            contentStream.close();
                            PDPage newPage = new PDPage();
                            document.addPage(newPage);
                            contentStream = new PDPageContentStream(document, newPage);
                            yPosition = startY;
                        }
                    }
                } else {
                    // Linha vazia - apenas adiciona espaço
                    yPosition -= leading;
                }
            }
        } finally {
            contentStream.close();
            document.save(new FileOutputStream(nomeArquivo));
            document.close();
        }

        return nomeArquivo;
    }

    // Abrir o PDF automaticamente
    // java.awt.Desktop.getDesktop().open(new java.io.File(nomeArquivo));
}