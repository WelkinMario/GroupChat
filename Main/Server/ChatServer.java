package Main.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    public static List<Socket> socketList = new ArrayList<Socket>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5555);
        System.out.println("Chat room created");
        while (true) {
            Socket socket = server.accept();
            System.out.println("Connected from " + socket.getInetAddress()
                                + ":" + socket.getPort());
            new Thread(new ServerThread(socket)).start();
        }
    }
}