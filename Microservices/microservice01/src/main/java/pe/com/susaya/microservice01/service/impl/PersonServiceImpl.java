package pe.com.susaya.microservice01.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.com.susaya.microservice01.entity.Person;
import pe.com.susaya.microservice01.repository.PersonRepository;
import pe.com.susaya.microservice01.service.PersonService;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public void saveAll(Person person) {
        Optional<Person> existingPerson = personRepository.findByEmail(person.getEmail());
        if (existingPerson.isPresent()) {
            Person existing = existingPerson.get();
            personRepository.save(existing);
        } else {
            personRepository.save(person);
        }
    }
}
