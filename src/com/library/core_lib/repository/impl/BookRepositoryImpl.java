package com.library.core_lib.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.core_lib.entitis.Book;
import com.library.core_lib.repository.AbsBaseRepository;
import com.library.core_lib.repository.GenericRepository;

public class BookRepositoryImpl extends AbsBaseRepository implements GenericRepository<Book> {

	public BookRepositoryImpl() {

	}

	@Override
	public List<Book> get(String s) {
		List<Book> list = new ArrayList<>();
		String sql = "SELECT * FROM Book " + s;
		try {
			ResultSet rs = this.conn.prepareStatement(sql).executeQuery();
			while (rs.next()) {
				Book st = new Book();
				st.setID(decryptAES.decrypt(rs.getString("Id")));
				st.setName(decryptAES.decrypt(rs.getString("Name")));
				st.setAuthor(decryptAES.decrypt(rs.getString("Author")));
				st.setQuantity(Integer.parseInt(decryptAES.decrypt(rs.getString("Quantity"))));
				st.setKindOfBook(decryptAES.decrypt(rs.getString("KindOfBook")));
				list.add(st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean add(Book b) {
		String sql = "INSERT INTO Book (Id,Name,Author,Quantity,KindOfBook) VALUES(?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, encryptAES.encrypt(b.getID()));
			ps.setString(2, encryptAES.encrypt(b.getName()));
			ps.setString(3, encryptAES.encrypt(b.getAuthor()));
			ps.setString(4, encryptAES.encrypt("" + b.getQuantity()));
			ps.setString(5, encryptAES.encrypt(b.getKindOfBook()));
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Book b) {
		try {
			String update = "UPDATE Book SET Name = N'"
					+ encryptAES.encrypt(b.getName())
					+ "', Author = N'" + encryptAES.encrypt(b.getAuthor())
					+ "', Quantity = '" + encryptAES.encrypt(b.getQuantity() + "")
					+ "', KindOfBook = N'" + encryptAES.encrypt(b.getKindOfBook())
					+ "' WHERE Id = '" + encryptAES.encrypt(b.getID()) + "'";
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
			String sql = "DELETE FROM Book " + s;
			Statement statement = conn.createStatement();
			return statement.executeUpdate(sql) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
