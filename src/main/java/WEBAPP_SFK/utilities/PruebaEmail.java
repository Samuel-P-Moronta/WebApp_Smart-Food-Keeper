package WEBAPP_SFK.utilities;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class PruebaEmail {
    String host = "smtp.gmail.com";
    int port = 587;
    String username = "smartfoodkeeperproject@gmail.com";
    String password = "Pucmm123";

    public boolean message(String email, String subject, String message){

        Properties properties = new Properties();

        properties.put( "mail.smtp.auth", "true" );
        properties.put( "mail.smtp.host", host );
        properties.put( "mail.smtp.port", port );
        properties.put( "mail.smtp.socketFactory.port", port );
        properties.put( "mail.smtp.starttls.enable", "true" );
        properties.put( "mail.smtp.socketFactory.fallback", "false" );
        properties.put( "mail.smtp.ssl.trust", host );

        String receptor = email;

        Session sesion = Session.getDefaultInstance(properties);

        MimeMessage mail = new MimeMessage(sesion);
        try {
            mail.setFrom(new InternetAddress(username));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (receptor));
            mail.setSubject(subject);
            mail.setContent(message,"text/html");
            Transport transportar = sesion.getTransport("smtp");
            transportar.connect(username,password);
            transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transportar.close();
            return true;
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return false;

    }
}
