package at.dissys.springrest.listener;

import at.dissys.springrest.entity.CurrentPercentage;
import at.dissys.springrest.message.PercentageUpdateMessage;
import at.dissys.springrest.repository.CurrentPercentageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PercentageUpdateListener {

    @Autowired
    private CurrentPercentageRepository repository;

    @RabbitListener(queues = "percentage-updates")
    public void receivePercentageUpdate(PercentageUpdateMessage msg) {
        CurrentPercentage percentage = new CurrentPercentage();
        percentage.setHour(msg.getHour());
        percentage.setCommunityDepleted(msg.getCommunityDepleted());
        percentage.setGridPortion(msg.getGridPortion());

        repository.save(percentage);
        System.out.println("SpringRest: Updated percentage data for hour: " + msg.getHour());
    }
}
