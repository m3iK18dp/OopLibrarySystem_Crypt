package com.library.core_lib.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.core_lib.entitis.RentalInformation;
import com.library.core_lib.repository.AbsBaseRepository;
import com.library.core_lib.repository.GenericRepository;

public class RentalInforRepositoryImpl extends AbsBaseRepository implements GenericRepository<RentalInformation> {

	public RentalInforRepositoryImpl() {
	}

	@Override
	public List<RentalInformation> get(String s) {
		ArrayList<RentalInformation> list = new ArrayList<>();
		String sql = "SELECT * FROM RentalInformation " + s;
		try {
			ResultSet rs = this.conn.prepareStatement(sql).executeQuery();
			while (rs.next()) {
				RentalInformation ri = new RentalInformation();
				ri.setID(decryptAES.decrypt(rs.getString("Id")));
				ri.setReaderID(decryptAES.decrypt(rs.getString("ReaderId")));
				ri.setBookID(decryptAES.decrypt(rs.getString("BookId")));
				ri.setLibStaffID(decryptAES.decrypt(rs.getString("LibStaffId")));
				ri.setBookBorrowDate(rs.getDate("BookBorrowDate"));
				ri.setBookReturnDate(rs.getDate("BookReturnDate"));
				ri.setNote(decryptAES.decrypt(rs.getString("Note")));
				list.add(ri);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean add(RentalInformation ri) {
		String sql = "INSERT INTO RentalInformation (Id,ReaderId,BookId,LibStaffId,BookBorrowDate,BookReturnDate,Note) VALUES(?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = this.conn.prepareStatement(sql);
			ps.setString(1, encryptAES.encrypt(ri.getID()));
			ps.setString(2, encryptAES.encrypt(ri.getReaderID()));
			ps.setString(3, encryptAES.encrypt(ri.getBookID()));
			ps.setString(4, encryptAES.encrypt(ri.getLibStaffID()));
			ps.setDate(5, ri.getBookBorrowDate());
			ps.setDate(6, ri.getBookReturnDate());
			ps.setString(7, encryptAES.encrypt(ri.getNote()));
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(RentalInformation ri) {
		try {
			String update = "UPDATE RentalInformation SET ReaderId = '" + encryptAES.encrypt(ri.getReaderID())
					+ "', BookId = '" + encryptAES.encrypt(ri.getBookID())
					+ "', LibStaffId = '" + encryptAES.encrypt(ri.getLibStaffID())
					+ "', BookBorrowDate = '" + ri.getBookBorrowDate()
					+ "', BookReturnDate = '" + ri.getBookReturnDate()
					+ "', Note = N'" + encryptAES.encrypt(ri.getNote())
					+ "' WHERE Id = '" + encryptAES.encrypt(ri.getID()) + "'";
			Statement statement = this.conn.createStatement();
			return statement.executeUpdate(update) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String s) {
		try {
			String delete = "DELETE FROM RentalInformation " + s;
			Statement statement = this.conn.createStatement();
			return statement.executeUpdate(delete) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
