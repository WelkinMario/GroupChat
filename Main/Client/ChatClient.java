package Main.Client;

import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5555);
        System.out.println("Client connected");
        new Thread(new ClientSender(socket)).start();
        new Thread(new ClientListen(socket)).start();
    }
}
