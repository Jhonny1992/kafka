package pe.com.susaya.microservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.com.susaya.microservice.consumer.Micro04Consumer;
import pe.com.susaya.microservice.entity.PersonKafka;
import pe.com.susaya.microservice.service.PersonKafkaService;

import java.util.List;

@AllArgsConstructor
@Service
public class PersonKafkaServiceImpl implements PersonKafkaService {

    private Micro04Consumer micro04Consumer;

    @Override
    public Page<PersonKafka> listPersonKafka(Pageable pageable) {
        List<PersonKafka> lastSetOfMessages = micro04Consumer.getConsumedMessages();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), lastSetOfMessages.size());
        return new PageImpl<>(lastSetOfMessages.subList(start, end), pageable, lastSetOfMessages.size());
    }
}
