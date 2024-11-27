//package group.nine.healthsystem.view;
//
//import group.nine.healthsystem.dao.UsuarioDao;
//import group.nine.healthsystem.domain.Usuario;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//
//import java.time.LocalDate;
//
//public class UsuarioController {
//
//
//    @FXML
//    private TextField fieldNome;
//
//    @FXML
//    private TextField fieldEmail;
//
//    @FXML
//    private PasswordField fieldSenha;
//
//    @FXML
//    private TextField fieldPeso;
//
//    @FXML
//    private TextField fieldAltura;
//
//    @FXML
//    private DatePicker fieldDataNascimento;
//
//    @FXML
//    private ToggleGroup sexoRadio;
//
//    @FXML
//    private Label labelNome;
//
//    @FXML
//    private Label labelEmail;
//
//    @FXML
//    private Label labelSenha;
//
//    @FXML
//    private Label labelPeso;
//
//    @FXML
//    private Label labelAltura;
//
//    @FXML
//    private Label labelDataNascimento;
//
//    @FXML
//    private Label labelSexo;
//
//    @FXML
//    private Label errorMessage;
//
//    @FXML
//    private Label cadastroMessage;
//
//    @FXML
//    private Button btnSalvar;
//
//    @FXML
//    private Button btnVoltar;
//
//    protected Usuario usuarioLogado;
//
//    public void setUsuarioLogado(Usuario usuarioLogado) {
//        this.usuarioLogado = usuarioLogado;
//        preencherDadosUsuario();
//    }
//
//    private void preencherDadosUsuario() {
//        labelNome.setText(usuarioLogado.getNome());
//        labelEmail.setText(usuarioLogado.getEmail());
//        labelSenha.setText("********");
//        labelPeso.setText(String.format("%.2f kg", usuarioLogado.getPeso()));
//        labelAltura.setText(String.format("%.2f m", usuarioLogado.getAltura()));
//        labelDataNascimento.setText(usuarioLogado.getDataNascimento().toString());
//        labelSexo.setText(usuarioLogado.getSexo());
//    }
//
//    @FXML
//    private void onSalvarButtonClick() {
//        errorMessage.setText("");
//        cadastroMessage.setText("");
//
//        UsuarioDao usuarioDao = new UsuarioDao();
//
//        try {
//            // Atualizar atributos do usuário
//            String novoNome = fieldNome.getText();
//            if (!novoNome.isEmpty()) usuarioLogado.setNome(novoNome);
//
//            String novoEmail = fieldEmail.getText();
//            if (!novoEmail.isEmpty()) usuarioLogado.setEmail(novoEmail);
//
//            String novaSenha = fieldSenha.getText();
//            if (!novaSenha.isEmpty()) usuarioLogado.setSenha(novaSenha);
//
//            String novoPeso = fieldPeso.getText();
//            if (!novoPeso.isEmpty()) usuarioLogado.setPeso(Double.parseDouble(novoPeso));
//
//            String novaAltura = fieldAltura.getText();
//            if (!novaAltura.isEmpty()) usuarioLogado.setAltura(Double.parseDouble(novaAltura));
//
//            LocalDate novaDataNascimento = fieldDataNascimento.getValue();
//            if (novaDataNascimento != null) usuarioLogado.setDataNascimento(novaDataNascimento);
//
//            String novoSexo = ((RadioButton) sexoRadio.getSelectedToggle()).getText();
//            if (!novoSexo.isEmpty()) usuarioLogado.setSexo(novoSexo);
//
//            // Persistindo no banco
//            usuarioDao.update(usuarioLogado);
//            preencherDadosUsuario(); // Atualiza os labels com os novos dados
//            cadastroMessage.setText("Usuário atualizado com sucesso!");
//
//        } catch (Exception e) {
//            errorMessage.setText("Erro ao atualizar: " + e.getMessage());
//        }
//    }
//
//    @FXML
//    private void onVoltarButtonClick() {
//        // Implementação do retorno à tela anterior
//        System.out.println("Retornando à tela principal...");
//    }
//}
//
//
