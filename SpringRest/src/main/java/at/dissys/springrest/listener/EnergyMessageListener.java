package at.dissys.springrest.listener;

import at.dissys.springrest.entity.HourlyUsage;
import at.dissys.springrest.message.EnergyMessage;
import at.dissys.springrest.repository.HourlyUsageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EnergyMessageListener {

    @Autowired
    private HourlyUsageRepository repository;

    @RabbitListener(queues = "energy")
    public void receiveEnergyMessage(EnergyMessage msg) {
        LocalDateTime hour = LocalDateTime.parse(msg.getDatetime()).withMinute(0).withSecond(0).withNano(0);
        HourlyUsage usage = repository.findById(hour).orElseGet(() -> {
            HourlyUsage newUsage = new HourlyUsage();
            newUsage.setHour(hour);
            return newUsage;
        });        switch (msg.getType()) {
            case PRODUCER:
                usage.setCommunityProduced(usage.getCommunityProduced() + msg.getKwh());
                System.out.println("PRODUCER: Added " + msg.getKwh() + " kWh. Total produced: " + usage.getCommunityProduced());
                break;            case USER:
                double currentProduced = usage.getCommunityProduced();
                double currentUsed = usage.getCommunityUsed();
                double consumption = msg.getKwh();
                
                // Calculate how much community energy is available
                double available = Math.max(0, currentProduced - currentUsed);
                
                System.out.println("USER: Consuming " + consumption + " kWh. Produced: " + currentProduced + 
                                 ", Already used: " + currentUsed + ", Available: " + available);
                
                if (available >= consumption) {
                    // All consumption can be met by community production
                    double newCommunityUsed = currentUsed + consumption;
                    // Safety check: never exceed production
                    if (newCommunityUsed <= currentProduced) {
                        usage.setCommunityUsed(newCommunityUsed);
                        System.out.println("USER: All from community. CommunityUsed now: " + usage.getCommunityUsed());
                    } else {
                        // This shouldn't happen, but safety fallback
                        usage.setCommunityUsed(currentProduced);
                        usage.setGridUsed(usage.getGridUsed() + (consumption - (currentProduced - currentUsed)));
                        System.out.println("USER: Safety fallback triggered");
                    }
                } else if (available > 0) {
                    // Use all remaining community energy and get the rest from grid
                    usage.setCommunityUsed(currentProduced); // Use up all available community energy
                    double gridNeed = consumption - available;
                    usage.setGridUsed(usage.getGridUsed() + gridNeed);
                    System.out.println("USER: Mixed. CommunityUsed: " + usage.getCommunityUsed() + 
                                     ", Additional GridUsed: " + gridNeed + ", Total GridUsed: " + usage.getGridUsed());
                } else {
                    // No community energy available, all from grid
                    usage.setGridUsed(usage.getGridUsed() + consumption);
                    System.out.println("USER: All from grid. GridUsed now: " + usage.getGridUsed());
                }
                break;
        }

        repository.save(usage);
        System.out.println("SpringRest: Updated usage data for hour: " + hour);
    }
}
