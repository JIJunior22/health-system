package group.nine.healthsystem.service;

import group.nine.healthsystem.dao.GlicoseDao;
import group.nine.healthsystem.dao.HipertensaoDao;
import group.nine.healthsystem.dao.LoginDao;
import group.nine.healthsystem.dao.UsuarioDao;
import group.nine.healthsystem.domain.Glicose;
import group.nine.healthsystem.domain.Hipertensao;
import group.nine.healthsystem.domain.Login;
import group.nine.healthsystem.domain.Usuario;

public class UsuarioService {

    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final HipertensaoDao hipertensaoDao = new HipertensaoDao();
    private final GlicoseDao glicoseDao = new GlicoseDao();
    private final LoginDao loginDao = new LoginDao();

    public void associarDados(
            Usuario usuario, int sistolica, int diastolica, double glicoseNivel,
            String hipertensaoObs, String glicoseObs, String email, String senha) {

        // Associar hipertensão
        Hipertensao hipertensao = new Hipertensao();
        hipertensao.setPressaoSistolica(sistolica);
        hipertensao.setPressaoDiastolica(diastolica);
        hipertensao.setDataHora("2024-12-01 10:00:00");
        hipertensao.setObservacoes(hipertensaoObs);
        hipertensao.setUsuario(usuario);
        hipertensaoDao.salvarHipertencao(hipertensao, usuario);

        // Associar glicose
        Glicose glicose = new Glicose();
        glicose.setNivelGlicose(glicoseNivel);
        glicose.setDataHora("2024-12-01 08:00:00");
        glicose.setEmJejum(true);
        glicose.setObservacoes(glicoseObs);
        glicose.setUsuario(usuario);
        glicoseDao.salvar(glicose, usuario);

        // Criar login
        Login login = new Login();
        login.setEmail(email);
        login.setSenha(senha);
        login.setUsuario(usuario);
        loginDao.adicionarLogin(login,usuario);
    }

    public void salvarUsuario(Usuario usuario) {
        usuarioDao.criar(usuario);
    }
}
