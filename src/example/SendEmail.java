package example;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class SendEmail {
    private String host;
    private String login;
    private String password;

    SendEmail(String host, String login, String password) {
        this.host = host;
        this.login = login;
        this.password = password;
    }

    public void send(String from, String to, String subject, String text) throws Exception{
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");

        Authenticator auth = new SendEmail.SMTPAuthenticator(this.login, this.password);
        Session mailSession = Session.getDefaultInstance(props, auth);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);

        message.addHeader("Content-type", "text/HTML; charset=UTF-8");
        message.addHeader("format", "flowed");
        message.addHeader("Content-Transfer-Encoding", "8bit");

        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(to));
        message.setSubject(subject, "UTF-8");
        message.setSentDate(new Date());

        BodyPart messageBodyPart = new MimeBodyPart();

        // Fill the message
        messageBodyPart.setText(text);

        // Create a multipart message for attachment
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Second part is attachment
        messageBodyPart = new MimeBodyPart();
        String filename = "C:\\Users\\vtamazlicari\\Desktop\\test.txt";
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        transport.connect();
        transport.send(message);
        System.out.println("Message send successfully!");
        transport.close();
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        String username;
        String password;
        SMTPAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }
        public PasswordAuthentication getPasswordAuthentication() {
            String username = this.username;
            String password = this.password;
            return new PasswordAuthentication(username, password);
        }
    }
}
