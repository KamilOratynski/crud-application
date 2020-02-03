package pl.oratynski.crudapplication.service;

import java.util.Optional;

public interface CrudService<T> {

    T add(T t);

    T update(T t);

    Long delete(Long id);

    Optional<T> getById(Long id);

}
