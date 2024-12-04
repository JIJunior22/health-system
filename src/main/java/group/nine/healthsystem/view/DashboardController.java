package group.nine.healthsystem.view;

import group.nine.healthsystem.domain.SessaoUsuario;
import group.nine.healthsystem.domain.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private HBox homeButton;

    @FXML
    private HBox novoRegistroButton;

    @FXML
    private HBox relatorioButton;

    @FXML
    private HBox perfilButton;

    @FXML
    private Label welcomeText;

    @FXML
    private Button sairButton;

    @FXML
    public void initialize() {
        // Associa eventos de clique aos botões
        homeButton.setOnMouseClicked(event -> setCenterPane("home.fxml"));
        novoRegistroButton.setOnMouseClicked(event -> setCenterPane("novo-registro.fxml"));
        relatorioButton.setOnMouseClicked(event -> setCenterPane("relatorio.fxml"));
        perfilButton.setOnMouseClicked(event -> setCenterPane("perfil.fxml"));

        Usuario usuarioLogado = SessaoUsuario.getInstance().getUsuarioLogado();
        if (usuarioLogado != null) {
            String primeiroNome = usuarioLogado.getNome().split(" ")[0];
            welcomeText.setText("Olá, " + primeiroNome + "!");
        }
    }

    public void setCenterPane(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/group/nine/healthsystem/" + fxmlFile));
            Pane newCenterPane = loader.load();
            mainPane.setCenter(newCenterPane); // Define o novo conteúdo no centro
        } catch (IOException e) {
            e.printStackTrace();
            // Opção: Mostrar uma mensagem de erro     }
        }

    }

    @FXML
    private void handleLogout() {
        try {
            // Limpa a sessão do usuário
            SessaoUsuario.getInstance().logout();

            // Carrega a tela de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/group/nine/healthsystem/login.fxml"));
            Parent root = loader.load();

            // Obtém a stage atual e atualiza a cena
            Stage stage = (Stage) sairButton.getScene().getWindow();
            Pane mainPane = (Pane) stage.getScene().getRoot();
            mainPane.getChildren().clear();
            mainPane.getChildren().add(root);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro aso sair");
        }
    }
}
