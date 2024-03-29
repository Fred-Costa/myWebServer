package io.codeforall.ironMAVEN;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SRV {

    public void webServer() throws IOException {

        int port = 8080;

        String pathFile = "resources/skeleton.html";

        String aidosPic = "resources/img.png";
        File file = new File("resources/img.png");

        String headerHTML = "HTTP/1.0 200 Document Follows\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: <file_byte_size> \r\n" +
                "\r\n";

        String headerIMG = "HTTP/1.0 200 Document Follows\r\n" +
                "Content-Type: image/<png> \r\n" +
                "Content-Length: " + file.length() + "\r\n" +
                "\r\n";

        String headerNotFound = "HTTP/1.0 404 Not Found\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "Content-Length: <file_byte_size> \r\n" +
                "\r\n";


        // bind the socket to specified port
        System.out.println("Binding to port " + port);
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("Server started: " + serverSocket);
        System.out.println("****************************************");

        Socket clientSocket;

        while (true) {

            // block waiting for a client to connect
            System.out.println("Waiting for a client connection");
            clientSocket = serverSocket.accept();

            Thread thread = new Thread(new ThreadSRV(clientSocket));
            thread.start();

            System.out.println("JA INICIEI A THREAD.");


            /*
            // handle client connection
            System.out.println("Client accepted -> " + clientSocket);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String line = in.readLine();

            // if received /quit close break out of the reading loop
            if (line == null) {
                System.out.println("Client closed, exiting");
                break;
            }

            // show the received line to the console
            /*
            System.out.println(line);
            System.out.println("-------------------------");

            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            if (line.equals("GET /skeleton.html HTTP/1.1")) {
                out.writeBytes(headerHTML);
                byte[] bytes = Files.readAllBytes(Paths.get(pathFile));
                out.write(bytes);

            }



            if (line.equals("GET /aidos.png HTTP/1.1")) {
                out.writeBytes(headerIMG);
                byte[] bytes = Files.readAllBytes(Paths.get(aidosPic));
                out.write(bytes);
            }

            clientSocket.close();
        }

             */

        }
        }

         class ThreadSRV implements Runnable {
            int port = 8080;
            Socket clientSocket;
            BufferedReader in;
            DataOutputStream out;
            String headerHTML = "HTTP/1.0 200 Document Follows\r\n" +
                    "Content-Type: text/html; charset=UTF-8\r\n" +
                    "Content-Length: <file_byte_size> \r\n" +
                    "\r\n";
            String pathFile = "resources/skeleton.html";

            public ThreadSRV(Socket clientSocket) {

                this.clientSocket = clientSocket;
            }

            @Override
            public void run() {
                System.out.println("Estou na THREAD");
                System.out.println(Thread.currentThread().getName());

                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String line = in.readLine();

                    out = new DataOutputStream(clientSocket.getOutputStream());

                    if (line.equals("GET /skeleton.html HTTP/1.1")) {
                        out.writeBytes(headerHTML);
                        byte[] bytes = Files.readAllBytes(Paths.get(pathFile));
                        out.write(bytes);
                        out.flush();

                    }

                    clientSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

