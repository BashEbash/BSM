package com.noteapp.security;




import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PasswordManager {

    static String filename = "/data/user/0/com.noteapp/files/pass.txt";

    public static boolean setPasswordCheck() {

        File passfile = new File(filename);

        return passfile.exists() && passfile.length() != 0;
    }

    public static void createPasswordFile(String password) throws IOException {
        new File(filename).createNewFile();
        FileWriter fw = new FileWriter(filename);
        fw.write(password);
        fw.close();

    }
}
