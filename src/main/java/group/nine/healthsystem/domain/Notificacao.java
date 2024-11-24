package group.nine.healthsystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo; // Título da notificação
    private String mensagem; // Mensagem detalhada
    private String tipo; // Tipo da notificação (ex: "Crítico", "Informativo")
    private LocalDateTime dataHoraEnvio; // Data e hora do envio
    private String destinatario; // Destinatário da notificação (ex: email ou usuário)
    private boolean visualizada; // Indica se o usuário já visualizou a notificação




    public void marcarComoVisualizada() {
        this.visualizada = true;
    }


}
