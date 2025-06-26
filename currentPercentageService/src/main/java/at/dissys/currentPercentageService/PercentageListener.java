package at.dissys.currentPercentageService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PercentageListener {

    @Autowired
    private CurrentPercentageService service;

    @RabbitListener(queues = "updates")
    public void onMessage(String hourStr) {
        try {
            LocalDateTime hour = LocalDateTime.parse(hourStr);
            service.calculateAndSavePercentage(hour);
            System.out.println("Updated percentage for hour: " + hour);
        } catch (Exception e) {
            System.err.println("Failed to process update: " + hourStr + " - " + e.getMessage());
        }
    }
}
