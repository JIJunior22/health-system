package group.nine.healthsystem.view;

import group.nine.healthsystem.dao.GlicoseDao;
import group.nine.healthsystem.dao.HipertensaoDao;
import group.nine.healthsystem.domain.Glicose;
import group.nine.healthsystem.domain.Hipertensao;
import group.nine.healthsystem.domain.SessaoUsuario;
import group.nine.healthsystem.domain.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NovoRegistroController {

    @FXML
    private TextField sisField;

   @FXML
   private TextField diasField;

   @FXML
   private TextField bpmField;

    @FXML
    private TextArea obsPressaoField;

    @FXML
    private TextField glicemiaField;

   @FXML
   private  ToggleGroup isJejum;

   @FXML
   private TextArea obsGlicemiaField;

   @FXML
   private Button salvarRegistroButton;


    @FXML
    private void handleSalvarRegistro(MouseEvent event) {
        // Coleta os dados da hipertensão e glicemia a partir dos campos da tela
        int sistolica = Integer.parseInt(sisField.getText());  // Pressão sistólica
        int diastolica = Integer.parseInt(diasField.getText());  // Pressão diastólica
        int frequenciaCardiaca = Integer.parseInt(bpmField.getText());  // Batimentos por minuto (opcional para a hipertensão, se necessário)
        String obsPressao = obsPressaoField.getText();  // Observações sobre a pressão
        double glicemiaNivel = Double.parseDouble(glicemiaField.getText());  // Nível de glicemia
        String userData = (String) isJejum.getSelectedToggle().getUserData(); // Se está em jejum
        boolean jejum = Boolean.parseBoolean(userData);
        String obsGlicemia = obsGlicemiaField.getText();  // Observações sobre a glicemia

        // Recupere o usuário (se já tiver sido salvo anteriormente ou transmitido para essa tela)

        Usuario usuario = obterUsuarioLogado();

        // Associa os dados de hipertensão e glicose
        associarDados(sistolica, diastolica, frequenciaCardiaca, glicemiaNivel, jejum, obsPressao, obsGlicemia, usuario);

        // Exibir alerta de sucesso
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Sucesso");
        alerta.setHeaderText(null);
        alerta.setContentText("Dados de hipertensão e glicose salvos com sucesso!");
        alerta.showAndWait();

        // Após salvar, talvez seja necessário redirecionar o usuário ou limpar os campos
    }

    public void associarDados(int sistolica, int diastolica, double frequenciaCardiaca, double glicoseNivel, Boolean jejum, String hipertensaoObs, String glicoseObs, Usuario usuario) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
        String dataHoraAtual = LocalDateTime.now().format(formatter);

        // Associar hipertensão
        Hipertensao hipertensao = new Hipertensao();
        HipertensaoDao hipertensaoDao = new HipertensaoDao();
        hipertensao.setPressaoSistolica(sistolica);
        hipertensao.setPressaoDiastolica(diastolica);
        hipertensao.setFrequenciaCardiaca(frequenciaCardiaca);
        hipertensao.setDataHora(dataHoraAtual);
        hipertensao.setObservacoes(hipertensaoObs);
        hipertensao.setUsuario(usuario);
        hipertensaoDao.salvarHipertencao(hipertensao, usuario);

        // Associar glicose
        Glicose glicose = new Glicose();
        GlicoseDao glicoseDao = new GlicoseDao();
        glicose.setNivelGlicose(glicoseNivel);
        glicose.setDataHora(dataHoraAtual);
        glicose.setEmJejum(jejum);
        glicose.setObservacoes(glicoseObs);
        glicose.setUsuario(usuario);
        glicoseDao.salvar(glicose, usuario);
    }

    private Usuario obterUsuarioLogado() {
        Usuario usuario = SessaoUsuario.getInstance().getUsuarioLogado();
        if (usuario == null) {
            throw new RuntimeException("Nenhum usuário logado!");
        }
        return usuario;
    }



}
