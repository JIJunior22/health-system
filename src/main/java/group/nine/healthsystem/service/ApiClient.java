package group.nine.healthsystem.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiClient {

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:streamGenerateContent";
    private static final String API_KEY = "chave da api do gemini";

    public String sendRequest(String prompt) throws IOException, InterruptedException {
        // Cria o corpo da requisição em formato JSON
        String jsonRequest = "{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}],\"role\":\"user\"}]}";

        // Configura o cliente HTTP
        HttpClient httpClient = HttpClient.newHttpClient();

        // Configura a requisição HTTP POST
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "?alt=sse&key=" + API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        // Envia a requisição e processa a resposta
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Erro ao chamar a API Gemini. Código de status: " + response.statusCode());
        }

        return processResponse(response.body());
    }

    private String processResponse(String responseBody) throws IOException {
        StringBuilder resposta = new StringBuilder();
        Pattern pattern = Pattern.compile("\"text\"\\s*:\\s*\"([^\"]+)\"");

        try (BufferedReader reader = new BufferedReader(new StringReader(responseBody))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    resposta.append(matcher.group(1)).append(" ");
                }
            }
        }

        return resposta.toString().trim();
    }
}





