package Main.Client;

import lib.Status;
import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    protected static String username;
    protected static String password;

    public static String SLASH = "/";

    private static void login(Socket socket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter wr = new PrintWriter(socket.getOutputStream());
            BufferedReader rr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Please login");

            boolean log = false;
            while (!log) {
                System.out.print("Username");
                username = input.readLine();
                System.out.print("Password");
                password = input.readLine();
                wr.println(Status.LOGIN + SLASH + username + SLASH + password);
                wr.flush();
                String rst = rr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Client connected");
            login(socket);
            new Thread(new ClientSender(socket)).start();
            new Thread(new ClientListen(socket)).start();
        } catch (IOException e) {
            System.out.println("Error: Server is not up");
        }
    }
}
