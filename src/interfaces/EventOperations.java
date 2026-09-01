package interfaces;

import model.Event;
import java.util.List;

public interface EventOperations {

    // Create
    boolean addEvent(Event event);

    // Read
    List<Event> getAllEvents();

    // Update
    boolean updateEvent(Event event);

    // Delete
    boolean deleteEvent(int eventId);
}