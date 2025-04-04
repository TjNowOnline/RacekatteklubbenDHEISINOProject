package com.example.racekatteklubbendheisino.infrastructure;

import java.util.List;

public interface CRUDRepository <T, ID> {
    void save(T entity);
    void delete(T entity);
    void update(T entity);
    T findByID(ID id);
    List<T> findAll();
}
