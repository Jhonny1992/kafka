package pe.com.susaya.microservice02.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pe.com.susaya.microservice02.producer.Micro02Producer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
public class Micro02Consumer {

    @Autowired
    private Micro02Producer micro02Producer;

    @KafkaListener(topics = "microservice05", groupId = "group-2")
    public void consumeMicro05(String message){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(message, new TypeReference<>() {});
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime date=LocalDateTime.now();
            jsonMap.put("last_update", formatter.format(date));
            String modifiedJson = objectMapper.writeValueAsString(jsonMap);
            micro02Producer.publishMicro05(modifiedJson);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
