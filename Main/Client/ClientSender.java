package Main.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSender implements Runnable {
    public Socket socket;

    public ClientSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader rr = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter wr = new PrintWriter(socket.getOutputStream());
            while (true) {
                String str = rr.readLine();
                wr.println(str);
                wr.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
