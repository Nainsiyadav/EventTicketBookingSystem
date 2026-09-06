package main;

import service.ReportService;

public class TestReport {

    public static void main(String[] args) {

        ReportService reportService = new ReportService();

        int totalBookings = reportService.getTotalBookings();

        System.out.println("========== TOTAL BOOKINGS ==========");
        System.out.println("Total Bookings: " + totalBookings);
    }
}