package example;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class MyAuthenticator extends Authenticator {

    protected PasswordAuthentication passwordAuthentication;

    public MyAuthenticator(String user, String password) {
        this.passwordAuthentication = new PasswordAuthentication(user, password);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return passwordAuthentication;
    }
}