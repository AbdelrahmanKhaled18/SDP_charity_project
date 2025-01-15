package model.DesignPatterns;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


import java.util.Properties;

public class VolunteerObserver implements IObserver {
    private ISubject ISubjectRef;
    private String EmailAddress;

    public VolunteerObserver(ISubject subject, String EmailAddress) {
        this.EmailAddress = EmailAddress;
        this.ISubjectRef = subject;
        ISubjectRef.registerObservers(this);
    }

    @Override
    public void update(double currentCollectedAmount) {
        sendEmail(this.EmailAddress, "Collected Amount Update", "Total Collected Amount : "
                + currentCollectedAmount);
    }

    private void sendEmail(String to, String subject, String body) {
        final String from = "mohamed.elkhattam2@gmail.com";
        final String password = "prch otsn tuhm jgje";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email to: " + to);
        }
    }
}
