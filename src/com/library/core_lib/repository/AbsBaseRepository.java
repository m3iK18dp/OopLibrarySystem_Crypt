package com.library.core_lib.repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import com.library.core_lib.crypt_aes.*;
import com.library.core_lib.crypt_rsa.*;

public abstract class AbsBaseRepository {
	public Connection conn;
	public EncryptionRSA encryptRSA;
	public EncryptionAES encryptAES;
	public DecryptionRSA decryptRSA;
	public DecryptionAES decryptAES;
	static final String USER = "sa";
	static final String PASS = "sa";

	public AbsBaseRepository() {
		try {
			new SecurityKeyPairGeneratorRSA();
			new SecurityKeyPairGeneratorAES();
			encryptRSA = new EncryptionRSA();
			encryptAES = new EncryptionAES();
			decryptRSA = new DecryptionRSA();
			decryptAES = new DecryptionAES();
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://localhost;databasename=LibraryCrypt;username=" + USER + ";password=" + PASS);
		} catch (Exception e) {
			conn();
		}
	}

	public void conn() {
		try {
			Connection conn1 = DriverManager.getConnection(
					"jdbc:sqlserver://localhost;databasename=master;username=" + USER + ";password=" + PASS);
			if (conn1 != null) {
				conn1.createStatement().executeUpdate("CREATE DATABASE [LibraryCrypt]");
				conn = DriverManager.getConnection(
						"jdbc:sqlserver://localhost;databasename=LibraryCrypt;username=" + USER + ";password=" + PASS);
				conn.createStatement().executeUpdate(readerFileSQL("lib.txt"));
				conn.createStatement().executeUpdate(readerFileSQL("view.txt"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readerFileSQL(String url) {
		@SuppressWarnings("resource")
		FileInputStream fin = null;
		String sql = "";
		try {
			fin = new FileInputStream(url);
			int ch;
			while ((ch = fin.read()) != -1)
				sql += (char) ch;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fin != null)
					fin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sql;
	}

	public AbsBaseRepository(Connection conn) {
		this.conn = conn;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
}