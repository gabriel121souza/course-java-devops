package br.com.spacegtech.controllers;


import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

import br.com.spacegtech.entities.Person;
import br.com.spacegtech.exceptions.ResourceNotFoundException;
import br.com.spacegtech.services.PersonServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;


@WebMvcTest
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PersonServices personServices;
    private Person person;

    @BeforeEach
    public void setup(){
        person = new Person("Gabriel","Souza","Valparaiso de Goias","Male", "gabriel121souza@gmail.com");
    }

    @DisplayName("testGivenPersonObject_whenCreatePerson_ReturnSavePerson")
    @Test
    void testGivenPersonObject_whenCreatePerson_ReturnSavePerson() throws Exception {
        given(personServices.create(any(Person.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(person)));

        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.email", is(person.getEmail())));
    }

    @DisplayName("testGivenPersonObject_whenFindAllPersons_ThenReturnPersonsList")
    @Test
    void testGivenPersonObject_whenFindAllPersons_ThenReturnPersonsList() throws Exception {

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(new Person("Daniel","Raflas","Valparaiso de Goias","Male", "danielRaflas@gmail.com"));

        given(personServices.findAll())
                .willReturn(personList);

        ResultActions response = mockMvc.perform(get("/person"));
        response
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(personList.size())));

    }

    @DisplayName("testGivenInvalidPersonId_whenFindByIdPerson_ThenReturnNotFound")
    @Test
    void testGivenInvalidPersonId_whenFindByIdPerson_ThenReturnNotFound() throws Exception {
        long personId = 1L;
        given(personServices.findById(personId))
                .willThrow(ResourceNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        response.andExpect(status().isNotFound())
                .andDo(print());
    }
    @DisplayName("testGivenPersonId_whenFindByIdPerson_ThenReturnPersonObject")
    @Test
    void testGivenPersonId_whenFindByIdPerson_ThenReturnPersonObject() throws Exception {
        long personId = 1L;
        given(personServices.findById(personId))
                .willReturn(person);

        ResultActions response = mockMvc.perform(get("/person/{id}", personId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(person.getLastName())))
                .andExpect(jsonPath("$.email", is(person.getEmail())));
    }

    @DisplayName("testGivenUpdatedPerson_whenUpdatedPerson_ThenReturnUpdatedPersonObject")
    @Test
    void testGivenUpdatedPerson_whenUpdatedPerson_ThenReturnUpdatedPersonObject() throws Exception {
        long personId = 1L;
        Person updatedPerson = new Person("Daniel","Raflas","Valparaiso de Goias","Male", "danielRaflas@gmail.com");
        given(personServices.findById(personId)).willReturn(person);
        given(personServices.update(updatedPerson)).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedPerson)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedPerson.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedPerson.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedPerson.getEmail())));
    }

    @DisplayName("testGivenUnExistentPersonUpdatedPerson_whenUpdatedPerson_ThenReturnNotFound")
    @Test
    void testGivenUnExistentPersonUpdatedPerson_whenUpdatedPerson_ThenReturnNotFound() throws Exception {

        // Given / Arrange
        long personId = 1L;
        given(personServices.findById(personId)).willThrow(ResourceNotFoundException.class);
        given(personServices.update(any(Person.class)))
                .willAnswer((invocation) -> invocation.getArgument(1));

        // When / Act
        Person updatedPerson = new Person("Daniel","Raflas","Valparaiso de Goias","Male", "danielRaflas@gmail.com");

        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedPerson)));


        // Then / Assert
        response.
                andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("testGivenPersonId_whenDeletePerson_ThenReturnNotContent")
    @Test
    void testGivenPersonId_whenDeletePerson_ThenReturnNotContent() throws Exception {
        long personId = 1L;
        willDoNothing().given(personServices).delete(personId);
        ResultActions response = mockMvc.perform(delete("/person/{id}", personId));
        response.
                andExpect(status().isNoContent())
                .andDo(print());
    }
}
