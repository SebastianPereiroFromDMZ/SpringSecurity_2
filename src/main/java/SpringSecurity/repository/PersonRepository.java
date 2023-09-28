package SpringSecurity.repository;

import SpringSecurity.model.Person;
import SpringSecurity.model.PersonPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, PersonPrimaryKey> {

    List<Person> findByCity(String city);

    List<Person> findByPersonPrimaryKey_AgeLessThan(int age);

    Optional<Person> findByPersonPrimaryKey_Age (int age);

    Optional<Person> findByPersonPrimaryKey_NameAndPersonPrimaryKey_Surname (String name, String surname);
}
