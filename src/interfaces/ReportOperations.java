package interfaces;

import java.util.List;
import java.util.Map;

public interface ReportOperations {

    int getTotalBookings();

    double getTotalRevenue();

    List<Map<String, Object>> getEventWiseRevenue();

    String getPopularEvent();

    List<Map<String, Object>> getPaymentStatusSummary();
}