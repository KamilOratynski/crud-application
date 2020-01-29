package pl.oratynski.crudapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.oratynski.crudapplication.entity.Person;
import pl.oratynski.crudapplication.service.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    PersonService personService;

    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Optional<Person> getAllPeople(@PathVariable Long id) {
        return personService.getById(id);
    }

    @RequestMapping(value = "/personByName/{name}", method = RequestMethod.GET)
    public List<Person> getPersonByName(@PathVariable String name) {
        return personService.findByName(name);
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public List<Person> getAll() {
        return personService.getAllPersons();
    }

    @RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
    public HttpStatus deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return HttpStatus.NO_CONTENT;
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public HttpStatus insertPerson(@RequestBody Person person) {
        return personService.addPerson(person) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value = "/person", method = RequestMethod.PUT)
    public HttpStatus updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person) ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
    }
}