package com;
import java.sql.*;
public class Item
{
private Connection connect()
 {
 Connection con = null;
 try
 {
 Class.forName("com.mysql.jdbc.Driver");
 con =
 DriverManager.getConnection(
 "jdbc:mysql://127.0.0.1:3306/itemdb", "root", "");
 }
 catch (Exception e)
 {
 e.printStackTrace();
 }
 return con;
 }
public String readItems()
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {
 return "Error while connecting to the database for reading.";
 }
 // Prepare the html table to be displayed
 output = "<table border='1'><tr><th>Item Name</th><th>Item Price</th><th>Item Category</th>" + "<th>User ID</th><th>Update</th><th>Remove</th></tr>";
 String query = "select * from item";
 Statement stmt = con.createStatement();
 ResultSet rs = stmt.executeQuery(query);
 // iterate through the rows in the result set
 while (rs.next())
 {
 String itemID = Integer.toString(rs.getInt("itemID"));
 String itemName = rs.getString("itemName");
 String itemPrice = Double.toString(
		 rs.getDouble("itemPrice"));
 String category = rs.getString("category");
 String userid = rs.getString("userid");
 // Add into the html table
 output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + itemID
 + "'>" + itemName + "</td>";
 output += "<td>" + itemPrice + "</td>";
 output += "<td>" + category + "</td>";
 output += "<td>" + userid + "</td>";
 // buttons
output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>" + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='"
 + itemID + "'>" + "</td></tr>";
 }
 con.close();
 // Complete the html table
 output += "</table>";
 }
 catch (Exception e)
 {
 output = "Error while reading the items.";
 System.err.println(e.getMessage());
 }
 return output;
 }
public String insertItem(String name, String price, String category,
  String uid)
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {
 return "Error while connecting to the database for inserting.";
 }
 // create a prepared statement
 String query = " insert into item(`itemID`,`itemName`,`itemPrice`,`category`,`userid`)"
 
+ " values (?, ?, ?, ?, ?)";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, name);
		 preparedStmt.setDouble(3, Double.parseDouble(price));
		 preparedStmt.setString(4, category);
		 preparedStmt.setString(5, uid);
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 String newItems = readItems();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newItems + "\"}";
		 }
		 catch (Exception e)
		 {
		 output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 }
		public String updateItem(String ID, String price, String name, String category,
		  String uid)
		 {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {
		 return "Error while connecting to the database for updating.";
		 }
		 // create a prepared statement
		 String query = "UPDATE item SET itemName=?,itemPrice=?,category=?,userid=? WHERE itemID=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setString(1, name);
		 preparedStmt.setDouble(2, Double.parseDouble(price));
		 preparedStmt.setString(3, category);
		 preparedStmt.setString(4, uid);
		 preparedStmt.setInt(5, Integer.parseInt(ID)); 
		// execute the statement
		 preparedStmt.execute();
		 con.close();
		 String newItems = readItems();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newItems + "\"}";
		 }
		 catch (Exception e)
		 {
		 output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 }
		public String deleteItem(String itemID)
		 {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {
		 return "Error while connecting to the database for deleting.";
		 }
		 // create a prepared statement
		 String query = "delete from item where itemID=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(itemID));
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 String newItems = readItems();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newItems + "\"}";
		 }
		 catch (Exception e)
		 {
		 output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 }
		}