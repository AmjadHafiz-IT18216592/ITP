package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtil {

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	private static Connection connection = null;
	
	private static final String connStr = "jdbc:mysql://localhost:3306/textile";
	
	public static  void dbConnect() throws SQLException , ClassNotFoundException{
		
		try {
			Class.forName(JDBC_DRIVER);
		}
		catch(ClassNotFoundException e) {
			System.out.println("where is your mysql JDBC driver");
			e.printStackTrace();
			throw e;
		}
		
		System.out.println(" JDBC Driver has been registered! ");
		
		try {
			 connection = DriverManager.getConnection(connStr,"root","root");
			
		}
		catch(SQLException e) {
			System.out.println("Connection fail Check output console");
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public static  void dbDisonnect() throws SQLException {
		
		try {
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	
	public static void dbExecuteQuery(String sqlStmt) throws SQLException, ClassNotFoundException{
		Statement stmt = null;
		
		try {
			dbConnect();
		stmt = connection.createStatement();
		stmt.execute(sqlStmt);
			
		}
		catch(SQLException e) {
			System.out.println("Problem occured at DBExcute queary operation "+e);
		}
		
		finally {
			if(stmt != null) {
				stmt.close();
			}
			dbDisonnect();
		}
	}
	
	public static ResultSet getValues(String sqlStmt) throws SQLException, ClassNotFoundException{
		Statement stmt = null;
		
		try {
			dbConnect();
		stmt = connection.createStatement();
		 ResultSet rs =  stmt.executeQuery(sqlStmt);
		 rs.next();
		 return rs;
			
		}
		catch(SQLException e) {
			System.out.println("Problem occured at DBExcute queary operation "+e);
		}
		
		return null;
	}
	
	public static PreparedStatement setValues(String sqlStmt) throws SQLException, ClassNotFoundException{
		PreparedStatement stmt = null;
		
		try {
			dbConnect();
		stmt = connection.prepareStatement(sqlStmt);
		
		 return stmt;
			
		}
		catch(SQLException e) {
			System.out.println("Problem occured at DBExcute queary operation "+e);
		}
		
		finally {
			if(stmt != null) {
				stmt.close();
			}
			dbDisonnect();
		}
		return null;
	}
}
