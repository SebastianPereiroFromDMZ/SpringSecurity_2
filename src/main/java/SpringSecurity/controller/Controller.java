package SpringSecurity.controller;

import SpringSecurity.model.Person;
import SpringSecurity.repository.PersonRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController

public class Controller {

    @PersistenceContext
    private EntityManager entityManager;

    private final PersonRepository personRepository;

    public Controller(PersonRepository personRepository) {
        this.personRepository = personRepository;

    }

    //localhost:8080/persons/by-city?city=Moscow
    @GetMapping("/persons/by-city")
    public List<Person> getPersonsByCity(@RequestParam String city) {
        return personRepository.findByCity(city);
    }

    //for one Person  localhost:8080/persons/by-age:12
    @GetMapping("/persons/by-age:{age}")
    @Secured({"ROLE_READ"})
    public Optional<Person> getPersonByAgeWithPathVariable(@PathVariable("age") int age) {
        return personRepository.findByPersonPrimaryKey_Age(age);
    }

    //for many Persons   localhost:8080/persons/by-age?age=26
    @GetMapping("/persons/by-age")
    @RolesAllowed({"WRITE"})
    public List<Person> getPersonByAgeWithRequestParam(@RequestParam int age) {
        return personRepository.findByPersonPrimaryKey_AgeLessThan(age);
    }

        //for one Person   localhost:8080/persons/by-name-and-surname?name=Nikola&surname=Holopenya
    @GetMapping("/persons/by-name-and-surname")
    @PreAuthorize("hasAnyRole('WRITE') or hasAnyRole('DELETE')")
    public Optional<Person> getPersonByNameAndSurname(@RequestParam String name, String surname) {
        return personRepository.findByPersonPrimaryKey_NameAndPersonPrimaryKey_Surname(name, surname);
    }

    //localhost:8080/persons/by-name-and-surname?name=Nikola
    @GetMapping("/persons/by-name")
    @PostAuthorize("returnObject.name == authentication.principal.name")
    public Optional<Person> findByPersonPrimaryKey_Name (@RequestParam String name){
        return personRepository.findByPersonPrimaryKey_Name(name);
    }
}
