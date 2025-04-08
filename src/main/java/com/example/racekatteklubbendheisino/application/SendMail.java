package com.example.racekatteklubbendheisino.application;

import com.example.racekatteklubbendheisino.infrastructure.Mailsystem;


public class SendMail {

    public void sendMail() throws
            Exception {
        try {
            Mailsystem.MailSystem.sendmail("dam004@edu.zealand.dk");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
