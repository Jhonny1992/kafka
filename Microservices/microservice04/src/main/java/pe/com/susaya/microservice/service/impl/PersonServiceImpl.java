package pe.com.susaya.microservice.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.susaya.microservice.entity.Person;
import pe.com.susaya.microservice.repository.PersonRepository;
import pe.com.susaya.microservice.service.PersonService;

import java.util.List;
@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public List<Person> listPerson() {
        return personRepository.findAll();
    }
}
