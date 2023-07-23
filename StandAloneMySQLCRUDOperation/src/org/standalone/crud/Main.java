package org.standalone.crud;

import java.sql.*;
import java.util.Scanner;


public class Main {


	public static String dbURL="jdbc:mysql://localhost:3306/sampledb";
	public static String username="root";
	public static String password="tiger";
	public static Connection con;
	
	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Enter a no as your preperence");
		Scanner sc = new Scanner(System.in);
		String  input= sc.nextLine();
		
		if(input.equals("insert")) {
			 ConnectionEstablishment();
			 insert("Ram","1234", "Ramprasad Patra","ram@gmail.com" );		
		}
		else if(input.equals("select")) {
			 ConnectionEstablishment();
			 select();	
		}
		else if(input.equals("update")) {
			ConnectionEstablishment();
			update();
		}
		else if(input.equals("delete")) {
			ConnectionEstablishment();
			delete();
		}
			
	}
	
	public static void ConnectionEstablishment() {
		// TODO Auto-generated constructor stub
		try {
			con= DriverManager.getConnection(dbURL, username, password);
			if(con !=null) {
				System.out.println("Data base Connected");
			}
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Sql Exception");
		}
		
	}
	
	public static void insert(String username, String password, String fullname, String email) {
		
		try {
			String sql="insert into Users(username, password, fullname, email) values(?,?,?,?)";
						
			PreparedStatement statement=  con.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			statement.setString(3, fullname);
			statement.setString(4, email);
			
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
			    System.out.println("A new user was inserted successfully!");
			}
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			
		}
		
	}
	
	public static void select() {
		try {
			String sql="select * from Users";
			System.out.println("select method executed");
			Statement statement= con.createStatement();
			ResultSet result=statement.executeQuery(sql);
			int count=0;
			
			while (result.next()){
			    String name = result.getString(2);
			    String pass = result.getString(3);
			    String fullname = result.getString("fullname");
			    String email = result.getString("email");
			 
			    String output = "User #%d: %s - %s - %s - %s";
			    System.out.println(String.format(output, ++count, name, pass, fullname, email));
			}
			con.close();
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void update() {
		String sql = "UPDATE Users SET password=?, fullname=?, email=? WHERE username=?";
		 
		try {
			System.out.println("inside update method");
		PreparedStatement statement= con.prepareStatement(sql);
		statement.setString(1, "bill");
		statement.setString(2, "Bill Gates");
		statement.setString(3, "bill@gmail.com");
		statement.setString(4, "Ram");
		 
		int rowsUpdated = statement.executeUpdate();
		if (rowsUpdated > 0) {
		    System.out.println("An existing user was updated successfully!");
		}
		con.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void delete() {
		try {
			String sql = "DELETE FROM Users WHERE username=?";
			System.out.println("inside delete method");
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, "Ram");
			 
			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
			    System.out.println("A user was deleted successfully!");
			}
			con.close();
		}catch(Exception ex) {
			ex.getStackTrace();
		}
	}
}
