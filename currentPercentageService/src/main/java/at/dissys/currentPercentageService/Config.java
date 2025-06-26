package at.dissys.currentPercentageService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("currentPercentageServiceConfig")
public class Config {
    @Bean
    public Queue updatesQueue() {
        return new Queue("updates", false);
    }
    
    @Bean
    public Queue percentageUpdatesQueue() {
        return new Queue("percentage-updates", false);
    }
    
    @Bean
    public Queue usageUpdatesQueue() {
        return new Queue("usage-updates", false);
    }
      @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
        converter.setCreateMessageIds(true);
        return converter;
    }
}
//// pushed individually
