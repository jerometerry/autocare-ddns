package com.autocare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

class WhatsMyIp {
    private static final String WHATS_MY_IP = "http://checkip.amazonaws.com";

    static String get() throws IOException {
        try (InputStream is = new URL(WHATS_MY_IP).openStream();
             InputStreamReader streamReader = new InputStreamReader(is);
             BufferedReader in = new BufferedReader(streamReader)) {
            return in.readLine();
        }
    }
}
