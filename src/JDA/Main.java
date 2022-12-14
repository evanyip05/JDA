package JDA;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.IOException;

public class Main {

    //public static final Client c = new Client("./Token/token.txt");
    public static final Listener l = new Listener();

    public static final String SELF = "JavaTest";

    public static void main(String[] args) throws IOException, InterruptedException {
        // add event action, using some Event class from jda (net.dv8tion.jda.api.events...)
        // message detect is a subclass of eventAction btw. signatures:
        // public                          void addEventAction(EventAction<? extends GenericEvent> action)
        // public <E extends GenericEvent> void addEventAction(Class<E> event, Consumer<E> action)
        // sheetsCLI -> token dir -> sheetID -> message
        l.addEventAction(MessageReceivedEvent.class, messageReceivedEvent -> {
            System.out.println(messageReceivedEvent.getMessage().getContentRaw());

            sendStringToSheets(messageReceivedEvent.getChannel().getName() + " ch: ", "15f3IR2T0ItInwfv9xe1JJmtzTq5W2YVlHSgZKbIq24Q");
            sendStringToSheets(messageReceivedEvent.getAuthor().getName() + ":   " + messageReceivedEvent.getMessage().getContentRaw(), "15f3IR2T0ItInwfv9xe1JJmtzTq5W2YVlHSgZKbIq24Q");
            sendStringToSheets("-----------------------------------------------------------------------------------------------------", "15f3IR2T0ItInwfv9xe1JJmtzTq5W2YVlHSgZKbIq24Q");
        });

        l.addEventAction(MessageReceivedEvent.class, messageReceivedEvent -> {
            if (messageReceivedEvent.getMessage().getContentRaw().equals("closeBot")) {
                System.exit(0);
            }
        });

        System.out.println("sending...");
        sendStringToSheets("test string to sheet method2", "1WnlzhJDbPux3vASM8dkZmBQ-ucQ6zf0JiUnrz1q20DM");

        //c.addListener(l);
    }

    public static void sendStringToSheets(String string, String sheetID) {
        run(new String[] {"sheetsCli.exe ./Token/credentials.json " + sheetID + " " + string}, "./");
    }

    public static void run(String[] actions, String dir) {
        try {
            for (String action : actions) {
                Process p = Runtime.getRuntime().exec(action, null, new File(dir));

                if (p.waitFor() == 0) {System.out.println("done");}
                else {System.out.println("failed");}
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
            });
        }
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
