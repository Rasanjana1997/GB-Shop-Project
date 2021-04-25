package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductManagement {
	
	// create database connection
	private Connection connect() {
		
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/gb_shop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return con;
	}

	public String insertProduct(String pId, String pName, String pDes, String price) {
		
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "Error found in connecting to the database for inserting......!";
			}
			
			// create the prepared statement
			
			String query = " insert into product(`pId`,`pName`,`pDes`,`price`)" + " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// values binding
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, pName);
			preparedStmt.setString(3, pDes);
			preparedStmt.setString(4, price);

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			output = "Data Inserted successfully.....!";
			
		} catch (Exception ex) {
			
			output = "Error found in inserting the Product.....";
			System.err.println(ex.getMessage());
		}
		return output;
	}

	public String readProduct() {
		
		String output = "";
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error found in connecting to the database for reading.....!";
			}
			// html table creation
			output = "<table border=\"1\"><tr><th>Product ID</th><th>Product Name</th><th>Description</th><th>Price</th></tr>";
			
			String query = "select * from product";
			
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			
			// iteration
			while (rs.next()) {
				
				String pId = Integer.toString(rs.getInt("pId"));
				String pName = rs.getString("pName");
				String pDes = rs.getString("pDes");
				String price = Float.toString(rs.getFloat("price"));

				output += "<tr><td>" + pId + "</td>";
				output += "<td>" + pName + "</td>";
				output += "<td>" + pDes + "</td>";
				output += "<td>" + price + "</td>";

			}
			
			con.close();

			output += "</table>";
			
		} catch (Exception ex) {
			
			output = "Error while reading the Product.....!";
			
			System.err.println(ex.getMessage());
		}
		return output;
	}

	public String updateProduct(String pId, String pName, String pDes, String price) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error found in connecting to the database for updating.....!";
			}

			// prepared statement creation
			
			String query = "UPDATE product SET pName=?,pDes=?,price=? WHERE pId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// values binding

			preparedStmt.setString(2, pName);
			preparedStmt.setString(3,pDes );
			preparedStmt.setFloat(4, Float.parseFloat(price));

			// execute the prepared statement
			
			preparedStmt.execute();
			con.close();

			output = "Updated successfully....!";
			
		} catch (Exception ex) {
			
			output = "Error found in updating the Prouduct.....!";
			
			System.err.println(ex.getMessage());
		}

		return output;
	}

	public String deleteProduct(String pId) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				
				return "Error found in connecting to the database for deleting.....!";
				
			}

			// create a prepared statement
			
			String query = "delete from product where pId=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// values building
			
			preparedStmt.setInt(1, Integer.parseInt(pId));

			// execute the prepare statement
			
			preparedStmt.execute();
			con.close();

			output = "Data Deleted successfully......";
			
		} catch (Exception ex) {
			
			output = "Error while deleting the Product.....!";
			
			System.err.println(ex.getMessage());
			
		}

		return output;
	}

}
