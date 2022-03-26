package com.xadmin.usermanagement.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xadmin.usermanagement.bean.User;

public class UserDao {

	private String jdbcURL = "jdbc:mysql://localhost:3306/userdb?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "rootpasswordgiven";
	private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	
	
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "(name,email,country)VALUES"
	
			+ "(?,?,?);";
	
	
	
	private final static String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
	private final static String SELECT_ALL_USERS = "select * from users";
	private final static String DELETE_USERS_SQL = "delete from users where id =?;";
	private final static String UPDATE_USERS_SQL = "update users set name=?, email=? ,country=?  where id =?;";

	public UserDao() {
		
		
	}
	
	

	private Connection getConnection() {
         Connection connection=null;
		try {
		
			Class.forName(jdbcDriver);
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		return connection; 

	}

// insert user 

	public void insertUser(User user) throws SQLException {
		System.out.println(INSERT_USERS_SQL);
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {

			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
		
	}

//select by id 
	public User selectUser(int id) {
		User user = null;
		// Step1: Establishe a Connection
		try (Connection connection = getConnection();
				// Step2: Create a Statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// step3:Excute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			// Step 4:Process theResultSet object
			while (rs.next()) {

				String name = rs.getNString("name");
				String email = rs.getNString("email");
				String country = rs.getNString("country");
				user = new User(id, name, email, country);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}

//select all
	public List<User> selectAllUser() {
		// using try-catch resourses to avoid closing (boiler plate code)
		List<User> users = new ArrayList<>();
// step1: Establsing a connection object 
		try (Connection connection = getConnection();
// step2 Creat a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {

// step3: Execute the query or update the query 
			ResultSet rs = preparedStatement.executeQuery();
// Step4:Process the ResultSet object
			while (rs.next()) {

				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				users.add(new User(id, name, email, country));
			}

		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;

	}

//update user 
	public boolean updateUser(User user) throws SQLException {

		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
			System.out.println("update User:" + statement);
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getCountry());
			statement.setInt(4, user.getId());
			rowUpdated = statement.executeUpdate() > 0;

		}
		return rowUpdated;
	}

//delet from user 

	public boolean deleteUser(int id) throws SQLException {

		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;

	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState:" + ((SQLException) e).getSQLState());
				System.err.println("Error Code:" + ((SQLException) e).getErrorCode());
				System.err.println("Message:" + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause:" + t);
					t = t.getCause();
				}

			}
		}

	}

}
