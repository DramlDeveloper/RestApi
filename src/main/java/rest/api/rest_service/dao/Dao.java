package rest.api.rest_service.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, K> {

    T save(T t);

    Optional<T> findById(K id);

    List<T> findAll();

    boolean update(T t);

    boolean deleteById(K id);



}