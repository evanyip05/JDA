package JDA;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;

public class Main {

    public static final Client c = new Client("");
    public static final Listener l = new Listener();

    public static final String SELF = "JavaTest";

    public static void main(String[] args) throws IOException, InterruptedException {
        // add event action, using some Event class from jda (net.dv8tion.jda.api.events...)
        // message detect is a subclass of eventAction btw. signatures:
        // public                          void addEventAction(EventAction<? extends GenericEvent> action)
        // public <E extends GenericEvent> void addEventAction(Class<E> event, Consumer<E> action)
        l.addEventAction(MessageReceivedEvent.class, messageReceivedEvent -> {
            System.out.println(messageReceivedEvent.getMessage().getContentRaw());
            run(new String[] {"node index.js " + messageReceivedEvent.getChannel().getName() + " ch:", "node index.js " + messageReceivedEvent.getMessage().getAuthor().getAsTag()+":   "+messageReceivedEvent.getMessage().getContentRaw(), "node index.js --------------------------------------------------------------------------"},
                "C:\\Users\\evany\\Desktop\\GSheets"
            );
        });

        c.addListener(l);
    }

    public static void run(String[] actions, String dir) {
        try {
            for (String action : actions) {
                Process p = Runtime.getRuntime().exec(action, null, new File(dir));

                if (p.waitFor() == 0) {System.out.println("done");}
                else {System.out.println("fail");}
            }
        } catch (IOException | InterruptedException e) {e.printStackTrace();}
    }

    public static class MessageReply extends EventAction<MessageReceivedEvent> {
        public MessageReply(String reply) {
            super(MessageReceivedEvent.class, messageReceivedEvent -> {
                Message message = messageReceivedEvent.getMessage();
                MessageChannel channel = message.getChannel();

                if (!message.getAuthor().getName().equals(SELF)) {
                    channel.sendMessage(reply).queue();
                }
            });        }
    }

    public static class MessageDetect extends EventAction<MessageReceivedEvent> {
        public MessageDetect(String detect, String reply) {
            super(MessageReceivedEvent.class, messageReceivedEvent -> {
                Message message = messageReceivedEvent.getMessage();
                MessageChannel channel = message.getChannel();

                if (!message.getAuthor().getName().equals(SELF)&&message.getContentRaw().equals(detect)) {
                    channel.sendMessage(reply).queue();
                }
            });
        }
    }
}
