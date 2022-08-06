package Main;

import java.net.Socket;

public class ChatClient {
    public static void main(String args[]) throws Exception {
        Socket socket = new Socket("localhost", 5555);
        System.out.println("Client connected");
    }
}
