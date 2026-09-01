package model;

public class Event {

    private int eventId;
    private String eventName;
    private String eventDate;
    private String eventTime;
    private String venue;
    private double ticketPrice;
    private int totalTickets;

    // Default Constructor
    public Event() {
    }

    // Parameterized Constructor
    public Event(int eventId, String eventName, String eventDate,
                 String eventTime, String venue, double ticketPrice,
                 int totalTickets) {

        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.venue = venue;
        this.ticketPrice = ticketPrice;
        this.totalTickets = totalTickets;
    }

    // Getters and Setters

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventTime='" + eventTime + '\'' +
                ", venue='" + venue + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", totalTickets=" + totalTickets +
                '}';
    }
}