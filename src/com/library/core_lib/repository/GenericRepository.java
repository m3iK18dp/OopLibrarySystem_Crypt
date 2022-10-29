package com.library.core_lib.repository;

import java.sql.Connection;
import java.util.List;

public interface GenericRepository<E> {
	// 1. getAll
	List<E> get(String s);

	/* 2. add */
	boolean add(E e);

	/* 3. update */
	boolean update(E e);

	/* 4. delete */
	boolean delete(String s);

	Connection getConn();
}
