package pe.com.susaya.microservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.susaya.microservice.entity.PersonKafka;
import pe.com.susaya.microservice.service.PersonKafkaService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path ="v1/service04")
public class PersonController {

    private final PersonKafkaService personKafkaService;

    @GetMapping
    public ResponseEntity<Page<PersonKafka>> getLastConsumedMessage(Pageable pageable) {
        Page<PersonKafka> page = personKafkaService.listPersonKafka(pageable);
        return ResponseEntity.ok(page);
    }
}
