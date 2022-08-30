package JDA;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Client {
    private JDA client;

    public Client(String token) {
        try {client = JDABuilder.createDefault(token).build();}
        catch (LoginException e) {
            System.out.println("token prob invalid");
            System.exit(0);
        }
    }

    public void doStuff() {
    }

    public void addListener(Listener l) {client.addEventListener(l);}
}