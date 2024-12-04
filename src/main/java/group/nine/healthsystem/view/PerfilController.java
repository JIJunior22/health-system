package group.nine.healthsystem.view;

import group.nine.healthsystem.domain.SessaoUsuario;
import group.nine.healthsystem.domain.Usuario;
import group.nine.healthsystem.domain.Login;
import group.nine.healthsystem.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class PerfilController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaAtualField;

    @FXML
    private PasswordField novaSenhaField;

    @FXML
    private PasswordField confirmarSenhaField;

    private UsuarioService usuarioService;
    private Usuario usuarioAtual;
    private Login loginAtual;

    @FXML
    public void initialize() {
        usuarioService = new UsuarioService();
        usuarioAtual = SessaoUsuario.getInstance().getUsuarioLogado();

        // Preenche os campos com os dados atuais do usuário
        if (usuarioAtual != null) {
            loginAtual = usuarioService.buscarLoginPorUsuario(usuarioAtual);
            nomeField.setText(usuarioAtual.getNome());
            emailField.setText(loginAtual.getEmail());
        }
    }

    @FXML
    private void handleSalvarAlteracoes() {
        if (!validarCampos()) {
            return;
        }

        boolean alteracaoSenha = !novaSenhaField.getText().isEmpty();

        // Verifica a senha atual apenas se estiver alterando a senha
        if (alteracaoSenha) {
            if (!usuarioService.verificarSenha(loginAtual.getEmail(), senhaAtualField.getText())) {
                mostrarAlerta("Erro", "Senha atual incorreta!");
                return;
            }
        }

        // Atualiza os dados do usuário
        usuarioAtual.setNome(nomeField.getText());
        loginAtual.setEmail(emailField.getText());

        // Atualiza a senha apenas se uma nova senha foi fornecida
        if (alteracaoSenha) {
            // Usa o BCrypt para criptografar a nova senha
            String senhaCriptografada = BCrypt.withDefaults().hashToString(12, novaSenhaField.getText().toCharArray());
            loginAtual.setSenha(senhaCriptografada);
        }

        try {
            usuarioService.atualizarUsuarioELogin(usuarioAtual, loginAtual);
            mostrarAlerta("Sucesso", "Dados atualizados com sucesso!");
            limparCamposSenha();
        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao atualizar dados: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelar() {
        // Recarrega os dados originais
        initialize();
        limparCamposSenha();
    }

    private boolean validarCampos() {
        if (nomeField.getText().isEmpty() || emailField.getText().isEmpty()) {
            mostrarAlerta("Erro", "Nome e email são obrigatórios!");
            return false;
        }

        // Validações de senha apenas se estiver tentando alterar a senha
        if (!novaSenhaField.getText().isEmpty()) {
            if (!novaSenhaField.getText().equals(confirmarSenhaField.getText())) {
                mostrarAlerta("Erro", "As senhas não coincidem!");
                return false;
            }
            if (senhaAtualField.getText().isEmpty()) {
                mostrarAlerta("Erro", "Digite a senha atual para alterar a senha!");
                return false;
            }
        }

        return true;
    }

    private void limparCamposSenha() {
        senhaAtualField.clear();
        novaSenhaField.clear();
        confirmarSenhaField.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}