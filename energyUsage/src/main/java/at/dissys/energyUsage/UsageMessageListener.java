package at.dissys.energyUsage;

import at.dissys.shared.messages.HourlyUsageUpdateMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UsageMessageListener {

    @Autowired
    private HourlyUsageRepository repository;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Instead of listening to individual energy messages, process aggregated data periodically
    @Scheduled(fixedRate = 10000) // Every 10 seconds
    public void processHourlyUsageData() {
        LocalDateTime currentHour = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        
        // Get or create hourly usage data
        HourlyUsage usage = (HourlyUsage) repository.findById(currentHour).orElseGet(() -> {
            HourlyUsage newUsage = new HourlyUsage();
            newUsage.setHour(currentHour);
            return newUsage;
        });

        // This service can focus on aggregating data from multiple sources
        // or performing calculations, rather than processing individual energy messages
        
        // Send usage updates to other services
        HourlyUsageUpdateMessage updateMsg = new HourlyUsageUpdateMessage(
                usage.getHour(), usage.getCommunityProduced(), 
                usage.getCommunityUsed(), usage.getGridUsed());
        
        rabbitTemplate.convertAndSend("usage-updates", updateMsg);
        
        System.out.println("EnergyUsage: Published aggregated usage data for hour: " + currentHour);
    }
}
