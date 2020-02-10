package pl.oratynski.crudapplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.oratynski.crudapplication.model.Person;
import pl.oratynski.crudapplication.service.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Person> insertPerson(@RequestBody Person person) {
        Person newPerson = personService.add(person);
        return newPerson != null ?
                new ResponseEntity<>(newPerson, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAll() {
        List<Person> allPeople = personService.getAllPeople();
        return new ResponseEntity<>(allPeople, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Person> getById(@PathVariable(name = "id") Long id) {
        Optional<Person> person = personService.getById(id);
        return person.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<List<Person>> getByName(@PathVariable(name = "name") String name) {
        List<Person> people = personService.findByName(name);
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
        Person updatePerson = personService.update(person);
        return updatePerson != null ?
                new ResponseEntity<>(updatePerson, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> deletePerson(@PathVariable(name = "id") Long id) {
        personService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}