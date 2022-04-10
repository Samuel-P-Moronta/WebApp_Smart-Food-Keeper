package WEBAPP_SFK.utilities;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomEmailSender {
    public void message(String email, String subject, String mensaje) throws IOException {


        Mailer mailer = MailerBuilder

                .withSMTPServer("smtp.gmail.com", 587, "smartfoodkeeperproject@gmail.com", "Pucmm123")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withSessionTimeout(5 * 1000)
                .clearEmailAddressCriteria() // turns off email validation
                .withDebugLogging(true)
                .buildMailer();{
            Email emailAux = EmailBuilder.startingBlank()
                    .from("smartfoodkeeperproject@gmail.com")
                    .to("Para", email)
                    .withSubject(subject + " " + new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()))
                    .withHTMLText(mensaje)
                    .withPlainText("No visualiza la información en formato html")
                    .withReturnReceiptTo()
                    .withBounceTo("smartfoodkeeperproject@gmail.com")
                    .buildEmail();

            mailer.sendMail(emailAux);
        }
    }
    public static String htmlMessage() {

        String mensaje = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <title>Simple Transactional Email</title>\n" +
                "    <style>\n" +
                "      @media only screen and (max-width: 620px) {\n" +
                "        table.body h1 {\n" +
                "          font-size: 28px !important;\n" +
                "          margin-bottom: 10px !important;\n" +
                "        }\n" +
                "\n" +
                "        table.body p,\n" +
                "        table.body ul,\n" +
                "        table.body ol,\n" +
                "        table.body td,\n" +
                "        table.body span,\n" +
                "        table.body a {\n" +
                "          font-size: 16px !important;\n" +
                "        }\n" +
                "\n" +
                "        table.body .wrapper,\n" +
                "        table.body .article {\n" +
                "          padding: 10px !important;\n" +
                "        }\n" +
                "\n" +
                "        table.body .content {\n" +
                "          padding: 0 !important;\n" +
                "        }\n" +
                "\n" +
                "        table.body .container {\n" +
                "          padding: 0 !important;\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        table.body .main {\n" +
                "          border-left-width: 0 !important;\n" +
                "          border-radius: 0 !important;\n" +
                "          border-right-width: 0 !important;\n" +
                "        }\n" +
                "\n" +
                "        table.body .btn table {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        table.body .btn a {\n" +
                "          width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        table.body .img-responsive {\n" +
                "          height: auto !important;\n" +
                "          max-width: 100% !important;\n" +
                "          width: auto !important;\n" +
                "        }\n" +
                "      }\n" +
                "      @media all {\n" +
                "        .ExternalClass {\n" +
                "          width: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .ExternalClass,\n" +
                "        .ExternalClass p,\n" +
                "        .ExternalClass span,\n" +
                "        .ExternalClass font,\n" +
                "        .ExternalClass td,\n" +
                "        .ExternalClass div {\n" +
                "          line-height: 100%;\n" +
                "        }\n" +
                "\n" +
                "        .apple-link a {\n" +
                "          color: inherit !important;\n" +
                "          font-family: inherit !important;\n" +
                "          font-size: inherit !important;\n" +
                "          font-weight: inherit !important;\n" +
                "          line-height: inherit !important;\n" +
                "          text-decoration: none !important;\n" +
                "        }\n" +
                "\n" +
                "        #MessageViewBody a {\n" +
                "          color: inherit;\n" +
                "          text-decoration: none;\n" +
                "          font-size: inherit;\n" +
                "          font-family: inherit;\n" +
                "          font-weight: inherit;\n" +
                "          line-height: inherit;\n" +
                "        }\n" +
                "\n" +
                "        .btn-primary table td:hover {\n" +
                "          background-color: #34495e !important;\n" +
                "        }\n" +
                "\n" +
                "        .btn-primary a:hover {\n" +
                "          background-color: #34495e !important;\n" +
                "          border-color: #34495e !important;\n" +
                "        }\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "  <body\n" +
                "    style=\"\n" +
                "      background-color: #f6f6f6;\n" +
                "      font-family: sans-serif;\n" +
                "      -webkit-font-smoothing: antialiased;\n" +
                "      font-size: 14px;\n" +
                "      line-height: 1.4;\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      -ms-text-size-adjust: 100%;\n" +
                "      -webkit-text-size-adjust: 100%;\n" +
                "    \"\n" +
                "  >\n" +
                "    <span\n" +
                "      class=\"preheader\"\n" +
                "      style=\"\n" +
                "        color: transparent;\n" +
                "        display: none;\n" +
                "        height: 0;\n" +
                "        max-height: 0;\n" +
                "        max-width: 0;\n" +
                "        opacity: 0;\n" +
                "        overflow: hidden;\n" +
                "        mso-hide: all;\n" +
                "        visibility: hidden;\n" +
                "        width: 0;\n" +
                "      \"\n" +
                "      >This is preheader text. Some clients will show this text as a\n" +
                "      preview.</span\n" +
                "    >\n" +
                "    <table\n" +
                "      role=\"presentation\"\n" +
                "      border=\"0\"\n" +
                "      cellpadding=\"0\"\n" +
                "      cellspacing=\"0\"\n" +
                "      class=\"body\"\n" +
                "      style=\"\n" +
                "        border-collapse: separate;\n" +
                "        mso-table-lspace: 0pt;\n" +
                "        mso-table-rspace: 0pt;\n" +
                "        background-color: #f6f6f6;\n" +
                "        width: 100%;\n" +
                "      \"\n" +
                "      width=\"100%\"\n" +
                "      bgcolor=\"#f6f6f6\"\n" +
                "    >\n" +
                "      <tr>\n" +
                "        <td\n" +
                "          style=\"font-family: sans-serif; font-size: 14px; vertical-align: top\"\n" +
                "          valign=\"top\"\n" +
                "        >\n" +
                "          &nbsp;\n" +
                "        </td>\n" +
                "        <td\n" +
                "          class=\"container\"\n" +
                "          style=\"\n" +
                "            font-family: sans-serif;\n" +
                "            font-size: 14px;\n" +
                "            vertical-align: top;\n" +
                "            display: block;\n" +
                "            max-width: 580px;\n" +
                "            padding: 10px;\n" +
                "            width: 580px;\n" +
                "            margin: 0 auto;\n" +
                "          \"\n" +
                "          width=\"580\"\n" +
                "          valign=\"top\"\n" +
                "        >\n" +
                "          <div\n" +
                "            class=\"content\"\n" +
                "            style=\"\n" +
                "              box-sizing: border-box;\n" +
                "              display: block;\n" +
                "              margin: 0 auto;\n" +
                "              max-width: 580px;\n" +
                "              padding: 10px;\n" +
                "            \"\n" +
                "          >\n" +
                "            <!-- START CENTERED WHITE CONTAINER -->\n" +
                "            <table\n" +
                "              role=\"presentation\"\n" +
                "              class=\"main\"\n" +
                "              style=\"\n" +
                "                border-collapse: separate;\n" +
                "                mso-table-lspace: 0pt;\n" +
                "                mso-table-rspace: 0pt;\n" +
                "                background: #ffffff;\n" +
                "                border-radius: 3px;\n" +
                "                width: 100%;\n" +
                "              \"\n" +
                "              width=\"100%\"\n" +
                "            >\n" +
                "              <!-- START MAIN CONTENT AREA -->\n" +
                "              <tr>\n" +
                "                <td\n" +
                "                  class=\"wrapper\"\n" +
                "                  style=\"\n" +
                "                    font-family: sans-serif;\n" +
                "                    font-size: 14px;\n" +
                "                    vertical-align: top;\n" +
                "                    box-sizing: border-box;\n" +
                "                    padding: 20px;\n" +
                "                  \"\n" +
                "                  valign=\"top\"\n" +
                "                >\n" +
                "                  <table\n" +
                "                    role=\"presentation\"\n" +
                "                    border=\"0\"\n" +
                "                    cellpadding=\"0\"\n" +
                "                    cellspacing=\"0\"\n" +
                "                    style=\"\n" +
                "                      border-collapse: separate;\n" +
                "                      mso-table-lspace: 0pt;\n" +
                "                      mso-table-rspace: 0pt;\n" +
                "                      width: 100%;\n" +
                "                    \"\n" +
                "                    width=\"100%\"\n" +
                "                  >\n" +
                "                    <tr>\n" +
                "                      <td\n" +
                "                        style=\"\n" +
                "                          font-family: sans-serif;\n" +
                "                          font-size: 14px;\n" +
                "                          vertical-align: top;\n" +
                "                        \"\n" +
                "                        valign=\"top\"\n" +
                "                      >\n" +
                "                        <p\n" +
                "                          style=\"\n" +
                "                            font-family: sans-serif;\n" +
                "                            font-size: 14px;\n" +
                "                            font-weight: normal;\n" +
                "                            margin: 0;\n" +
                "                            margin-bottom: 15px;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          Hola,\n" +
                "                        </p>\n" +
                "                        <p\n" +
                "                          style=\"\n" +
                "                            font-family: sans-serif;\n" +
                "                            font-size: 14px;\n" +
                "                            font-weight: normal;\n" +
                "                            margin: 0;\n" +
                "                            margin-bottom: 15px;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          Este correo es para informarte que se ha generado una\n" +
                "                          alerta que tienes que inspeccionar\n" +
                "                        </p>\n" +
                "                        <table\n" +
                "                          role=\"presentation\"\n" +
                "                          border=\"0\"\n" +
                "                          cellpadding=\"0\"\n" +
                "                          cellspacing=\"0\"\n" +
                "                          class=\"btn btn-primary\"\n" +
                "                          style=\"\n" +
                "                            border-collapse: separate;\n" +
                "                            mso-table-lspace: 0pt;\n" +
                "                            mso-table-rspace: 0pt;\n" +
                "                            box-sizing: border-box;\n" +
                "                            width: 100%;\n" +
                "                          \"\n" +
                "                          width=\"100%\"\n" +
                "                        >\n" +
                "                          <tbody>\n" +
                "                            <tr>\n" +
                "                              <td\n" +
                "                                align=\"left\"\n" +
                "                                style=\"\n" +
                "                                  font-family: sans-serif;\n" +
                "                                  font-size: 14px;\n" +
                "                                  vertical-align: top;\n" +
                "                                  padding-bottom: 15px;\n" +
                "                                \"\n" +
                "                                valign=\"top\"\n" +
                "                              >\n" +
                "                                <table\n" +
                "                                  role=\"presentation\"\n" +
                "                                  border=\"0\"\n" +
                "                                  cellpadding=\"0\"\n" +
                "                                  cellspacing=\"0\"\n" +
                "                                  style=\"\n" +
                "                                    border-collapse: separate;\n" +
                "                                    mso-table-lspace: 0pt;\n" +
                "                                    mso-table-rspace: 0pt;\n" +
                "                                    width: auto;\n" +
                "                                  \"\n" +
                "                                >\n" +
                "                                  <tbody>\n" +
                "                                    <tr>\n" +
                "                                      <td\n" +
                "                                        style=\"\n" +
                "                                          font-family: sans-serif;\n" +
                "                                          font-size: 14px;\n" +
                "                                          vertical-align: top;\n" +
                "                                          border-radius: 5px;\n" +
                "                                          text-align: center;\n" +
                "                                          background-color: #3498db;\n" +
                "                                        \"\n" +
                "                                        valign=\"top\"\n" +
                "                                        align=\"center\"\n" +
                "                                        bgcolor=\"#3498db\"\n" +
                "                                      >\n" +
                "                                        <a\n" +
                "                                          href=\"https://sfkproject.tech/employeePortal/shelfMonitoringEmployee\"\n" +
                "                                          target=\"_blank\"\n" +
                "                                          style=\"\n" +
                "                                            border: solid 1px #3498db;\n" +
                "                                            border-radius: 5px;\n" +
                "                                            box-sizing: border-box;\n" +
                "                                            cursor: pointer;\n" +
                "                                            display: inline-block;\n" +
                "                                            font-size: 14px;\n" +
                "                                            font-weight: bold;\n" +
                "                                            margin: 0;\n" +
                "                                            padding: 12px 25px;\n" +
                "                                            text-decoration: none;\n" +
                "                                            text-transform: capitalize;\n" +
                "                                            background-color: #3498db;\n" +
                "                                            border-color: #3498db;\n" +
                "                                            color: #ffffff;\n" +
                "                                          \"\n" +
                "                                          >Ver notificacion</a\n" +
                "                                        >\n" +
                "                                      </td>\n" +
                "                                    </tr>\n" +
                "                                  </tbody>\n" +
                "                                </table>\n" +
                "                              </td>\n" +
                "                            </tr>\n" +
                "                          </tbody>\n" +
                "                        </table>\n" +
                "                        <p\n" +
                "                          style=\"\n" +
                "                            font-family: sans-serif;\n" +
                "                            font-size: 14px;\n" +
                "                            font-weight: normal;\n" +
                "                            margin: 0;\n" +
                "                            margin-bottom: 15px;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                        </p>\n" +
                "                        <p\n" +
                "                          style=\"\n" +
                "                            font-family: sans-serif;\n" +
                "                            font-size: 14px;\n" +
                "                            font-weight: normal;\n" +
                "                            margin: 0;\n" +
                "                            margin-bottom: 15px;\n" +
                "                          \"\n" +
                "                        >\n" +
                "                          Smart Food Keeper Project 2022\n" +
                "                        </p>\n" +
                "                      </td>\n" +
                "                    </tr>\n" +
                "                  </table>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "\n" +
                "              <!-- END MAIN CONTENT AREA -->\n" +
                "            </table>\n" +
                "            <!-- END CENTERED WHITE CONTAINER -->\n" +
                "\n" +
                "            <!-- START FOOTER -->\n" +
                "            <div\n" +
                "              class=\"footer\"\n" +
                "              style=\"\n" +
                "                clear: both;\n" +
                "                margin-top: 10px;\n" +
                "                text-align: center;\n" +
                "                width: 100%;\n" +
                "              \"\n" +
                "            >\n" +
                "              <table\n" +
                "                role=\"presentation\"\n" +
                "                border=\"0\"\n" +
                "                cellpadding=\"0\"\n" +
                "                cellspacing=\"0\"\n" +
                "                style=\"\n" +
                "                  border-collapse: separate;\n" +
                "                  mso-table-lspace: 0pt;\n" +
                "                  mso-table-rspace: 0pt;\n" +
                "                  width: 100%;\n" +
                "                \"\n" +
                "                width=\"100%\"\n" +
                "              >\n" +
                "                <tr>\n" +
                "                  <td\n" +
                "                    class=\"content-block\"\n" +
                "                    style=\"\n" +
                "                      font-family: sans-serif;\n" +
                "                      vertical-align: top;\n" +
                "                      padding-bottom: 10px;\n" +
                "                      padding-top: 10px;\n" +
                "                      color: #999999;\n" +
                "                      font-size: 12px;\n" +
                "                      text-align: center;\n" +
                "                    \"\n" +
                "                    valign=\"top\"\n" +
                "                    align=\"center\"\n" +
                "                  >\n" +
                "                    <span\n" +
                "                      class=\"apple-link\"\n" +
                "                      style=\"\n" +
                "                        color: #999999;\n" +
                "                        font-size: 12px;\n" +
                "                        text-align: center;\n" +
                "                      \"\n" +
                "                      >Navarrete, Santiago Republica Dominicana</span\n" +
                "                    >\n" +
                "                    <br />\n" +
                "                    Don't like these emails?\n" +
                "                    <a\n" +
                "                      href=\"http://i.imgur.com/CScmqnj.gif\"\n" +
                "                      style=\"\n" +
                "                        text-decoration: underline;\n" +
                "                        color: #999999;\n" +
                "                        font-size: 12px;\n" +
                "                        text-align: center;\n" +
                "                      \"\n" +
                "                      >Unsubscribe</a\n" +
                "                    >.\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td\n" +
                "                    class=\"content-block powered-by\"\n" +
                "                    style=\"\n" +
                "                      font-family: sans-serif;\n" +
                "                      vertical-align: top;\n" +
                "                      padding-bottom: 10px;\n" +
                "                      padding-top: 10px;\n" +
                "                      color: #999999;\n" +
                "                      font-size: 12px;\n" +
                "                      text-align: center;\n" +
                "                    \"\n" +
                "                    valign=\"top\"\n" +
                "                    align=\"center\"\n" +
                "                  ></td>\n" +
                "                </tr>\n" +
                "              </table>\n" +
                "            </div>\n" +
                "            <!-- END FOOTER -->\n" +
                "          </div>\n" +
                "        </td>\n" +
                "        <td\n" +
                "          style=\"font-family: sans-serif; font-size: 14px; vertical-align: top\"\n" +
                "          valign=\"top\"\n" +
                "        >\n" +
                "          &nbsp;\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>\n";


        return mensaje;
    }
}
