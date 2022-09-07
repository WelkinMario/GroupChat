package Main.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ChatServer {
    /*
    The main class for server;
    Open ChatClient to get connection;
     */
    public static List<Socket> socketList = new ArrayList<>();
    public static List<ServerClient> clientList = new ArrayList<>();

    // the address to where usernames and passwords are stored
    private static final String DATA_SOURCE = "./Main/data/Userlist.txt";

    // the main method to set up the server
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5555);
        System.out.println("Chat room created");
        while (!server.isClosed()) {
            // Listening to new connections, forks thread for each client
            Socket socket = server.accept();
            ServerClient client = new ServerClient(socket);
            addClient(client);
            System.out.println("Connected from " + socket.getInetAddress()
                                + ":" + socket.getPort());
            new Thread(new ServerThread(client)).start();
        }
    }

    // add client info to local variable. Synchronization is used to avoid inconsistency
    public static synchronized void addClient(ServerClient client) {
        clientList.add(client);
        socketList.add(client.socket);
    }

    public static synchronized void rmvClient(ServerClient client) {
        clientList.remove(client);
        socketList.remove(client.socket);
    }

    // get names and passwords in data file into a map
    public static synchronized Map<String, String> getUserlist() {
        try {
            File file = new File(DATA_SOURCE);
            if (!file.exists()) {
                boolean placer = file.createNewFile();
            }
            Scanner scanner = new Scanner(file);

            Map<String, String> users = new HashMap<>();
            while (scanner.hasNextLine()) {
                String[] namePass = scanner.nextLine().split(" ");
                users.put(namePass[0], namePass[1]);
            }

            scanner.close();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // add name and password to the data file.
    public static synchronized void signupUser(String name, String pass) {
        String data = name + " " + pass + "\n";
        try {
            File file = new File(DATA_SOURCE);
            if (!file.exists()) {
                boolean placer = file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(DATA_SOURCE, true);
            fos.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // a simple requirement on the username and password
    public static boolean isValidString(String str) {
        return !str.trim().equals("") && str.length() < 32 && !str.matches(".*?(/s|\\|/).*");
    }
}