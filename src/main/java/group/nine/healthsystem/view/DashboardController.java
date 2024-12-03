package group.nine.healthsystem.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DashboardController {


    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView homeButton;

    @FXML
    private ImageView novoRegistroButton;

    @FXML
    private ImageView relatorioButton;

    @FXML
    private ImageView perfilButton;


    @FXML
    public void initialize() {
        // Associa eventos de clique aos botões
        homeButton.setOnMouseClicked(event -> setCenterPane("home.fxml"));
        novoRegistroButton.setOnMouseClicked(event -> setCenterPane("novoRegistro.fxml"));
        relatorioButton.setOnMouseClicked(event -> setCenterPane("relatorio.fxml"));
        perfilButton.setOnMouseClicked(event -> setCenterPane("perfil.fxml"));
    }

    private void setCenterPane(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newCenterPane = loader.load();
            mainPane.setCenter(newCenterPane); // Define o novo conteúdo no centro
        } catch (IOException e) {
            e.printStackTrace();
            // Opção: Mostrar uma mensagem de erro     }
        }

    }
}
