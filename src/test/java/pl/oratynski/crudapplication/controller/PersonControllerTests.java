package pl.oratynski.crudapplication.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.oratynski.crudapplication.model.Person;
import pl.oratynski.crudapplication.service.PersonService;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonControllerTests {

    @Test
    public void givenExistingId_whenGetById_thenReturnPerson() {
        Person person = createPerson(1L, "Kamil", "Orat", 15);
        PersonService personService = mock(PersonService.class);
        when(personService.getById(1L)).thenReturn(Optional.of(person));

        PersonController controller = new PersonController(personService);

        Person actual = controller.getById(1L).getBody();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1L, actual.getId());
        Assertions.assertEquals("Kamil", actual.getFirstName());
    }

    @Test
    public void givenNonExistingId_whenGetById_thenNotReturnPerson() {
        Person person = createPerson(1L, "Kamil", "Orat", 15);
        PersonService personService = mock(PersonService.class);
        when(personService.getById(1L)).thenReturn(Optional.of(person));

        PersonController controller = new PersonController(personService);

        Person actual = controller.getById(2L).getBody();

        Assertions.assertNull(actual);
    }

    @Test
    public void givenNewPerson_whenAdd_thenReturnNewPerson() {
        Person person = createPerson(1L, "Kamil", "Orat", 15);
        PersonService personService = mock(PersonService.class);
        when(personService.add(person)).thenReturn(person);

        PersonController controller = new PersonController(personService);

        ResponseEntity<Person> personResponseEntity = controller.insertPerson(person);
        Person actual = personResponseEntity.getBody();

        Assertions.assertEquals(201, personResponseEntity.getStatusCodeValue());
        Assertions.assertEquals(person, actual);
        Assertions.assertNotNull(actual);
    }

    private Person createPerson(Long id, String name, String surname, int age) {
        return Person.builder().id(id).firstName(name).lastName(surname).age(age).build();
    }
}
