package example;

import org.apache.commons.lang3.StringUtils;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.Properties;

public class FetchingEmail {

    private String host;
    private String login;
    private String password;
    private Store store;
    private Message[] messages;
    private Folder emailFolder;

    FetchingEmail(String host, String login, String password) throws MessagingException {
        this.host = host;
        this.login = login;
        this.password = password;

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3.host", this.host);
        properties.put("mail.pop3.port", "110");
        properties.put("mail.pop3.starttls.enable", "true");

        Session emailSession = Session.getInstance(properties);
        this.store = emailSession.getStore("pop3");
        this.store.connect(this.host, this.login, this.password);
        this.emailFolder = this.store.getFolder("INBOX");
        this.emailFolder.open(Folder.READ_WRITE);
        this.messages = emailFolder.getMessages();
    }

    public String[] getFromMails() throws MessagingException, IOException {
        String[] arrayWithMessage = new String[this.messages.length];
        for (int i = 0; i < this.messages.length; i++) {
            Message message = this.messages[i];
            arrayWithMessage[i] = String.valueOf(message.getFrom()[0]);
            System.out.println("---------------------------------");
            System.out.println("Email Number " + (i + 1));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("Text: " + this.getTextFromMessage(message));
        }
        return arrayWithMessage;
    }

    private void readFile(Message message) throws IOException, MessagingException {
            Multipart multipart = (Multipart) message.getContent();

            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if(!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) &&
                        StringUtils.isBlank(bodyPart.getFileName())) {
                    continue;
                }
                InputStream stream  = bodyPart.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                System.out.println("Text from file: ");
                while (br.ready()) {
                    System.out.println(br.readLine());
                }
            }
    }


    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
            this.readFile(message);
        }
        return result;
    }


    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break;
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }

    private void delete(Message message) throws MessagingException, IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));

        String subject = message.getSubject();
        System.out.print("Do you want to delete this message [y/n] ? ");
        String ans = reader.readLine();
        if ("Y".equals(ans) || "y".equals(ans)) {
            message.setFlag(Flags.Flag.DELETED, true);
            System.out.println("Marked DELETE for message: " + subject);
        } else if ("n".equals(ans)) {
            return;
        }
    }

    public MailMessage[] getMessages() throws MessagingException, IOException {
        MailMessage[] messages = new MailMessage[this.messages.length];
        for (int i = 0; i < this.messages.length; i++) {
            Message message = this.messages[i];
            messages[i] = new MailMessage(message.getFrom()[0].toString(), message.getReplyTo().toString(), message.getSubject(), message.getContent().toString());
        }
        return messages;
    }

    public void closeStore() throws MessagingException {
        this.emailFolder.close(true);
        this.store.close();
    }

    public void fetch() {
        try {
            this.getFromMails();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}