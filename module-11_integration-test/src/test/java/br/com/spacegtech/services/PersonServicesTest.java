package br.com.spacegtech.services;

import br.com.spacegtech.entities.Person;
import br.com.spacegtech.exceptions.ResourceNotFoundException;
import br.com.spacegtech.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {

    @Mock
    private PersonRepository repository;
    @InjectMocks
    private PersonServices service;
    private Person person0;

    @BeforeEach
    public void setup(){
        person0 = new Person("Gabriel","Souza","Valparaiso de Goias","Male", "gabriel121souza@gmail.com");
    }
    @DisplayName("estGivenPersonObject_WhenSavePerson_thenReturnPersonObject")
    @Test
    void testGivenPersonObject_WhenSavePerson_thenReturnPersonObject(){
        //Given / Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        given(repository.save(person0)).willReturn(person0);

        //When or ACT
        Person savedPerson = service.create(person0);
        //then or assert
        assertNotNull(savedPerson);
        assertEquals("Gabriel",savedPerson.getFirstName());
    }

    @DisplayName("esta GivenTestGivenExistEmail_WhenSavePerson_ThenThrowsExceptions")
    @Test
    void testGivenExistEmail_WhenSavePerson_thenThrowsExceptions(){
        //Given / Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.of(person0));

        //When or ACT
        assertThrows(ResourceNotFoundException.class, () -> {
            Person savedPerson = service.create(person0);
        });
        //then or assert
        verify(repository, never()).save(any(Person.class));
    }

    @DisplayName("esta GivenTestGivenExistEmail_WhenSavePerson_ThenThrowsExceptions")
    @Test
    void testGivenPersonsList_WhenFindAllPeople_thenReturnPeopleList(){
        //Given / Arrange
        Person person1 = new Person("Daniel","Raflas","Valparaiso de Goias","Male", "danielRaflas@gmail.com");
        given(repository.findAll()).willReturn(List.of(person0, person1));

        //When or ACT
        List<Person> personList = service.findAll();
        //then or assert
        assertNotNull(personList);
        assertEquals(2, personList.size());
    }
    @DisplayName("testGivenEmptyPersonsList_WhenFindAllPeople_thenReturnEmptyPeopleList")
    @Test
    void testGivenEmptyPersonsList_WhenFindAllPeople_thenReturnEmptyPeopleList(){
        //Given / Arrange
        given(repository.findAll()).willReturn(Collections.emptyList());
        //When or ACT
        List<Person> personList = service.findAll();
        //then or assert
        assertNotNull(personList.isEmpty());
        assertEquals(0, personList.size());
    }

    @DisplayName("testGivenPersonObject_WhenFindByIdPerson_thenReturnPersonObject")
    @Test
    void testGivenPersonObject_WhenFindByIdPerson_thenReturnPersonObject(){
        //Given / Arrange
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));

        //When or ACT
        Person savedPerson = service.findById(1L);
        //then or assert
        assertNotNull(savedPerson);
        assertEquals("Gabriel",savedPerson.getFirstName());
    }

    @DisplayName("testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatePersonObject")
    @Test
    void testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatePersonObject(){
        person0.setId(1L);
        //Given / Arrange
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));
        person0.setEmail("biel@gmail.com");
        person0.setFirstName("Biel");
        given(repository.save(person0)).willReturn(person0);

        //When or ACT
        Person updatedPerson = service.update(person0);
        //then or assert
        assertNotNull(updatedPerson);
        assertEquals("Biel",updatedPerson.getFirstName());
        assertEquals("biel@gmail.com",updatedPerson.getEmail());

    }

    @DisplayName("testGivenPersonObject_WhenDeletePerson_thenReturnDeletePersonObject")
    @Test
    void testGivenPersonObject_WhenDeletePerson_thenReturnDeletePersonObject(){
        person0.setId(1L);
        //Given / Arrange
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));
        willDoNothing().given(repository).delete(person0);

        //When or ACT
        service.delete(person0.getId());
        //then or assert
        verify(repository, times(1)).delete(person0);

    }


}
