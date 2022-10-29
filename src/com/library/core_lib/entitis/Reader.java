package com.library.core_lib.entitis;

import java.sql.Date;

public class Reader extends Person {
	private String kindOfReader;
	private Date startDate;
	private Date endDate;

	public Reader() {
		kindOfReader = "";
		startDate = new Date(System.currentTimeMillis());
		endDate = new Date(System.currentTimeMillis());
	}

	public Reader(String id, String fullName, String gender, Date dob, String address, String license,
			String phoneNumber, String gmail, String kindOfReader, Date startDate, Date endDate) {
		super(id, fullName, gender, dob, address, license, phoneNumber, gmail);
		this.kindOfReader = kindOfReader;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getKindOfReader() {
		return kindOfReader;
	}

	public void setKindOfReader(String kindOfReader) {
		this.kindOfReader = kindOfReader;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
