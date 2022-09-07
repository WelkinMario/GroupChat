package Main.Server;

import lib.Status;

import java.net.Socket;

public class ServerClient {
    /*
    The object for locally storing the client information
    Reserved for future use/expansion
     */
    public Socket socket;
    public Status status;

    private String username;

    public ServerClient(Socket socket) {
        this.socket = socket;
        status = Status.WAIT;
    }

    public String getUsername() {
        return username;
    }
    /*
    public String getPassword() {
        return password;
    }
    */
    public boolean isUsername(String str) {
        return username.equals(str);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
