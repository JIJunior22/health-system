package group.nine.healthsystem.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class UsuarioController {
    @FXML
    private Label voltar;

    @FXML
    private void handleVoltar(MouseEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/group/nine/healthsystem/login.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) voltar.getScene().getWindow();
            Pane mainPane = (Pane) stage.getScene().getRoot();

            mainPane.getChildren().clear();
            mainPane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar a tela de login!");
        }

    }
}
