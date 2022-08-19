package Main.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    public static List<Socket> socketList = new ArrayList<>();
    public static List<ServerClient> clientList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5555);
        System.out.println("Chat room created");
        while (!server.isClosed()) {
            Socket socket = server.accept();
            ServerClient client = new ServerClient(socket);
            addClient(client);
            System.out.println("Connected from " + socket.getInetAddress()
                                + ":" + socket.getPort());
            new Thread(new ServerThread(client)).start();
        }
    }

    public static synchronized void addClient(ServerClient client) {
        clientList.add(client);
        socketList.add(client.socket);
    }

    public static synchronized void rmvClient(ServerClient client) {
        clientList.remove(client);
        socketList.remove(client.socket);
    }
}