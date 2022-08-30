package JDA;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Listener implements EventListener {
    private final ArrayList<EventAction<? extends GenericEvent>> eventActions = new ArrayList<>();

    public <E extends GenericEvent> void addEventAction(Class<E> event, Consumer<E> action) {
        eventActions.add(new EventAction<>(event, action));
    }

    public void addEventAction(EventAction<? extends GenericEvent> action) {
        eventActions.add(action);
    }

    @Override public void onEvent(@NotNull GenericEvent genericEvent) {
        eventActions.forEach(eventAction -> eventAction.execute(genericEvent));
    }
}