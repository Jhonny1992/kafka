package pe.com.susaya.microservice05.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    private final String topics;

    public KafkaTopicConfig(@Value("${kafka.topic}") String topics){
        this.topics = topics;
    }

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(topics)
                .build();
    }
}
