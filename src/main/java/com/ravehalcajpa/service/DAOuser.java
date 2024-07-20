package com.ravehalcajpa.service;

import java.util.List;

public interface DAOuser<T> {
	public List<T> getAll();
	public T getById(int id);
	public T create(T entity);
	public T update(T entity);
	public boolean delete(int id);
}