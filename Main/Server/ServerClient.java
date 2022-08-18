package Main.Server;

import java.net.Socket;

public class ServerClient {
    public Socket socket;

    private String username;
    private String password;

    public ServerClient(Socket socket) {
        this.socket = socket;
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

    public boolean isPassword(String str) {
        return password.equals(str);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
