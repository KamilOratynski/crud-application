package pl.oratynski.crudapplication.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import pl.oratynski.crudapplication.model.Person;
import pl.oratynski.crudapplication.repository.PersonRepository;
import pl.oratynski.crudapplication.service.PersonService;
import pl.oratynski.crudapplication.service.impl.PersonServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonControllerTests {

    @Test
    public void insertPerson() {
        Person person = createPerson(1L, "Jan", "Nowak", 15);
        PersonController controller = getPersonController();

        ResponseEntity<Person> personResponseEntity = controller.insertPerson(person);
        Person actual = personResponseEntity.getBody();

        Assertions.assertEquals(201, personResponseEntity.getStatusCodeValue());
        Assertions.assertEquals(person, actual);
    }

    @Test
    public void findAllPeople() {
        PersonController controller = getPersonController();

        ResponseEntity<List<Person>> personResponseEntity = controller.getAll();
        List<Person> actual = controller.getAll().getBody();

        Assertions.assertEquals(200, personResponseEntity.getStatusCodeValue());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(personResponseEntity.getBody(), actual);
    }

    @Test
    public void findById() {
        PersonController controller = getPersonController();

        ResponseEntity<Person> idResponseEntity = controller.getById(1L);
        Person actual = controller.getById(1L).getBody();

        Assertions.assertEquals(200, idResponseEntity.getStatusCodeValue());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1L, actual.getId());
        Assertions.assertEquals("Jan", actual.getFirstName());
    }

    @Test
    public void findById_noExistId_notReturnPerson() {
        PersonController controller = getPersonController();

        ResponseEntity<Person> idResponseEntity = controller.getById(2L);
        Person actual = controller.getById(2L).getBody();

        Assertions.assertEquals(404, idResponseEntity.getStatusCodeValue());
        Assertions.assertNull(actual);
    }

    @Test
    public void findByFirstName() {
        PersonController controller = getPersonController();

        ResponseEntity<List<Person>> nameResponseEntity = controller.getByName("Jan");
        List<Person> actual = controller.getByName("Jan").getBody();

        Assertions.assertEquals(200, nameResponseEntity.getStatusCodeValue());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(nameResponseEntity.getBody(), actual);
    }

    @Test
    public void findByFirstName_noExistName_notReturnPerson() {
        PersonController controller = getPersonController();

        ResponseEntity<List<Person>> nameResponseEntity = controller.getByName("Kamil");

        Assertions.assertEquals(200, nameResponseEntity.getStatusCodeValue());
    }

    @Test
    public void deletePeron() {
        PersonController controller = getPersonController();

        ResponseEntity<Long> personResponseEntity = controller.deletePerson(1L);
        Long actual = personResponseEntity.getBody();

        Assertions.assertEquals(200, personResponseEntity.getStatusCodeValue());
        Assertions.assertEquals(1, actual);
    }

    private PersonController getPersonController() {
        Person person = createPerson(1L, "Jan", "Nowak", 15);
        PersonService personService = new PersonServiceImpl(new InMemoryPersonRepository());
        personService.add(person);

        return new PersonController(personService);
    }

    private Person createPerson(Long id, String name, String surname, int age) {
        return Person.builder().id(id).firstName(name).lastName(surname).age(age).build();
    }

    static class InMemoryPersonRepository implements PersonRepository {
        List<Person> personList = new ArrayList<>();

        @Override
        public List<Person> findByFirstName(String firstName) {
            List<Person> people = new ArrayList<>();
            for (Person person : personList) {
                if (person.getFirstName().equals(firstName)) {
                    people.add(person);
                }
            }
            return people;
        }

        @Override
        public <S extends Person> S save(S entity) {
            personList.add(entity);
            return entity;
        }

        @Override
        public <S extends Person> Iterable<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public Optional<Person> findById(Long aLong) {
            for (Person person : personList) {
                if (person.getId() == aLong) {
                    return Optional.of(person);
                }
            }
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public Iterable<Person> findAll() {
            return personList;
        }

        @Override
        public Iterable<Person> findAllById(Iterable<Long> longs) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Long aLong) {

        }

        @Override
        public void delete(Person entity) {

        }

        @Override
        public void deleteAll(Iterable<? extends Person> entities) {

        }

        @Override
        public void deleteAll() {

        }
    }
}