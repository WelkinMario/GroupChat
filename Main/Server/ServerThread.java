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
        init();
    }

    private void init() {
        broadCast(socket.getPort() + " joined chat room");
    }

    private void broadCast(String msg) {
        try {
            for (Socket s : ChatServer.socketList) {
                PrintWriter wr = new PrintWriter(s.getOutputStream());
                wr.println(msg);
                wr.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader rr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true) {
                String str = rr.readLine();
                broadCast(socket.getPort() + ": " + str);
            }
        } catch (IOException e) {
            ChatServer.socketList.remove(socket);
            broadCast(socket.getPort() + " left chat room");
            System.out.println("Disconnected to " + socket.getInetAddress()
                                + ":" + socket.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
