package pl.oratynski.crudapplication.service.impl;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import pl.oratynski.crudapplication.model.Person;
import pl.oratynski.crudapplication.repository.PersonRepository;
import pl.oratynski.crudapplication.service.PersonService;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getAllPeople() {
        return Lists.newArrayList(personRepository.findAll());
    }

    @Override
    public List<Person> findByName(String name) {
        return personRepository.findByFirstName(name);
    }

    @Override
    public Optional<Person> getById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Long delete(Long id) {
        personRepository.deleteById(id);
        return id;
    }

    @Override
    public Person add(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person update(Person person) {
        return personRepository.save(person);
    }
}
