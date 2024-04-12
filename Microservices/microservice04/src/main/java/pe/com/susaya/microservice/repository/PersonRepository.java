package pe.com.susaya.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.susaya.microservice.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
