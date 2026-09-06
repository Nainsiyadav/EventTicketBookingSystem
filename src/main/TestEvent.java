package main;

import model.Event;
import service.EventService;

public class TestEvent {

    public static void main(String[] args) {

        // Create EventService object
        EventService eventService = new EventService();

        // Create Event object
        Event event = new Event();

        event.setEventName("Music Concert");
        event.setEventDate("2026-10-15");
        event.setEventTime("18:30:00");
        event.setVenue("Mumbai Arena");
        event.setTicketPrice(500.00);
        event.setTotalTickets(100);

        // Add event
        boolean result = eventService.addEvent(event);

        if (result) {
            System.out.println("Event Added Successfully!");
        } else {
            System.out.println("Event Addition Failed!");
        }
    }
}