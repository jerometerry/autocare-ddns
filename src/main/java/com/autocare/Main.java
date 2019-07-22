package com.autocare;

import java.util.*;
import javax.mail.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;
import java.net.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, MessagingException {

        String computerName = args.length > 0 ? args[0] : "";
        String[] to = { Settings.RECIPIENT };
        String subject = computerName + " Public IP";
        String body = getPublicIp();

        sendFromGMail(Settings.USER_NAME, Settings.PASSWORD, to, subject, body);
    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) throws MessagingException {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));
        InternetAddress[] toAddress = new InternetAddress[to.length];

        for( int i = 0; i < to.length; i++ ) {
            toAddress[i] = new InternetAddress(to[i]);
        }

        for (InternetAddress address : toAddress) {
            message.addRecipient(Message.RecipientType.TO, address);
        }

        message.setSubject(subject);
        message.setText(body);

        try (Transport transport = session.getTransport("smtp")) {
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
        }
    }

    private static String getPublicIp() throws IOException {
        URL whatismyip = new URL(Settings.WHATS_MY_IP);
        try (InputStream is = whatismyip.openStream();
             InputStreamReader streamReader = new InputStreamReader(is);
             BufferedReader in = new BufferedReader(streamReader);) {
            return in.readLine();
        }
    }
}