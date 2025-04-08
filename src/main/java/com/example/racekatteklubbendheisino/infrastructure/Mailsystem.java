package com.example.racekatteklubbendheisino.infrastructure;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mailsystem {

    public static class MailSystem {
        public static void sendmail(String recepient) throws Exception {
            System.out.println("Prepering to send mail");

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            String myAccountEmail = "tino03543@gmail.com";
            String myPassword = "XXXXXXXXXXX";

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, myPassword);
                }
            });
            Message message = prepareMessage(session, myAccountEmail, recepient);
            Transport.send(message);
            System.out.println("Sent message successfully....");

        }

        private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(myAccountEmail));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
                message.setSubject("I have a video of you ");
                message.setText("Hello, i have a video of you gooning to a five year old cat baby. i will like my 10000£ now pleas ");
                return message;
            } catch (Exception e) {
                Logger.getLogger(MailSystem.class.getName()).log(Level.SEVERE, null, e);
            }
            return null;
        }
    }
}

