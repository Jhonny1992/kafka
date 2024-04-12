package pe.com.susaya.microservice01.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pe.com.susaya.microservice01.entity.Person;
import pe.com.susaya.microservice01.service.PersonService;

import java.util.Map;

@Service
public class Micro01Consumer {

    @Autowired
    private PersonService personService;

    @KafkaListener(topics = "microservice05", groupId = "group-1")
    public void consumeMicro05(String message){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Person person = objectMapper.readValue(message, new TypeReference<>() {});
            personService.saveAll(person);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
