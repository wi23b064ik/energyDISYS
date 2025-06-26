package at.dissys.currentPercentageService;

import at.dissys.shared.messages.HourlyUsageUpdateMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsageUpdateListener {

    @Autowired
    private HourlyUsageRepositoryPercentage repository;

    @RabbitListener(queues = "usage-updates")
    public void receiveUsageUpdate(HourlyUsageUpdateMessage msg) {
        HourlyUsagePercentage usage = repository.findById(msg.getHour()).orElseGet(() -> {
            HourlyUsagePercentage newUsage = new HourlyUsagePercentage();
            newUsage.setHour(msg.getHour());
            return newUsage;
        });

        usage.setCommunityProduced(msg.getCommunityProduced());
        usage.setCommunityUsed(msg.getCommunityUsed());
        usage.setGridUsed(msg.getGridUsed());

        repository.save(usage);
        System.out.println("CurrentPercentageService: Updated usage data for hour: " + msg.getHour());
    }
}
