package at.dissys.currentPercentageService;

import at.dissys.shared.messages.PercentageUpdateMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CurrentPercentageService {
    private HourlyUsageRepositoryPercentage hourlyUsageRepository;
    private CurrentPercentageRepo currentPercentageRepository;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public CurrentPercentageService(HourlyUsageRepositoryPercentage hourlyUsageRepository, 
                                  CurrentPercentageRepo currentPercentageRepository) {
        this.hourlyUsageRepository = hourlyUsageRepository;
        this.currentPercentageRepository = currentPercentageRepository;
    }

    public void calculateAndSavePercentage(LocalDateTime hour) {
        HourlyUsagePercentage usage = hourlyUsageRepository.findById(hour)
                .orElseThrow(() -> new RuntimeException("Usage data not found"));

        double produced = usage.getCommunityProduced();
        double used = usage.getCommunityUsed();
        double grid = usage.getGridUsed();

        double depleted = (produced == 0) ? 100.0 : Math.min(100.0, (used / produced) * 100.0);
        double gridPortion = (produced + grid == 0) ? 0.0 : (grid / (produced + grid)) * 100.0;

        CurrentPercentage percentage = new CurrentPercentage();
        percentage.setHour(hour);
        percentage.setCommunityDepleted(depleted);
        percentage.setGridPortion(gridPortion);

        currentPercentageRepository.save(percentage);
        
        // Send update to SpringRest via RabbitMQ
        PercentageUpdateMessage updateMsg = new PercentageUpdateMessage(hour, depleted, gridPortion);
        rabbitTemplate.convertAndSend("percentage-updates", updateMsg);
        
        System.out.println("Calculated and sent percentage update for hour: " + hour);
    }
}
