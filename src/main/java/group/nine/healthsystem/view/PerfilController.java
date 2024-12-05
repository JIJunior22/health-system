package group.nine.healthsystem.view;

import group.nine.healthsystem.domain.SessaoUsuario;
import group.nine.healthsystem.domain.Usuario;
import group.nine.healthsystem.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class PerfilController {
    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private DatePicker dataNascimentoField;
    @FXML private RadioButton masculinoButton;
    @FXML private RadioButton femininoButton;
    @FXML private TextField pesoField;
    @FXML private TextField alturaField;
    @FXML private Button editarButton;
    @FXML private Button salvarButton;
    @FXML private ToggleGroup sexoGroup;

    private Usuario usuarioAtual;
    private UsuarioService usuarioService;
    private boolean modoEdicao = false;

    @FXML
    public void initialize() {
        usuarioService = new UsuarioService();
        usuarioAtual = SessaoUsuario.getInstance().getUsuarioLogado();
        carregarDadosUsuario();
        desabilitarEdicao();
    }

    private void carregarDadosUsuario() {
        if (usuarioAtual != null) {
            nomeField.setText(usuarioAtual.getNome());
            emailField.setText(usuarioAtual.getLogin().getEmail());
            dataNascimentoField.setValue(usuarioAtual.getDataNascimento());

            if ("M".equals(usuarioAtual.getSexo())) {
                masculinoButton.setSelected(true);
            } else if ("F".equals(usuarioAtual.getSexo())) {
                femininoButton.setSelected(true);
            }

            pesoField.setText(String.format("%.1f", usuarioAtual.getPeso()));
            alturaField.setText(String.format("%.2f", usuarioAtual.getAltura()));
        }
    }

    @FXML
    private void handleEditar() {
        if (!modoEdicao) {
            habilitarEdicao();
            editarButton.setText("Cancelar");
            salvarButton.setDisable(false);
            modoEdicao = true;
        } else {
            desabilitarEdicao();
            editarButton.setText("Editar");
            salvarButton.setDisable(true);
            modoEdicao = false;
            carregarDadosUsuario(); // Restaura os dados originais
        }
    }

    @FXML
    private void handleSalvar() {
        try {
            // Validações
            if (nomeField.getText().trim().isEmpty()) {
                mostrarErro("O nome não pode estar vazio");
                return;
            }

            double peso = Double.parseDouble(pesoField.getText().replace(",", "."));
            double altura = Double.parseDouble(alturaField.getText().replace(",", "."));

            if (peso <= 0 || altura <= 0) {
                mostrarErro("Peso e altura devem ser maiores que zero");
                return;
            }

            // Atualiza o usuário
            usuarioAtual.setNome(nomeField.getText());
            usuarioAtual.getLogin().setEmail(emailField.getText());

            // Criptografa a senha se foi alterada
            if (!senhaField.getText().isEmpty()) {
                String senhaCriptografada = BCrypt.withDefaults()
                        .hashToString(12, senhaField.getText().toCharArray());
                usuarioAtual.getLogin().setSenha(senhaCriptografada);
            }

            usuarioAtual.setDataNascimento(dataNascimentoField.getValue());
            usuarioAtual.setSexo(masculinoButton.isSelected() ? "M" : "F");
            usuarioAtual.setPeso(peso);
            usuarioAtual.setAltura(altura);

            // Salva as alterações
            usuarioService.atualizarUsuarioELogin(usuarioAtual, usuarioAtual.getLogin());

            // Atualiza o IMC
            usuarioAtual.setImc(usuarioAtual.calcularIMC());

            mostrarSucesso("Dados atualizados com sucesso!");

            desabilitarEdicao();
            editarButton.setText("Editar");
            salvarButton.setDisable(true);
            modoEdicao = false;
            senhaField.clear(); // Limpa o campo de senha após salvar

        } catch (NumberFormatException e) {
            mostrarErro("Peso e altura devem ser números válidos");
        } catch (Exception e) {
            mostrarErro("Erro ao salvar: " + e.getMessage());
        }
    }

    private void habilitarEdicao() {
        nomeField.setEditable(true);
        emailField.setEditable(true);
        senhaField.setEditable(true);
        dataNascimentoField.setEditable(true);
        masculinoButton.setDisable(false);
        femininoButton.setDisable(false);
        pesoField.setEditable(true);
        alturaField.setEditable(true);
    }

    private void desabilitarEdicao() {
        nomeField.setEditable(false);
        emailField.setEditable(false);
        senhaField.setEditable(false);
        dataNascimentoField.setEditable(false);
        masculinoButton.setDisable(true);
        femininoButton.setDisable(true);
        pesoField.setEditable(false);
        alturaField.setEditable(false);
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}