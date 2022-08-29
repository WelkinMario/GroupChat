package Main.Client;

import lib.Status;
import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ChatClient {
    protected static String username;
    protected static String password;

    public static String SLASH = "/";

    private static void login(Socket socket) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter wr = new PrintWriter(socket.getOutputStream());
            BufferedReader rr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Please choose from the following options: \n\t" +
                    "1. Login   2. Sign up   3. Continue as a guest");
            String op = input.readLine();
            if (op.equals("1")) {
                while (true) {
                    getInfo(input);
                    wr.println(Status.LOGIN + SLASH + username + SLASH + password);
                    wr.flush();
                    String rst = rr.readLine();
                    String[] res = rst.split(SLASH);
                    if (res[0].equals("OK")) {
                        System.out.println("login succeeded, " + res[1]);
                        break;
                    } else {
                        System.out.println("Error: " + res[1] + "\nPlease try again");
                    }
                }
            } else if (op.equals("2")) {
                while (true) {
                    getInfo(input);
                    wr.println(Status.SIGNUP + SLASH + username + SLASH + password);
                    wr.flush();
                    String rst = rr.readLine();
                    String[] res = rst.split(SLASH);
                    if (res[0].equals("OK")) {
                        System.out.println("Signup succeeded, " + res[1]);
                        break;
                    } else {
                        System.out.println("Error: " + res[1] + "\nPlease try again");
                    }
                }
            } else if (op.equals("3")) {
                wr.println(Status.GUEST);
                wr.flush();
                String rst = rr.readLine();
                String[] res = rst.split(SLASH);
                if (res[0].equals("OK")) {
                    System.out.println("Welcome, " + res[1]);
                } else {
                    System.out.println("Error: " + res[1] + "\nPlease try again");
                    login(socket);
                }
            } else {
                System.out.println("Error: " + "Unknown command" + "\nPlease try again");
                login(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getInfo(BufferedReader input) throws IOException {
        System.out.print("\tUsername: ");
        username = input.readLine();
        System.out.print("\tPassword: ");
        password = input.readLine();
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5555);
            System.out.println("Server connected");
            login(socket);
            new Thread(new ClientSender(socket)).start();
            new Thread(new ClientListen(socket)).start();
        } catch (IOException e) {
            System.out.println("Error: Server is not up");
        }
    }
}
