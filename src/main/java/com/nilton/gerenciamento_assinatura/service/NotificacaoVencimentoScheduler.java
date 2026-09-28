package com.nilton.gerenciamento_assinatura.service;

import com.nilton.gerenciamento_assinatura.model.Assinatura;
import com.nilton.gerenciamento_assinatura.repository.AssinaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificacaoVencimentoScheduler {

    private final AssinaturaRepository assinaturaRepository;
    private final EmailService emailService;

    // Executa todo dia as 08:00
    @Scheduled(cron = "0 0 8 * * *")
    public void verificarVencimentos() {
        List<Assinatura> assinaturas = assinaturaRepository.findAll();
        LocalDate hoje = LocalDate.now();

        for (Assinatura assinatura : assinaturas) {
            if (assinatura.getAtiva() && assinatura.getDataVencimento() != null) {
                LocalDate dataVencimento = assinatura.getDataVencimento();
                
                
                long diasParaVencer = ChronoUnit.DAYS.between(hoje, dataVencimento);

                if (diasParaVencer == 3 || diasParaVencer == 1) {
                    emailService.enviarEmailNotificacaoVencimento(
                            assinatura.getUser().getEmail(),
                            assinatura.getNomeAssinatura(),
                            (int) diasParaVencer
                    );
                }
            }
        }
    }
}
