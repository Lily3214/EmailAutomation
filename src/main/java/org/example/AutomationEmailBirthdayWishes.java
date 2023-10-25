package org.example;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class AutomationEmailBirthdayWishes {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "465";
    private static final boolean SMTP_SSL_ENABLED = true;

    private static final String FROM_EMAIL = "yue4509@gmail.com";
    private static final String APP_SPECIFIC_PASSWORD = "your_specific_password";
    public static void main(String[] args) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Birthday.csv"));
            String line;
            boolean skipHeader = true;
            while ((line = reader.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    String email = parts[5].trim();
                    String firstName = parts[3].trim();
                    LocalDate dob = LocalDate.parse(parts[2].trim(), DateTimeFormatter.ofPattern("M/d/yyyy")); // Date of birth from the CSV

                    if (isBirthdayToday(dob)) {
                        sendBirthdayWish(firstName, email);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isBirthdayToday(LocalDate dob) {
        LocalDate currentDate = LocalDate.now();
        return dob.getMonth() == currentDate.getMonth() && dob.getDayOfMonth() == currentDate.getDayOfMonth();
    }

    private static void sendBirthdayWish(String recipientName, String recipientEmail) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.ssl.enable", SMTP_SSL_ENABLED);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_SPECIFIC_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Happy Birthday, " + recipientName + "!");
            message.setText("Dear " + recipientName + ",\n\nWishing you a wonderful and joyous birthday!");

            Transport.send(message);
            System.out.println("Birthday wish sent to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
