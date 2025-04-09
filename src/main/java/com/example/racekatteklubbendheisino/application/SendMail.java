package com.example.racekatteklubbendheisino.application;

import com.example.racekatteklubbendheisino.infrastructure.Mailsystem;


public class SendMail {

    public void sendMail(String recepient, String subject, String message) throws
            Exception {
        try {
                Mailsystem.MailSystem.sendmail(recepient, subject, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
