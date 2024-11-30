package group.nine.healthsystem.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Label labelCadastro;

    // Método chamado ao clicar na label "Cadastre-se"
    @FXML
    private void handleCadastro(MouseEvent event) {
        try {
            System.out.println(getClass().getResource("/group/nine/healthsystem/cadastro.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/group/nine/healthsystem/cadastro.fxml"));
            Parent root = fxmlLoader.load();

            root.requestFocus();

            Stage stage = (Stage) labelCadastro.getScene().getWindow();
            Pane mainPane = (Pane) stage.getScene().getRoot();

            mainPane.getChildren().clear();
            mainPane.getChildren().add(root);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar a tela de cadastro!");
        }
    }
    }


