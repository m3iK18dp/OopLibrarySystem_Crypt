package com.library.core_lib.repository.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.core_lib.entitis.LibStaff;
import com.library.core_lib.repository.AbsBaseRepository;
import com.library.core_lib.repository.GenericRepository;

public class LibStaffRepositoryImpl extends AbsBaseRepository implements GenericRepository<LibStaff> {

	public LibStaffRepositoryImpl() {
	}

	@Override
	public List<LibStaff> get(String s) {
		ArrayList<LibStaff> list = new ArrayList<>();
		String sql = "SELECT * FROM LibStaff " + s;
		try {
			ResultSet rs = this.conn.prepareStatement(sql).executeQuery();
			while (rs.next()) {
				LibStaff st = new LibStaff();
				st.setID(decryptAES.decrypt(rs.getString("Id")));
				st.setFullName(decryptAES.decrypt(rs.getString("FullName")));
				st.setGender(decryptAES.decrypt(rs.getString("Gender")));
				st.setDob(Date.valueOf(decryptAES.decrypt("" + rs.getDate("Dob"))));
				st.setAddress(decryptAES.decrypt(rs.getString("Address")));
				st.setLicense(decryptAES.decrypt(rs.getString("License")));
				st.setPhoneNumber(decryptAES.decrypt(rs.getString("PhoneNumber")));
				st.setGmail(decryptAES.decrypt(rs.getString("Gmail")));
				st.setStartWorkDate(Date.valueOf(decryptAES.decrypt(rs.getDate("StartWorkDate") + "")));
				st.setPosition(decryptAES.decrypt(rs.getString("Position")));
				st.setBasicSalary(Integer.parseInt(decryptAES.decrypt(rs.getInt("BasicSalary") + "")));
				st.setSalaryBonus(Integer.parseInt(decryptAES.decrypt(rs.getInt("SalaryBonus") + "")));
				st.setPenalty(Integer.parseInt(decryptAES.decrypt(rs.getInt("Penalty") + "")));
				st.setActualSalary();
				list.add(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean add(LibStaff ls) {
		String sql = "INSERT INTO LibStaff (Id,FullName,Gender,Dob,Address,License,PhoneNumber,Gmail, StartWorkDate,Position,BasicSalary,SalaryBonus,Penalty,ActualSalary) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = this.conn.prepareStatement(sql);
			ps.setString(1, decryptAES.decrypt(ls.getID()));
			ps.setString(2, decryptAES.decrypt(ls.getFullName()));
			ps.setString(3, decryptAES.decrypt(ls.getGender()));
			ps.setString(4, decryptAES.decrypt("" + ls.getDob()));
			ps.setString(5, decryptAES.decrypt(ls.getAddress()));
			ps.setString(6, decryptAES.decrypt(ls.getLicense()));
			ps.setString(8, decryptAES.decrypt(ls.getGmail()));
			ps.setString(8, decryptAES.decrypt(ls.getGmail()));
			ps.setString(8, decryptAES.decrypt(ls.getGmail()));
			ps.setString(7, decryptAES.decrypt(ls.getPhoneNumber()));
			ps.setString(8, decryptAES.decrypt(ls.getGmail()));
			ps.setString(9, decryptAES.decrypt("" + ls.getStartWorkDate()));
			ps.setString(10, decryptAES.decrypt(ls.getPosition()));
			ps.setString(11, decryptAES.decrypt("" + ls.getBasicSalary()));
			ps.setString(12, decryptAES.decrypt("" + ls.getSalaryBonus()));
			ps.setString(13, decryptAES.decrypt("" + ls.getPenalty()));
			ps.setString(14, decryptAES.decrypt("" + ls.getActualSalary()));
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(LibStaff e) {
		try {
			String update = "UPDATE LibStaff SET FullName = N'" + decryptAES.decrypt(e.getFullName())
					+ "', Gender = N'" + decryptAES.decrypt(e.getGender())
					+ "', Dob = '" + decryptAES.decrypt(e.getDob() + "")
					+ "',Address = N'" + decryptAES.decrypt(e.getAddress())
					+ "',License = '" + decryptAES.decrypt(e.getLicense())
					+ "', PhoneNumber = '" + decryptAES.decrypt(e.getPhoneNumber())
					+ "',Gmail = '" + decryptAES.decrypt(e.getGmail())
					+ "',StartWorkDate = '" + decryptAES.decrypt("" + e.getStartWorkDate())
					+ "', Position = N'" + decryptAES.decrypt(e.getPosition())
					+ "',BasicSalary = '" + decryptAES.decrypt("" + e.getBasicSalary())
					+ "', SalaryBonus = '" + decryptAES.decrypt("" + e.getSalaryBonus())
					+ "', Penalty = '" + decryptAES.decrypt("" + e.getPenalty())
					+ "' WHERE Id = '" + decryptAES.decrypt(e.getID()) + "'";
			Statement statement = this.conn.createStatement();
			return statement.executeUpdate(update) > 0;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String s) {
		try {
			String sql = "DELETE FROM LibStaff " + s;
			Statement statement = this.conn.createStatement();
			return statement.executeUpdate(sql) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
