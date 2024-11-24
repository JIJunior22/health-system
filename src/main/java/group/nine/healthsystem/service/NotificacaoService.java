package group.nine.healthsystem.service;

import group.nine.healthsystem.domain.IndicadorDeSaude;
import group.nine.healthsystem.domain.Notificacao;
import group.nine.healthsystem.domain.Usuario;
import group.nine.healthsystem.repository.INotificacaoRepository;

import java.time.LocalDateTime;

public class NotificacaoService {
    private INotificacaoRepository repository;


    public void criarNotificacao(Usuario usuario, IndicadorDeSaude indicador, double limite) {
        if (indicador.getFrequenciaCardiaca() > limite) {
            Notificacao notificacao = new Notificacao();
            notificacao.setUsuario(usuario);
            notificacao.setTipoIndicador(indicador);
            notificacao.setAfericaoMaximaDoIndicador(limite);
            notificacao.setMsg("Frequência cardíaca acima do limite!");
            notificacao.setDataHora(LocalDateTime.now());
            notificacao.setStatus(false);
            repository.save(notificacao);

        }
    }
}
