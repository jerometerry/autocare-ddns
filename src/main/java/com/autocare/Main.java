package com.autocare;

import javax.mail.MessagingException;
import java.io.IOException;

public class Main {
    private static final String SUBJECT_SUFFIX = " Public IP";

    public static void main(String[] args) throws IOException, MessagingException {
        String computerName = getComputerName(args);
        String subject = getSubject(computerName);
        String body = getBody();
        String[] to = getRecipients();
        EmailService.send(subject, body, to);
    }

    private static String getComputerName(String[] args) {
        return args.length > 0 ? args[0] : "";
    }

    private static String getSubject(String computerName) {
        return computerName + SUBJECT_SUFFIX;
    }

    private static String getBody() throws IOException {
        return WhatsMyIp.get();
    }

    private static String[] getRecipients() {
        return new String[]{ Settings.RECIPIENT };
    }
}