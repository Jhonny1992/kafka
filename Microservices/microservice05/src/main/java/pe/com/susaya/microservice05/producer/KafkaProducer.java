package pe.com.susaya.microservice05.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {


    private final String topics;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(@Value("${kafka.topic}") String topics, KafkaTemplate<String, String> kafkaTemplate){
        this.topics = topics;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMicro05(String publishMic){
        kafkaTemplate.send(topics, publishMic);
    }
}
