package com.ravehalcajpa.service;

import java.util.List;

public interface DAOedit<T> {
	public List<T> getAll();
	public T getById(Long id);
	public T update(T entity);
}

