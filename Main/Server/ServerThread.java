package Main.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable {
    public Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader rr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true) {
                String str = rr.readLine();
                for (Socket s : ChatServer.socketList) {
                    PrintWriter wr = new PrintWriter(s.getOutputStream());
                    wr.println(socket.getPort() + ": " + str);
                    wr.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
