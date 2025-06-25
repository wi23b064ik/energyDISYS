package at.dissys.energyUser;

import at.dissys.shared.messages.EnergyMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/*
 * This component simulates community energy users.
 */

@RestController
public class UserSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedRate = 5000)
    public void sendEnergyMessage() {
        EnergyMessage msg = new EnergyMessage();
        msg.setType(EnergyMessage.Type.USER);
        msg.setAssociation(EnergyMessage.Association.COMMUNITY);
        msg.setKwh(generateKWh());
        msg.setDatetime(LocalDateTime.now().toString());

        rabbitTemplate.convertAndSend("energy", msg);
        System.out.println("Sent: " + msg.getKwh() + " kWh used at " + msg.getDatetime());
    }

    private double generateKWh() {
        // Simulate higher consumption in the morning and evening
        int hour = LocalDateTime.now().getHour();
        if ((hour >= 7 && hour <= 10) || (hour >= 17 && hour <= 21)) {
            return 0.002 + Math.random() * 0.003; // peak usage
        } else {
            return 0.0005 + Math.random() * 0.0015; // off-peak
        }
    }
}
