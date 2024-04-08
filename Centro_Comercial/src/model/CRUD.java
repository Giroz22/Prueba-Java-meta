package model;

import java.util.List;

public interface CRUD<T> {
    public List<T> findAll();
    public T findById(int id);
    public T save(T obj);
    public T update(T obj);
    public boolean delete(int id);
}
