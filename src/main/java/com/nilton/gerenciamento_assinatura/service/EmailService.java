package com.nilton.gerenciamento_assinatura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void enviarEmail(String emailDestino, String token){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailDestino);
        message.setSubject("Recuperação de Senha - Gerenciador-Assinaturas ");
        message.setText("Vimos que você solicitou trocar a sua senha." +
                "Copie esse token para conseguir trocar e não envie a ninguém: "+token +
                "Esse token é valido por apenas 15 minutos" +
                "" +
                "" +
                "Se você não pediu troca de senha, ignore esse email."
        );

        javaMailSender.send(message);

    }


}
