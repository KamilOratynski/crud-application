package pl.oratynski.crudapplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.oratynski.crudapplication.model.Person;
import pl.oratynski.crudapplication.service.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "person/{id}")
    public ResponseEntity<Person> getById(@PathVariable(name = "id") Long id) {
        Optional<Person> person = personService.getById(id);
        return person.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "person/name/{name}")
    public ResponseEntity<List<Person>> getByName(@PathVariable(name = "name") String name) {
        List<Person> people = personService.findByName(name);
        return people.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(people, HttpStatus.OK);
    }

    @GetMapping(value = "person")
    public ResponseEntity<List<Person>> getAll() {
        List<Person> allPeople = personService.getAllPeople();
        return allPeople.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(allPeople, HttpStatus.OK);
    }

    @DeleteMapping(value = "person/{id}")
    public ResponseEntity<Long> deletePerson(@PathVariable(name = "id") Long id) {
        personService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.GONE);
    }

    @PostMapping(value = "person")
    public ResponseEntity<Person> insertPerson(@RequestBody Person person) {
        Person newPerson = personService.add(person);
        return newPerson != null ?
                new ResponseEntity<>(newPerson, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "person")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        Person updatePerson = personService.update(person);
        return updatePerson != null ?
                new ResponseEntity<>(updatePerson, HttpStatus.NOT_MODIFIED) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}