package com.library.core_lib.entitis;

import java.sql.Date;

public class LibStaff extends Person {
	private Date startWorkDate;
	private String position;
	private int basicSalary;
	private int salaryBonus;
	private int penalty; // tiền phạt
	@SuppressWarnings("unused")
	private int actualSalary;

	public LibStaff() {
		startWorkDate = Date.valueOf("2000-01-01");
		position = "";
		basicSalary = 0;
		salaryBonus = 0;
		penalty = 0;
		actualSalary = 0;
	}

	public LibStaff(String id, String fullName, String gender, Date dob, String address, String license,
			String phoneNumber, String gmail, Date startWorkDate, String position, int basicSalary, int salarybonus,
			int penalty, int actualSalary) {
		super(id, fullName, gender, dob, address, license, phoneNumber, gmail);
		this.startWorkDate = startWorkDate;
		this.position = position;
		this.basicSalary = basicSalary;
		this.salaryBonus = salarybonus;
		this.penalty = penalty;
		this.actualSalary = actualSalary;
	}

	public Date getStartWorkDate() {
		return startWorkDate;
	}

	public void setStartWorkDate(Date startWorkDate) {
		this.startWorkDate = startWorkDate;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(int basicSalary) {
		this.basicSalary = basicSalary;
	}

	public int getSalaryBonus() {
		return salaryBonus;
	}

	public void setSalaryBonus(int salarybonus) {
		this.salaryBonus = salarybonus;
	}

	public int getPenalty() {
		return penalty;
	}

	public void setPenalty(int penalty) {
		this.penalty = penalty;
	}

	public void setActualSalary() {
		this.actualSalary = getBasicSalary() + getSalaryBonus() - getPenalty();
	}

	public int getActualSalary() {
		return getBasicSalary() + getSalaryBonus() - getPenalty();
	}

}
