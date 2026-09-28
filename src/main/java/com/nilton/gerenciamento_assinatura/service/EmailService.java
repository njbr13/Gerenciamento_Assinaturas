package com.nilton.gerenciamento_assinatura.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailRemetente;

    public void enviarEmail(String emailDestino, String token){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailRemetente);
        message.setTo(emailDestino);
        message.setSubject("Recuperação de Senha - Gerenciador-Assinaturas ");
        message.setText(
                "Olá!\n\n" +
                        "Vimos que você solicitou a alteração da sua senha no Gerenciador de Assinaturas.\n\n" +
                        "Copie o token abaixo para conseguir redefinir o seu acesso. Por motivos de segurança, não envie este código a ninguém:\n\n" +
                        "🔑 Token: " + token + "\n\n" +
                        "⚠️ Atenção: Este token é válido por apenas 15 minutos.\n\n" +
                        "Se você não solicitou a troca de senha, por favor, ignore este e-mail. A sua conta continuará segura."
        );

        javaMailSender.send(message);

    }

    public void enviarEmailNotificacaoVencimento(String emailDestino, String nomeAssinatura, int diasParaVencer) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailRemetente);
        message.setTo(emailDestino);
        message.setSubject("Aviso de Vencimento - " + nomeAssinatura);
        
        String tempo = diasParaVencer == 1 ? "1 dia" : diasParaVencer + " dias";
        
        message.setText(
                "Olá!\n\n" +
                "Este é um aviso amigável de que a sua assinatura de " + nomeAssinatura + " vencerá em " + tempo + ".\n\n" +
                "Verifique seu saldo para garantir que o pagamento seja processado corretamente.\n\n" +
                "Atenciosamente,\nEquipe Gerenciador de Assinaturas"
        );

        javaMailSender.send(message);
    }


}
