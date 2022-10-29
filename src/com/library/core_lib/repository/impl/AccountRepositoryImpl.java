package com.library.core_lib.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.core_lib.entitis.Account;
import com.library.core_lib.repository.AbsBaseRepository;
import com.library.core_lib.repository.GenericRepository;

public class AccountRepositoryImpl extends AbsBaseRepository implements GenericRepository<Account> {

	public AccountRepositoryImpl() {
	}

	@Override
	public List<Account> get(String s) {
		String sql = "SELECT * FROM Account " + s;
		List<Account> accounts = new ArrayList<>();
		try {
			ResultSet rs = this.conn.prepareStatement(sql).executeQuery();
			while (rs.next())
				accounts.add(new Account(
						decryptAES.decrypt(rs.getString("UserName")),
						decryptRSA.decrypt(rs.getString("Password"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accounts;
	}

	@Override
	public boolean add(Account a) {
		try {
			PreparedStatement ps = this.conn.prepareStatement("INSERT INTO Account (UserName,Password) VALUES(?,?)");
			ps.setString(1, encryptAES.encrypt(a.getUserName()));
			ps.setString(2, encryptRSA.encrypt(a.getPassword()));
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(Account a) {
		try {
			PreparedStatement ps = this.conn
					.prepareStatement("UPDATE Account SET Password = '" + encryptRSA.encrypt(a.getPassword())
							+ "' WHERE UserName = '" + encryptAES.encrypt(a.getUserName()) + "'");
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String s) {
		String sql = "DELETE FROM Account " + s;
		try {
			Statement statement = this.conn.createStatement();
			return statement.executeUpdate(sql) > 0;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
	}
}
