package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf?useTimezone=true&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
  
	public String insertItem( String Ruser_name, String Ruser_address, String Ruser_gender,String Ruser_age,String Ruser_notes) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into reg_user(`Ruser_ID`,`Ruser_name`,`Ruser_address`,`Ruser_gender`,`Ruser_age`,`Ruser_notes`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, Ruser_name);
			preparedStmt.setString(3, Ruser_address);
			preparedStmt.setString(4, Ruser_gender);
			preparedStmt.setString(5, Ruser_age);
			preparedStmt.setString(6, Ruser_notes);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readItems() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}   
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Ruser_ID</th><th>Ruser_name</th><th>Ruser_address</th><th>Ruser_gender<th>Ruser_age</th><th>Ruser_notes</th></th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from reg_user";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String Ruser_ID = Integer.toString(rs.getInt("Ruser_ID"));
				String Ruser_name = rs.getString("Ruser_name");
				String Ruser_address = rs.getString("Ruser_address");
				String Ruser_gender = rs.getString("Ruser_gender");
				String Ruser_age = Integer.toString(rs.getInt("Ruser_age"));
				String Ruser_notes = rs.getString("Ruser_notes");
				// Add into the html table
				output += "<tr><td>" + Ruser_ID + "</td>";
				output += "<td>" + Ruser_name + "</td>";
				output += "<td>" + Ruser_address + "</td>";
				output += "<td>" + Ruser_gender + "</td>";
				output += "<td>" + Ruser_age + "</td>";
				output += "<td>" + Ruser_notes + "</td>";
				  
				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"items.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
						+ "<input name=\"itemID\" type=\"hidden\" value=\"" + Ruser_ID + "\">" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateItem( String Ruser_ID ,String Ruser_name, String Ruser_address, String Ruser_gender,String Ruser_age,String Ruser_notes) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE reg_user SET Ruser_name=?,Ruser_address=?,Ruser_gender=?,Ruser_age=?,Ruser_notes=? WHERE Ruser_ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, Ruser_name);
			preparedStmt.setString(2, Ruser_address);
			preparedStmt.setString(3, Ruser_gender);
			preparedStmt.setString(4, Ruser_age);
			preparedStmt.setString(5, Ruser_notes);
			preparedStmt.setInt(6,Integer.parseInt( Ruser_ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteItem(String Ruser_ID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from reg_user where Ruser_ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(Ruser_ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String readUsersAppointmentsHistory(String Ruser_ID1) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Appointment_ID</th><th>Ruser_ID</th><th>Rdoctor_ID</th><th>Hospital_ID</th><th>Rdoctor_name</th><th>Appointment_type</th><th>Appointment_no</th><th>Appointment_date</th><th>Appointment_description</th></tr>";
			String query = "select * from appointment where Ruser_ID="+ Ruser_ID1;
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next())
			{
				String Appointment_ID = Integer.toString(rs.getInt("Appointment_ID"));
				String Ruser_ID = Integer.toString(rs.getInt("Ruser_ID"));
				String Rdoctor_ID = Integer.toString(rs.getInt("Rdoctor_ID"));
				String Rdoctor_name = rs.getString("Rdoctor_name");
				String Hospital_ID = Integer.toString(rs.getInt("Hospital_ID"));
				String Appointment_type = rs.getString("Appointment_type");
				String Appointment_no = Integer.toString(rs.getInt("Appointment_no"));
				String Appointment_date = rs.getString("Appointment_date");
				String Appointment_description = rs.getString("Appointment_description");
	
				// Add into the html table
				output += "<tr><td>" + Appointment_ID + "</td>";
				output += "<td>" + Ruser_ID + "</td>";
				output += "<td>" + Rdoctor_ID + "</td>";
				output += "<td>" + Rdoctor_name + "</td>";
				output += "<td>" + Hospital_ID + "</td>";
				output += "<td>" + Appointment_type + "</td>";
				output += "<td>" + Appointment_no + "</td>";
				output += "<td>" + Appointment_date + "</td>";
				output += "<td>" + Appointment_description + "</td>";
	
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the users appointments.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String userLogin(String Ruser_name, String Ruser_ID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			
			String query ="select Ruser_ID,Ruser_name from reg_user where Ruser_name="+Ruser_name+" AND Ruser_ID="+Ruser_ID;
			PreparedStatement preparedStmt = con.prepareStatement(query);
			ResultSet rs = ((java.sql.Statement) preparedStmt).executeQuery(query);
			
			
			  while (rs.next()) {
			        //String Ruser_name1 = rs.getString("Ruser_name");
			        String Ruser_ID1 = rs.getString("Ruser_ID");
			        
			  
			        if((Ruser_name.equalsIgnoreCase(Ruser_name)) && (Ruser_ID.equalsIgnoreCase(Ruser_ID1))) {
			        	//output ="     Login Failed  !!";
			        	output ="     Login Successful  !!           You're logged as"   +Ruser_name;
			        	}
		              else {
		                output ="      Login Failed...!!";
		            	 //output ="     Login Successful  !!           You're logged as"   +Ruser_name;
		              	} 
			  	}
			
			con.close();
			
		}catch (Exception e) {
			output = "Error while Logging.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}




