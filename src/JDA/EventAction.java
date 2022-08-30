package JDA;

import net.dv8tion.jda.api.events.GenericEvent;

import java.util.function.Consumer;

public class EventAction<E extends GenericEvent> {

    private final Consumer<E> action;
    private final Class<E> event;

    public EventAction(Class<E> event, Consumer<E> action) {
        this.action = action;
        this.event = event;
    }

    public void execute(GenericEvent genericEvent) {
        if (event.isInstance(genericEvent)) {
            action.accept(event.cast(genericEvent));
        }
    }
}