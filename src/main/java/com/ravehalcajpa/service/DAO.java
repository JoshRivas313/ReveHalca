package com.ravehalcajpa.service;

import java.util.List;

public interface DAO<T> {
	public List<T> getAll();
	public T getById(Long id);
	public T create(T entity);
	public T update(T entity);
	public boolean delete(long id);
}