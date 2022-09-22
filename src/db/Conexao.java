package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Conexao {

	private static Connection conn = null;	

	public static Connection getConnection() {
		try {
			Properties props = loadProperties();
			String dburl = props.getProperty("dburl");
			conn = DriverManager.getConnection(dburl, props);
			return conn;
		} catch (SQLException error) {
			throw new DBException(error.getMessage());
		}
	}

	private static Properties loadProperties() {
		try (FileInputStream file = new FileInputStream("C:\\Curso Java\\Codigo Projeto 05\\Data Acess Objeto\\src\\db.properties")) {
			Properties properties = new Properties();
			properties.load(file);
			return properties;
		} catch (IOException error) {
			throw new DBException(error.getMessage());
		}
	}
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException error) {
				throw new DBException(error.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException error) {
				throw new DBException(error.getMessage());
			}
		}
	}
}
