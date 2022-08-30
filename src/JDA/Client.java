package JDA;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Client {
    private JDA client;

    public Client(String tokenDir) {
        try {client = JDABuilder.createDefault(new Scanner(new File(tokenDir)).next()).build();}
        catch (LoginException | FileNotFoundException e) {
            System.out.println("token prob invalid");
            System.exit(0);
        }
    }

    public void addListener(Listener l) {client.addEventListener(l);}
}