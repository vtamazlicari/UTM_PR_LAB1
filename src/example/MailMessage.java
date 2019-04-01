package example;

public class MailMessage {
    String to;
    String from;
    String subject;
    String text;
    MailMessage(String from, String to, String subject, String text) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.text = text;
    }
}
