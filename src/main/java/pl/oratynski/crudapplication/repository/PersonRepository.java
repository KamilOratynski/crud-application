package pl.oratynski.crudapplication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.oratynski.crudapplication.entity.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}