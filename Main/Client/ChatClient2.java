package Main.Client;

import java.net.Socket;

public class ChatClient2 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 5555);
        System.out.println("Chatroom connected");
        new Thread(new ClientSender(socket)).start();
        new Thread(new ClientListen(socket)).start();
    }
}
