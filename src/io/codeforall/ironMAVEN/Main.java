package io.codeforall.ironMAVEN;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        SRV webServer = new SRV();
        try {
            webServer.webServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
