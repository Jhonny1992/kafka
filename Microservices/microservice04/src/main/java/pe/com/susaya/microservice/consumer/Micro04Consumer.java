package pe.com.susaya.microservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pe.com.susaya.microservice.entity.Person;
import pe.com.susaya.microservice.entity.PersonKafka;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class Micro04Consumer {

    private final List<PersonKafka> listPersonKafka;

    @KafkaListener(topics = "microservice02", groupId = "group-2")
    public void consumeMicro02(String message) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            PersonKafka person = objectMapper.readValue(message, new TypeReference<>() {});
            listPersonKafka.add(person);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<PersonKafka> getConsumedMessages() {
        return listPersonKafka;
    }

    public void clearConsumedMessages() {
        listPersonKafka.clear();
    }
}
