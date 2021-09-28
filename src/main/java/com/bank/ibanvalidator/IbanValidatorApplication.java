package com.bank.ibanvalidator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class IbanValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbanValidatorApplication.class, args);
		createNewDatabase("iban.db");
	}

	public static void createNewDatabase(String fileName) {

		String url = "jdbc:sqlite:" + fileName;

		File file = new File(fileName);

		if(file.exists() && !file.isDirectory()) {
			return;
		}

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
