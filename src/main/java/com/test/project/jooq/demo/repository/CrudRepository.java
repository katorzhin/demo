package com.test.project.jooq.demo.repository;

import org.jooq.Condition;

import java.util.List;
public interface CrudRepository<T> {

    Integer SUCCESS_CODE = 1;

    T insert(T t);

    T update(T t);

    T find(Long id);

    List<T> findAll(Condition condition);

    Boolean delete(Long id);
}