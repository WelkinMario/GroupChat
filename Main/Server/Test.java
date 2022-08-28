package Main.Server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Test {

    private static final String DATA_SOURCE = "./Main/data/Userlist.txt";

    public static void main(String[] args) {
        String data = "ad 0\n";
        try {
            FileOutputStream fos = new FileOutputStream(DATA_SOURCE, true);
            fos.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
