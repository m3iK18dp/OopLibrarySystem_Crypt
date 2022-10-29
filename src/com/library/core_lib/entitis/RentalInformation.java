package com.library.core_lib.entitis;

import java.sql.Date;

public class RentalInformation {
	private String iD;
	private String readerID;
	private String bookID;
	private String libStaffID;
	private Date bookBorrowDate;
	private Date bookReturnDate;
	private String note;

	public RentalInformation() {
		iD = "";
		readerID = "";
		bookID = "";
		libStaffID = "";
		bookBorrowDate = new Date(System.currentTimeMillis());
		bookReturnDate = new Date(System.currentTimeMillis());
		note = "";
	}

	public RentalInformation(String iD, String readerID, String bookID, String libStaffID, Date bookBorrowDate,
			Date bookReturnDate, String note) {
		super();
		this.iD = iD;
		this.readerID = readerID;
		this.bookID = bookID;
		this.libStaffID = libStaffID;
		this.bookBorrowDate = bookBorrowDate;
		this.bookReturnDate = bookReturnDate;
		this.note = note;
	}

	public String getID() {
		return iD;
	}

	public void setID(String iD) {
		this.iD = iD;
	}

	public String getReaderID() {
		return readerID;
	}

	public void setReaderID(String readerID) {
		this.readerID = readerID;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getLibStaffID() {
		return libStaffID;
	}

	public void setLibStaffID(String libStaffID) {
		this.libStaffID = libStaffID;
	}

	public Date getBookBorrowDate() {
		return bookBorrowDate;
	}

	public void setBookBorrowDate(Date bookBorrowDate) {
		this.bookBorrowDate = bookBorrowDate;
	}

	public Date getBookReturnDate() {
		return bookReturnDate;
	}

	public void setBookReturnDate(Date bookReturnDate) {
		this.bookReturnDate = bookReturnDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
