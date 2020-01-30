package pl.oratynski.crudapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.oratynski.crudapplication.entity.Person;
import pl.oratynski.crudapplication.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<Person> getAllPeople() {
        return (List<Person>) personRepository.findAll();
    }

    public List<Person> findByName(String name) {
        return personRepository.findByFirstName(name);
    }

    public Optional<Person> getById(Long id) {
        return personRepository.findById(id);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public boolean addPerson(Person person) {
        personRepository.save(person);
        return true;
    }

    public boolean updatePerson(Person person) {
        personRepository.save(person);
        return true;
    }

}
