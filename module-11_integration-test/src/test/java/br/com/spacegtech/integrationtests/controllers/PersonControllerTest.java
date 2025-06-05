package br.com.spacegtech.integrationtests.controllers;

import br.com.spacegtech.config.TestConfigs;
import br.com.spacegtech.entities.Person;
import br.com.spacegtech.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PersonControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Person person;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        specification = new RequestSpecBuilder()
                .setBasePath("/person")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        person = new Person(
                "Gabriel",
                "Rodrigues",
                "Valparaiso de Goiás",
                "Male",
                "gabrielsrodriguesdev@gmail.com"
                );
    }

    @DisplayName("integrationTestGivenPersonObject_when_CreateOnePerson_ShouldReturnAPersonObject")
    @Test
    @Order(1)
    void integrationTestGivenPersonObject_when_CreateOnePerson_ShouldReturnAPersonObject() throws JsonProcessingException {
        var content =
                given().spec(specification)
                    .contentType(TestConfigs.CONTET_TYPE_JSON)
                    .body(person)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();
        Person createdPerson = objectMapper.readValue(content, Person.class);
        assertNotNull(createdPerson);
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getEmail());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);
        assertEquals("Gabriel",createdPerson.getFirstName());
        assertEquals("Rodrigues",createdPerson.getLastName());
        assertEquals("Valparaiso de Goiás",createdPerson.getAddress());
        assertEquals("gabrielsrodriguesdev@gmail.com", createdPerson.getEmail());
        assertEquals("Male",createdPerson.getGender());
        person = createdPerson;
    }

    @DisplayName("integrationTestGivenPersonObject_when_UpdatedOnePerson_ShouldReturnAPersonObject")
    @Test
    @Order(2)
    void integrationTestGivenPersonObject_when_UpdatedOnePerson_ShouldReturnAPersonObject() throws JsonProcessingException {
        person.setFirstName("Biel");
        person.setEmail("gabriel121souza@gmail.com");
        var content =
                given().spec(specification)
                        .contentType(TestConfigs.CONTET_TYPE_JSON)
                        .body(person)
                        .when()
                        .put()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();
        Person updatedPerson = objectMapper.readValue(content, Person.class);
        assertNotNull(updatedPerson);
        assertNotNull(updatedPerson.getId());
        assertNotNull(updatedPerson.getFirstName());
        assertNotNull(updatedPerson.getLastName());
        assertNotNull(updatedPerson.getAddress());
        assertNotNull(updatedPerson.getEmail());
        assertNotNull(updatedPerson.getGender());

        assertTrue(updatedPerson.getId() > 0);
        assertEquals("Biel",updatedPerson.getFirstName());
        assertEquals("Rodrigues",updatedPerson.getLastName());
        assertEquals("Valparaiso de Goiás",updatedPerson.getAddress());
        assertEquals("gabriel121souza@gmail.com", updatedPerson.getEmail());
        assertEquals("Male",updatedPerson.getGender());
        person = updatedPerson;
    }
    @DisplayName("integrationTestGivenPersonObject_when_findByIdPerson_ShouldReturnAPersonObject")
    @Test
    @Order(3)
    void integrationTestGivenPersonObject_when_findByIdPerson_ShouldReturnAPersonObject() throws JsonProcessingException {
          var content =
                given().spec(specification)
                        .pathParams("id", person.getId())
                        .when()
                            .get("{id}")
                        .then()
                            .statusCode(200)
                        .extract()
                            .body()
                                .asString();
        Person foundPerson = objectMapper.readValue(content, Person.class);
        assertNotNull(foundPerson);
        assertNotNull(foundPerson.getId());
        assertNotNull(foundPerson.getFirstName());
        assertNotNull(foundPerson.getLastName());
        assertNotNull(foundPerson.getAddress());
        assertNotNull(foundPerson.getEmail());
        assertNotNull(foundPerson.getGender());

        assertTrue(foundPerson.getId() > 0);
        assertEquals("Biel",foundPerson.getFirstName());
        assertEquals("Rodrigues",foundPerson.getLastName());
        assertEquals("Valparaiso de Goiás",foundPerson.getAddress());
        assertEquals("gabriel121souza@gmail.com", foundPerson.getEmail());
        assertEquals("Male",foundPerson.getGender());
    }

    @DisplayName("integrationTestGivenPersonObject_when_findByAllPerson_ShouldReturnAPersonsList")
    @Test
    @Order(4)
    void integrationTestGivenPersonObject_when_findByAllPerson_ShouldReturnAPersonsList() throws JsonProcessingException {
        Person anotherPerson = new Person("Daniel",
        "Raflas",
        "Ceu Azul, Valparaiso Goiás",
        "Male",
        "danielRaflas@gmail.com"
        );
        given().spec(specification)
                .contentType(TestConfigs.CONTET_TYPE_JSON)
                .body(anotherPerson)
                .when()
                .post()
                .then();

        var content =
                given().spec(specification)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();
        List<Person> people = Arrays.asList(objectMapper.readValue(content, Person[].class));
        Person foundPersonOne = people.get(0);
        assertNotNull(foundPersonOne);
        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getEmail());
        assertNotNull(foundPersonOne.getGender());
        assertTrue(foundPersonOne.getId() > 0);
        assertEquals("Biel",foundPersonOne.getFirstName());
        assertEquals("Rodrigues",foundPersonOne.getLastName());
        assertEquals("Valparaiso de Goiás",foundPersonOne.getAddress());
        assertEquals("gabriel121souza@gmail.com", foundPersonOne.getEmail());
        assertEquals("Male",foundPersonOne.getGender());

        Person foundPersonTwo = people.get(1);
        assertNotNull(foundPersonTwo);
        assertNotNull(foundPersonTwo.getId());
        assertNotNull(foundPersonTwo.getFirstName());
        assertNotNull(foundPersonTwo.getLastName());
        assertNotNull(foundPersonTwo.getAddress());
        assertNotNull(foundPersonTwo.getEmail());
        assertNotNull(foundPersonTwo.getGender());
        assertTrue(foundPersonTwo.getId() > 0);
        assertEquals("Daniel",foundPersonTwo.getFirstName());
        assertEquals("Raflas",foundPersonTwo.getLastName());
        assertEquals("Ceu Azul, Valparaiso Goiás",foundPersonTwo.getAddress());
        assertEquals("danielRaflas@gmail.com", foundPersonTwo.getEmail());
        assertEquals("Male",foundPersonTwo.getGender());

    }

    @DisplayName("integrationTestGivenPersonObject_when_DeletePerson_ShouldReturnNoContent")
    @Test
    @Order(5)
    void integrationTestGivenPersonObject_when_DeletePerson_ShouldReturnNoContent() throws JsonProcessingException {
                given().spec(specification)
                        .pathParams("id", person.getId())
                        .when()
                        .delete("{id}")
                        .then()
                        .statusCode(204)
                        .extract()
                        .body()
                        .asString();
    }

}

