package pe.com.susaya.microservice.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonKafka implements Serializable {

    private Long idPerson;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String age;
    private String firstName2;
    private String lastName2;
    private String email;
    private Integer random;
    private Double randomFloat;
    private String bool;
    private String dates;
    private String regEx;
    private String enume;
    private String elt;
    private String last_update;
}
