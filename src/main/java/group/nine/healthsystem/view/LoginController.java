package group.nine.healthsystem.view;

import group.nine.healthsystem.dao.LoginDao;
import group.nine.healthsystem.domain.Login;
import group.nine.healthsystem.domain.SessaoUsuario;
import group.nine.healthsystem.domain.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML
    private TextField loginEmailField;

    @FXML
    private PasswordField senhaEmailField;

    @FXML
    private Label errorMessage;  // Label de erro (caso a autenticação falhe)

    @FXML
    private Button entrarButton;

    @FXML
    private Label labelCadastro;

    // Método chamado ao clicar na label "Cadastre-se"

    @FXML
    private void handleLogin(MouseEvent event) {
        String email = loginEmailField.getText();
        String senha = senhaEmailField.getText();

        try {
            LoginDao loginDao = new LoginDao();
            Usuario usuario = loginDao.validarLoginRetornandoUsuario(email, senha);

            if (usuario != null) {
                SessaoUsuario.getInstance().setUsuarioLogado(usuario);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/group/nine/healthsystem/dashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) entrarButton.getScene().getWindow();
                Pane mainPane = (Pane) stage.getScene().getRoot();
                mainPane.getChildren().clear();
                mainPane.getChildren().add(root);
            } else {
                errorMessage.setText("Email ou senha incorretos!");
                errorMessage.setVisible(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage.setText("Erro ao carregar o dashboard.");
        }
    }


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


