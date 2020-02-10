package pl.oratynski.crudapplication.service;

import pl.oratynski.crudapplication.model.Person;

import java.util.List;

public interface PersonService extends CrudService<Person> {

    List<Person> getAllPeople();

    List<Person> findByName(String name);
}
