package group.nine.healthsystem.view;

import group.nine.healthsystem.dao.UsuarioDao;
import group.nine.healthsystem.domain.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class UsuarioController {

    @FXML
    private Label voltar;

    @FXML
    private TextField nomeField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField senhaField;
    @FXML
    private DatePicker dataDeNascimentoField;
    @FXML
    private ToggleGroup sexoField;
    @FXML
    private RadioButton masculinoButton;
    @FXML
    private RadioButton femininoButton;
    @FXML
    private TextField pesoField;
    @FXML
    private TextField alturaField;
    @FXML
    private Button cadastrarButton;

    @FXML
    public void initialize(){
        masculinoButton.setUserData("M");
        femininoButton.setUserData("F");
    }

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

    private UsuarioDao usuarioDao = new UsuarioDao();

    @FXML
    private void handleFinalizarCadastro(){
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();
        LocalDate dataDeNascimento = dataDeNascimentoField.getValue();
        Toggle sexoToggle = sexoField.getSelectedToggle();
        String sexo = "";
        if(sexoToggle != null){
            sexo = (String) sexoToggle.getUserData();
        }
        String peso = pesoField.getText();
        String altura = alturaField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || dataDeNascimento ==null || sexo == null || peso.isEmpty() || altura.isEmpty() ){
            exibirAlerta("Erro", "Todos os campos são obrigatórios");
            return;
        }

        //        criar usuário
        Usuario usuario = new Usuario();
        usuario.setNome(nome);

        usuario.setDataNascimento(dataDeNascimento);
        usuario.setSexo(sexo.toString());
        usuario.setPeso(Double.parseDouble(peso));
        usuario.setAltura(Double.parseDouble(altura));

        try {
            usuarioDao.criar(usuario);
            // Exibir alerta de sucesso
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Sucesso");
            alerta.setHeaderText(null);
            alerta.setContentText("Usuário cadastrado com sucesso!");
            alerta.showAndWait();

            // Redirecionar para a tela de login após clicar em "OK"
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/group/nine/healthsystem/login.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) nomeField.getScene().getWindow();
            Pane mainPane = (Pane) stage.getScene().getRoot();

            mainPane.getChildren().clear();
            mainPane.getChildren().add(root);

        } catch (Exception e) {
            exibirAlerta("Erro","falha ao cadastrar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void exibirAlerta (String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    }



