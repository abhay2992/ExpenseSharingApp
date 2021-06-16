package com.splitwise.repositories;

import com.splitwise.models.User;

import java.util.List;
import java.util.Optional;

public interface IRepository<E, Id> {
    // create
    public void create(E obj);

    // read
    public Optional<E> findById(Id id);
    public List<E> findAll();

    // update
    public void save(E obj);

    // delete
    public void delete(Id id);
}
