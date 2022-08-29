package Main.Server;

import lib.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;


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
                    case SIGNUP:
                        if (handleSignup(para) == 0) break;
                    case LOGIN:
                        handleLogin(para);
                        break;
                    case GUEST:
                        client.setUsername("Guest" + client.socket.getPort());
                        client.setStatus(Status.GUEST);
                        break;
                    default:
                        wr.println("NO" + SLASH + "Unknown command");
                        wr.flush();
                }
            }

            wr.println("OK" + SLASH + client.getUsername());
            wr.flush();
            broadCast(client.getUsername() + " joined chat room");

            while(client.status != Status.WAIT) {
                String str = rr.readLine();
                broadCast(client.getUsername() + ": " + str);
            }
        } catch (IOException e) {
            ChatServer.rmvClient(client);
            broadCast(socket.getPort() + " left chat room");
            System.out.println("Disconnected from " + socket.getInetAddress()
                                + ":" + socket.getPort());
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized int handleSignup(String[] para) throws IOException {
        PrintWriter wr = new PrintWriter(socket.getOutputStream());
        Map<String, String> users = ChatServer.getUserlist();
        if (users == null) {
            throw new IOException("Server cannot access userlist data");
        }
        if (ChatServer.isValidString(para[1]) && (ChatServer.isValidString(para[2])) &&
                !users.containsKey(para[1])) {
            ChatServer.signupUser(para[1], para[2]);
            return 1;
        } else {
            wr.println("NO" + SLASH + "the name already exists or it is invalid");
            wr.flush();
            return 0;
        }
    }

    private void handleLogin(String[] para) throws IOException {
        PrintWriter wr = new PrintWriter(socket.getOutputStream());
        Map<String, String> users = ChatServer.getUserlist();
        if (users == null) {
            throw new IOException("Server cannot access userlist data");
        }
        if (ChatServer.isValidString(para[1]) && users.containsKey(para[1]) && users.get(para[1]).equals(para[2])) {
            client.setUsername(para[1]);
            client.setStatus(Status.LOGIN);
        } else {
            wr.println("NO" + SLASH + "the name does not exist or the password is incorrect");
            wr.flush();
        }
    }
}
