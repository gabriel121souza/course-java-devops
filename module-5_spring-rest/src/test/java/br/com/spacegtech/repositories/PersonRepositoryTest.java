package br.com.spacegtech.repositories;

import br.com.spacegtech.entities.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;
    private Person person0;

    @BeforeEach
    public void setup(){
        person0 = new Person("Gabriel","Souza","Valparaiso de Goias","Male", "gabriel121souza@gmail.com");
    }
    @Test
    @DisplayName("test Given Person Object when Save then Return Saved Person")
    void testGivenPersonObject_whenSave_thenReturnSavedPerson(){
    Person savePerson = personRepository.save(person0);

    assertNotNull(savePerson);
    assertTrue(savePerson.getId()> 0);
    }
    @Test
    @DisplayName("test Given Person Object when find all then Return Person List")
    void testGivenPersonObject_whenFindAll_thenReturnPersonList(){
        Person person1 = new Person("Daniel","Raflas","Valparaiso de Goias","Male", "danielRaflas@gmail.com");

        personRepository.save(person0);
        personRepository.save(person1);
        List<Person> personList = personRepository.findAll();
        assertNotNull(personList);
        assertEquals(2, personList.size());
    }
    @Test
    @DisplayName("test Given Person Object when find by Id then Return Person ")
    void testGivenPersonObject_whenFindById_thenReturnPerson(){
        personRepository.save(person0);
        Person person = personRepository.findById(person0.getId()).get();
        assertNotNull(person);
        assertEquals(person0.getId(), person.getId());
    }


    @Test
    @DisplayName("test Given Person Object when find by Email then Return Person ")
    void testGivenPersonObject_whenFindByEmail_thenReturnPerson(){
        personRepository.save(person0);
        Person person = personRepository.findByEmail(person0.getEmail()).get();
        assertNotNull(person);
        assertEquals(person0.getId(), person.getId());
    }

    @Test
    @DisplayName("test Given Person Object when updated person then Return Updated Person ")
    void testGivenPersonObject_whenUpdatedPerson_thenUpdatedReturnPerson(){
        personRepository.save(person0);
        Person savedPerson = personRepository.findById(person0.getId()).get();
        savedPerson.setFirstName("Daniel");
        savedPerson.setEmail("danielRaflas@gmail.com");

        Person updatedPerson = personRepository.save(savedPerson);
        assertEquals("Daniel", updatedPerson.getFirstName());
        assertEquals(savedPerson.getEmail(), updatedPerson.getEmail());
    }

    @Test
    @DisplayName("test Given Person Object when delete Id then remove Person ")
    void testGivenPersonObject_whenDelete_thenRemovePerson(){
        personRepository.save(person0);
        personRepository.deleteById(person0.getId());
        Optional<Person> personOptional = personRepository.findById(person0.getId());
        assertTrue(personOptional.isEmpty());

    }

    @Test
    @DisplayName("test Given Person Object when firstName and LastName when findJPQL Id then Return Person ")
    void testGivenPersonObject_whenFindByfirstNameAnLastName_thenReturnPerson(){
        personRepository.save(person0);
        Person person = personRepository.findByJPQL("Gabriel", "Souza");
        assertNotNull(person);
        assertEquals("Gabriel", person.getFirstName());
        assertEquals("Souza", person.getLastName());
    }


    @Test
    @DisplayName("test Given Person Object when firstName and LastName when findJPQLNameParemetersId then Return Person ")
    void testGivenFirstNamedAndLastName_whenFindJPQLNameParemeters_thenReturnPerson(){
        personRepository.save(person0);
        Person person = personRepository.findByJPQLNameParemeters("Gabriel", "Souza");
        assertNotNull(person);
        assertEquals("Gabriel", person.getFirstName());
        assertEquals("Souza", person.getLastName());
    }

    @Test
    @DisplayName("test Given Person Object when firstName and LastName when findNativeQuery then Return Person ")
    void testGivenFirstNamedAndLastName_whenFindNativeQuery_thenReturnPerson(){
        personRepository.save(person0);
        Person person = personRepository.findByNativeQuery("Gabriel", "Souza");
        assertNotNull(person);
        assertEquals("Gabriel", person.getFirstName());
        assertEquals("Souza", person.getLastName());
    }
    @Test
    @DisplayName("test Given Person Object when firstName and LastName when findNativeSQLWithNameParameters then Return Person ")
    void testGivenFirstNamedAndLastName_whenFindNativeSQLWithNameParameters_thenReturnPerson(){

        personRepository.save(person0);
        Person person = personRepository.findByNativeSQLWithNameParameters("Gabriel", "Souza");
        assertNotNull(person);
        assertEquals("Gabriel", person.getFirstName());
        assertEquals("Souza", person.getLastName());
    }
}
