package group.nine.healthsystem.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    public void initialize() {
        // Associa eventos de clique aos botões
        homeButton.setOnMouseClicked(event -> setCenterPane("home.fxml"));
        novoRegistroButton.setOnMouseClicked(event -> setCenterPane("novo-registro.fxml"));
        relatorioButton.setOnMouseClicked(event -> setCenterPane("relatorio.fxml"));
        perfilButton.setOnMouseClicked(event -> setCenterPane("perfil.fxml"));
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
}
