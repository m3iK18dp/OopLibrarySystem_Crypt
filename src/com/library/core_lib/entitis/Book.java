package com.library.core_lib.entitis;

public class Book {
	private String iD = "";
	private String name = "";
	private String author = "";
	private int quantity = 0;
	private String kindOfBook = "";

	public Book() {
		iD = "";
		name = "";
		author = "";
		quantity = 0;
		kindOfBook = "";
	}

	public Book(String iD, String name, String author, int quantity, String kindOfBook) {
		this.iD = iD;
		this.name = name;
		this.author = author;
		this.quantity = quantity;
		this.kindOfBook = kindOfBook;
	}

	public String getID() {
		return iD;
	}

	public void setID(String id) {
		this.iD = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getKindOfBook() {
		return kindOfBook;
	}

	public void setKindOfBook(String kindOfBook) {
		this.kindOfBook = kindOfBook;
	}

}
