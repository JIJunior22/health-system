package group.nine.healthsystem.domain;

import lombok.Data;

@Data
public class SessaoUsuario {
    private static SessaoUsuario instance;
    private Usuario usuarioLogado;

    private SessaoUsuario() {}

    public static SessaoUsuario getInstance() {
        if (instance == null) {
            instance = new SessaoUsuario();
        }
        return instance;
    }
    public void logout() {
        this.usuarioLogado = null;
    }
}