package pe.com.susaya.microservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.susaya.microservice.entity.PersonKafka;

import java.util.List;

public interface PersonKafkaService {

    Page<PersonKafka> listPersonKafka(Pageable pageable);
}
