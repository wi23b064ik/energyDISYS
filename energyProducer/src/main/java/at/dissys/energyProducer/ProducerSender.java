package at.dissys.energyProducer;

import at.dissys.shared.messages.EnergyMessage;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/*
* This is the actual component that sends messages to RabbitMQ.
*/

@RestController
public class ProducerSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedRate = 5000)
    public void sendEnergyMessage() {
        EnergyMessage msg = new EnergyMessage();
        msg.setType(EnergyMessage.Type.PRODUCER);
        msg.setAssociation(EnergyMessage.Association.COMMUNITY);
        msg.setKwh(generateKWh());
        msg.setDatetime(LocalDateTime.now().toString());

        rabbitTemplate.convertAndSend("energy", msg);
        System.out.println("Sent: " + msg.getKwh() + " kWh at " + msg.getDatetime());
    }

    private double generateKWh() {
        // Simulate more production during daylight hours
        int hour = LocalDateTime.now().getHour();
        if (hour >= 6 && hour <= 18) {
            return 0.002 + Math.random() * 0.003; // 0.002–0.005
        } else {
            return 0.0001 + Math.random() * 0.0005; // almost nothing at night
        }
    }
}
