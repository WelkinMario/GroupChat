package Main.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientListen implements Runnable {
    /*
    The object works on receiving messages from server and print them out.
     */
    public Socket socket;

    public ClientListen(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader rr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String str = rr.readLine();
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
