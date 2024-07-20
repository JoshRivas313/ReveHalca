package com.ravehalcajpa.service;

import java.util.List;

public interface DAOlist<T> {
	public List<T> getAll();
	public T getById(int id);
}