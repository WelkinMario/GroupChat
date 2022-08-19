package Main.Server;

import lib.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;


public class ServerThread implements Runnable {
    public ServerClient client;
    public Socket socket;

    public static String SLASH = "/";

    public ServerThread(ServerClient client) {
        this.client = client;
        this.socket = client.socket;
    }

    private void broadCast(String msg) {
        try {
            for (Socket s : ChatServer.socketList) {
                PrintWriter wr = new PrintWriter(s.getOutputStream());
                wr.println(msg);
                wr.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader rr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter wr = new PrintWriter(socket.getOutputStream());
            while(client.status == Status.WAIT) {
                String str = rr.readLine();
                String[] para = str.split(SLASH);
                switch (Status.valueOf(para[0])) {
                    case SIGNUP, LOGIN -> {
                        client.setUsername(para[1]);
                        client.setPassword(para[2]);
                        client.setStatus(Status.LOGIN);
                    } // TODO: create local file to store user information and check password validity
                    case GUEST -> {
                        client.setUsername("Guest" + client.socket.getPort());
                        client.setStatus(Status.GUEST);
                    }
                    default -> {
                        wr.println("NO" + SLASH + "Incorrect code");
                        wr.flush();
                    }
                }
            }

            wr.println("OK" + SLASH + client.getUsername());
            wr.flush();
            broadCast(client.getUsername() + " joined chat room");

            while(client.status != Status.WAIT) {
                String str = rr.readLine();
                broadCast(socket.getPort() + ": " + str);
            }
        } catch (IOException e) {
            ChatServer.rmvClient(client);
            broadCast(socket.getPort() + " left chat room");
            System.out.println("Disconnected to " + socket.getInetAddress()
                                + ":" + socket.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
