package WEBAPP_SFK.services.Notification;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendMessage {
    public void message(String correo, String asunto, String mensaje) throws IOException {
        /**
         * Fuente: https://github.com/vacax/mail_transaccionales
         */
        //Configurando el servidor.
        Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "smartfoodkeeperproject@gmail.com", "Pucmm123")
                .withTransportStrategy(TransportStrategy.SMTP_TLS) // estaba SMTP_TLS
                .withSessionTimeout(5 * 1000)
                .clearEmailAddressCriteria() // turns off email validation
                .withDebugLogging(true)
                .buildMailer();{
            Email email = EmailBuilder.startingBlank()
                    .from("smartfoodkeeperproject@gmail.com")
                    .to("Para", correo)
                    //.to("Otra Dirección Random", "dissuadaient@voicememe.com")
                    //.withReplyTo("Soporte", "soporte@aciacs.com.do")
                    .withSubject(asunto + " " + new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()))
                    .withHTMLText(mensaje)
                    .withPlainText("No visualiza la información en formato html")
                    .withReturnReceiptTo()
                    .withBounceTo("smartfoodkeeperproject@gmail.com")
                    .buildEmail();

            //Enviando el mensaje:
            mailer.sendMail(email);
        }
    }
}
