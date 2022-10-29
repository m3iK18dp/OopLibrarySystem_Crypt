package com.library.core_lib.repository.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.core_lib.entitis.Reader;
import com.library.core_lib.repository.AbsBaseRepository;
import com.library.core_lib.repository.GenericRepository;

public class ReaderRepositoryImpl extends AbsBaseRepository implements GenericRepository<Reader> {

	public ReaderRepositoryImpl() {

	}

	@Override
	public List<Reader> get(String s) {
		ArrayList<Reader> list = new ArrayList<>();
		String sql = "SELECT * FROM Reader " + s;
		try {
			ResultSet rs = this.conn.prepareStatement(sql).executeQuery();
			while (rs.next()) {
				Reader st = new Reader();
				st.setID(decryptAES.decrypt(rs.getString("Id")));
				st.setFullName(decryptAES.decrypt(rs.getString("FullName")));
				st.setGender(decryptAES.decrypt(rs.getString("Gender")));
				st.setDob(Date.valueOf(decryptAES.decrypt(rs.getString("Dob"))));
				st.setAddress(decryptAES.decrypt(rs.getString("Address")));
				st.setLicense(decryptAES.decrypt(rs.getString("License")));
				st.setPhoneNumber(decryptAES.decrypt(rs.getString("PhoneNumber")));
				st.setGmail(decryptAES.decrypt(rs.getString("Gmail")));
				st.setKindOfReader(decryptAES.decrypt(rs.getString("KindOfReader")));
				st.setStartDate(rs.getDate("StartDate"));
				st.setEndDate(rs.getDate("EndDate"));
				list.add(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean add(Reader r) {
		String sql = "INSERT INTO Reader(Id,FullName,Gender,Dob,Address,License,PhoneNumber,Gmail,KindOfReader,StartDate,EndDate) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = this.conn.prepareStatement(sql);
			ps.setString(1, encryptAES.encrypt(r.getID()));
			ps.setString(2, encryptAES.encrypt(r.getFullName()));
			ps.setString(3, encryptAES.encrypt(r.getGender()));
			ps.setString(4, encryptAES.encrypt("" + r.getDob()));
			ps.setString(5, encryptAES.encrypt(r.getAddress()));
			ps.setString(6, encryptAES.encrypt(r.getLicense()));
			ps.setString(7, encryptAES.encrypt(r.getPhoneNumber()));
			ps.setString(8, encryptAES.encrypt(r.getGmail()));
			ps.setString(9, encryptAES.encrypt(r.getKindOfReader()));
			ps.setDate(10, r.getStartDate());
			ps.setDate(11, r.getEndDate());
			return (ps.executeUpdate() > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Reader r) {
		try {
			String update = "UPDATE Reader SET FullName = N'"
					+ encryptAES.encrypt(r.getFullName())
					+ "', Gender = N'" + encryptAES.encrypt(r.getGender())
					+ "', Dob = '" + encryptAES.encrypt(r.getDob() + "")
					+ "',Address = N'" + encryptAES.encrypt(r.getAddress())
					+ "',License = '" + encryptAES.encrypt(r.getLicense())
					+ "', PhoneNumber = '" + encryptAES.encrypt(r.getPhoneNumber())
					+ "',Gmail = '" + encryptAES.encrypt(r.getGmail())
					+ "', KindOfReader = N'" + encryptAES.encrypt(r.getKindOfReader())
					+ "', StartDate = '" + r.getStartDate()
					+ "', EndDate = '" + r.getEndDate()
					+ "' WHERE Id = '" + encryptAES.encrypt(r.getID()) + "'";
			Statement statement = this.conn.createStatement();
			return statement.executeUpdate(update) > 0;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String s) {
		try {
			String delete = "DELETE FROM Reader " + s;
			Statement statement = this.conn.createStatement();
			return statement.executeUpdate(delete) > 0;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}

}
