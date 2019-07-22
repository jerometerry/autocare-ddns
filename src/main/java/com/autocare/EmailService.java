package com.autocare;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

class EmailService {
    private static final String SMTP_PROTOCOL = "smtp";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String TLS_KEY = "mail.smtp.starttls.enable";
    private static final String ENABLED_VALUE = "true";
    private static final String SMTP_HOST_KEY = "mail.smtp.host";
    private static final String SMTP_PORT_KEY = "mail.smtp.port";
    private static final String SMTP_PORT = "587";
    private static final String AUTH_KEY = "mail.smtp.auth";

    static void send(String subject, String body, String[] to) throws MessagingException {
        Session session = Session.getInstance(getProperties(), getAuthenticator());
        MimeMessage message = createMessage(subject, body, to, session);
        try (Transport transport = session.getTransport(SMTP_PROTOCOL)) {
            transport.connect(SMTP_HOST, Settings.USER_NAME, Settings.PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
        }
    }

    private static MimeMessage createMessage(String subject, String body, String[] to, Session session) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(Settings.USER_NAME));
        addRecipients(to, message);
        message.setSubject(subject);
        message.setText(body);
        return message;
    }

    private static void addRecipients(String[] recipients, MimeMessage message) throws MessagingException {
        for (String r : recipients) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(r));
        }
    }

    private static Authenticator getAuthenticator() {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Settings.USER_NAME, Settings.PASSWORD);
            }
        };
    }

    private static Properties getProperties() {
        Properties props = System.getProperties();
        props.put(TLS_KEY, ENABLED_VALUE);
        props.put(SMTP_HOST_KEY, SMTP_HOST);
        props.put(SMTP_PORT_KEY, SMTP_PORT);
        props.put(AUTH_KEY, ENABLED_VALUE);
        return props;
    }
}
