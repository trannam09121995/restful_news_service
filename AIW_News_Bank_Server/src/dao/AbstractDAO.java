package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AbstractDAO {
	
	private Connection connection;
	private Statement statement;
	
	private static final String URL = "jdbc:mysql://localhost:3306/aiw_news_bank";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	public AbstractDAO() {
		initConnection();
	}
	
	private void initConnection() {
		try {
			
			//load driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//create connection
			if (connection == null) {
				connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			}
			
			//create statement
			if (statement == null) {
				statement = connection.createStatement();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected ResultSet retrieveData(String query) {
		ResultSet rs = null;
		PreparedStatement ppStatement = null;
		try {
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	protected ResultSet retrieveDataUsingFields(String query, String... fields) {
		ResultSet rs = null;
		PreparedStatement ppStatement = null;
		try {
			ppStatement = connection.prepareStatement(query);
			for (int i = 0; i < fields.length; i++) {
				ppStatement.setString(i + 1, fields[i]);
			}
			rs = ppStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	protected ResultSet retrieveDataUsingFieldsLIKE(String query, String... fields) {
		ResultSet rs = null;
		PreparedStatement ppStatement = null;
		try {
			ppStatement = connection.prepareStatement(query);
			for (int i = 0; i < fields.length; i++) {
				ppStatement.setString(i + 1, "%"+fields[i]+"%");
			}
			rs = ppStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	protected void insertDataUsingFields(String query, String... fields) {
		PreparedStatement ppStatement = null;
		try {
			ppStatement = connection.prepareStatement(query);
			for (int i = 0; i < fields.length; i++) {
				ppStatement.setString(i + 1, fields[i]);
			}
			ppStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*protected void test_function(String... fields) {
		for (int i = 0; i < fields.length; i++) {
			System.out.println(i + " - " +fields[i]);
		}
	}
	
	public static void main(String[] args) {
		AbstractDAO a = new AbstractDAO();
		System.out.println("Test 1: ");
		a.test_function("a", "b", "c");
		System.out.println("Test 2: ");
		a.test_function("x", "y", "z");
	}*/

}
