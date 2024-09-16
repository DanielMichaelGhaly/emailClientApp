package emailclientapplication;

import java.util.Properties;
import java.util.Scanner;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailApp {
    public static void sendEmail(String recepient, String myEmail, String password, String subject, String text){
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myEmail, password);
            }
        });

        Message message = prepareMessage(session,myEmail,recepient, subject, text);
        
        try {
            Transport.send(message);
            System.out.println("Message sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    public static Message prepareMessage(Session session, String myEmail, String recepient, String subject,String text)
    {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(subject);
            message.setText(text);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the recepient email.");
        String recepient = sc.nextLine();
        System.out.println("Please enter your email.");
        String sender = sc.nextLine();
        System.out.println("Please enter your password.");
        String password = sc.nextLine();
        System.out.println("Please enter the subject");
        String subject = sc.nextLine();
        System.out.println("Please enter the email body.");
        String text = sc.nextLine();
        sendEmail(recepient,sender,password, subject, text);
        sc.close();
    }
}
