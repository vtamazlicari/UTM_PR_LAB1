package example;

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
  private static String SMTP_HOST_NAME = "127.0.0.1";
  private static String SMTP_AUTH_USER = "";
  private static String SMTP_AUTH_PWD  = "";
  static MailMessage[] messages = new MailMessage[0];
  static DefaultListModel listModel = new DefaultListModel();
  static JList list1 = new JList(listModel);
  static JScrollPane scrollableList = new JScrollPane(list1);

  public static void main(String[] args) throws Exception {
    JTextField user = new JTextField("");
    JTextField password = new JTextField("");

    JTextField pop3User = new JTextField("");
    JTextField pop3Password = new JTextField("");
    JTextField pop3Text = new JTextField("");

    JTextField from = new JTextField("");
    JTextField to = new JTextField("");

    JTextField subject = new JTextField("");
    JTextField text = new JTextField("");

    JButton submitButton = new JButton("Submit");
    JButton pop3SubmitButton = new JButton("Submit");
    pop3SubmitButton.setBounds(50, 150, 100, 30);

    JPanel smtp = new JPanel(new GridLayout(0, 1));
    JPanel pop3 = new JPanel(new GridLayout(0, 1));
    smtp.add(new JLabel("User name"));
    smtp.add(user);
    smtp.add(new JLabel("Password"));
    smtp.add(password);
    smtp.add(new JLabel("From"));
    smtp.add(from);
    smtp.add(new JLabel("To"));
    smtp.add(to);
    smtp.add(new JLabel("Subject"));
    smtp.add(subject);
    smtp.add(new JLabel("Text"));
    smtp.add(text);
    smtp.add(submitButton);

    pop3.add(new JLabel("User name"));
    pop3.add(pop3User);
    pop3.add(new JLabel("Password"));
    pop3.add(pop3Password);
    pop3.add(pop3SubmitButton);
    pop3.add(pop3Text);
    pop3.add(scrollableList);

    BorderLayout borderLayout1 = new BorderLayout();



    JFrame frame = new JFrame("Mail client");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JTabbedPane tabbedPane = new JTabbedPane();

    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    tabbedPane.addTab("SMTP", new ImageIcon("yourFile.gif"), smtp);

    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    tabbedPane.addTab("POP3", new ImageIcon("yourFile.gif"), pop3);

    frame.add(tabbedPane, BorderLayout.CENTER);
    frame.setSize(400, 400);
    frame.setVisible(true);

    submitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        SMTP_AUTH_USER = user.getText();
        System.out.println(SMTP_AUTH_USER);
        SMTP_AUTH_PWD = password.getText();
        System.out.println(SMTP_AUTH_PWD);
        String fromAddress = from.getText();
        String toAddress = to.getText();
        String subjectText = subject.getText();
        String message = text.getText();
        try {
          SendEmail email = new SendEmail(SMTP_HOST_NAME, SMTP_AUTH_USER, SMTP_AUTH_PWD);
          email.send(fromAddress, toAddress, subjectText, message);
        } catch (MessagingException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    pop3SubmitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        SMTP_AUTH_USER = pop3User.getText();
        SMTP_AUTH_PWD = pop3Password.getText();
        try {
          FetchingEmail emails = new FetchingEmail(SMTP_HOST_NAME, SMTP_AUTH_USER, SMTP_AUTH_PWD);
          emails.fetch();
          String[] allUsers = emails.getFromMails();
          messages = emails.getMessages();
          for (int i = 0; i < allUsers.length; i++)
            listModel.addElement(allUsers[i]);
          emails.closeStore();
        } catch (MessagingException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    list1.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
        JList list = (JList) evt.getSource();
        if (evt.getClickCount() == 2) {
          int index = list.locationToIndex(evt.getPoint());
          System.out.println(index);
          pop3Text.setText("From: " + messages[index].from + "\nSubject: " + messages[index].subject + "\nText: " + messages[index].text);
        }
      }
    });
  }

}