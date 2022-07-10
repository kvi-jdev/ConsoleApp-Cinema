package com.academy.repository;

import java.util.List;

public interface RepositoryOperations<T> {

    boolean create(T t);

    boolean delete(T t);

    boolean update(T oldT, T updT);

    T read(T t);

    List<T> readAll();


}
