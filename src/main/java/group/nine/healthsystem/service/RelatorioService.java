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
import java.io.IOException;

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
        prompt.append("Faça uma análise breve e direta dos seguintes valores médios, formatando a resposta em parágrafos curtos e claros:\n\n");

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

        prompt.append("\nPor favor, forneça:\n")
                .append("1. Uma breve avaliação se os valores estão normais ou alterados\n")
                .append("2. Uma recomendação principal baseada nos valores\n")
                .append("\nFormate a resposta de forma clara e concisa, sem usar marcadores especiais.");

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

        String diretorio = "relatorios";
        java.io.File dir = new java.io.File(diretorio);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String nomeArquivo = String.format("%s/relatorio_%s.pdf",
                diretorio,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));

        // Obter a análise antes de criar o documento
        String analiseIA = apiClient.sendRequest(gerarPromptDetalhado(usuario, medias, registrosPressao, registrosGlicose));

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        StringBuilder conteudo = new StringBuilder();
        conteudo.append("RELATÓRIO DE SAÚDE\n\n");
        conteudo.append("Data: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
        conteudo.append("Paciente: ").append(usuario.getNome()).append("\n\n");
        conteudo.append(analiseIA);

        PDType1Font font = new PDType1Font(FontName.HELVETICA);
        float fontSize = 11;
        float leading = 1.5f * fontSize;
        float margin = 50;
        float width = page.getMediaBox().getWidth() - 2 * margin;
        float yPosition = page.getMediaBox().getHeight() - margin;

        String[] paragrafos = conteudo.toString().split("\n");
        int currentParagraph = 0;

        while (currentParagraph < paragrafos.length) {
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                while (currentParagraph < paragrafos.length && yPosition >= margin) {
                    String paragrafo = paragrafos[currentParagraph].trim();

                    // Quebra o parágrafo em palavras
                    String[] palavras = paragrafo.split("\\s+");
                    StringBuilder linha = new StringBuilder();

                    for (String palavra : palavras) {
                        float larguraAtual = font.getStringWidth(linha + " " + palavra) / 1000 * fontSize;

                        if (larguraAtual > width) {
                            // Escreve a linha atual
                            if (linha.length() > 0) {
                                contentStream.beginText();
                                contentStream.setFont(font, fontSize);
                                contentStream.newLineAtOffset(margin, yPosition);
                                contentStream.showText(linha.toString().trim());
                                contentStream.endText();
                                yPosition -= leading;

                                if (yPosition < margin) {
                                    break;
                                }
                            }
                            linha = new StringBuilder(palavra);
                        } else {
                            if (linha.length() > 0) {
                                linha.append(" ");
                            }
                            linha.append(palavra);
                        }
                    }

                    // Escreve a última linha do parágrafo
                    if (linha.length() > 0 && yPosition >= margin) {
                        contentStream.beginText();
                        contentStream.setFont(font, fontSize);
                        contentStream.newLineAtOffset(margin, yPosition);
                        contentStream.showText(linha.toString().trim());
                        contentStream.endText();
                        yPosition -= leading;
                    }

                    currentParagraph++;
                    yPosition -= leading * 0.5; // Espaço extra entre parágrafos
                }
            }

            if (currentParagraph < paragrafos.length) {
                page = new PDPage();
                document.addPage(page);
                yPosition = page.getMediaBox().getHeight() - margin;
            }
        }

        document.save(nomeArquivo);
        document.close();

        return nomeArquivo;
    }
}