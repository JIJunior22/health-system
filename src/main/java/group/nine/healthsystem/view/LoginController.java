package group.nine.healthsystem.view;

import group.nine.healthsystem.dao.LoginDao;
import group.nine.healthsystem.domain.Login;
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

import java.io.IOException;

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
            // Criando a instância do LoginDao
            LoginDao loginDao = new LoginDao();

            // Verificando as credenciais
            Login usuario = loginDao.verificarLogin(email, senha);

            if (usuario != null) {
                // Se as credenciais forem válidas, redireciona para a dashboard
                Stage stage = (Stage) loginEmailField.getScene().getWindow(); // Obtém a janela atual
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/group/nine/healthsystem/dashboard.fxml"));
                Parent root = loader.load();  // Carrega o FXML da dashboard

                // Configura a nova cena (dashboard)
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                // Se o login falhar, exibe mensagem de erro
                errorMessage.setText("Email ou senha incorretos!");
                errorMessage.setVisible(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage.setText("Erro ao carregar o dashboard.");
        }
    }

    // Método de inicialização para o botão "Entrar"
    @FXML
    private void initialize() {
        entrarButton.setOnAction(event -> handleLogin(null)); // Configura o evento do botão "Entrar"
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


