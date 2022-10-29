package com.library.core_lib.entitis;

import java.sql.Date;

public class Person {
	protected String iD;
	protected String fullName;
	protected String gender;
	protected Date dob;
	protected String address;
	protected String license;
	protected String phoneNumber;
	protected String gmail;

	public Person() {
		iD = "";
		fullName = "";
		gender = "";
		dob = Date.valueOf("2000-01-01");
		address = "";
		license = "";
		phoneNumber = "";
		gmail = "";
	}

	public Person(String iD, String fullName, String gender, Date dob, String address, String license,
			String phoneNumber, String gmail) {
		this.iD = iD;
		this.fullName = fullName;
		this.gender = gender;
		this.dob = dob;
		this.address = address;
		this.license = license;
		this.phoneNumber = phoneNumber;
		this.gmail = gmail;
	}

	public String getID() {
		return iD;
	}

	public void setID(String iD) {
		this.iD = iD;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGmail() {
		return gmail;
	}

	public void setGmail(String gmail) {
		this.gmail = gmail;
	}

}
