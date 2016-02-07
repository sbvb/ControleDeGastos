package project;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.Connection;

public class Hello {

	
	public String create_user(String username,String email,String password)
	{
		

	      // create a mysql database connection
	      String myDriver = "com.mysql.jdbc.Driver";
	      String myUrl = "jdbc:mysql://localhost/mydb";
	      try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	      Connection conn;
		try {
		  conn = DriverManager.getConnection(myUrl, "root", "1234");
		
	      
		  String query_user_exists = "SELECT * from user where username = ? or email = ?";
		  
		  PreparedStatement preparedStmtExists = conn.prepareStatement(query_user_exists);
		  preparedStmtExists.setString (1, username);
		  preparedStmtExists.setString (2, email);
		  //preparedStmtExists.setString (3, password);
	 
	      // execute the preparedstatement
		  ResultSet result_exist = preparedStmtExists.executeQuery();
		  if (result_exist.next())
		  {                         
              //then there are at least one rows.
			  //System.out.println("No records found");
			  return "The user already exist!";
		  }
		  		
		  
		 
		  
		  // the mysql insert statement
	      String query = " insert into user (username,email,password)"
	        + " values (?, ?, ?)";
	 
	      // create the mysql insert preparedstatement
	      PreparedStatement preparedStmt = conn.prepareStatement(query);
	      preparedStmt.setString (1, username);
	      preparedStmt.setString (2, email);
	      preparedStmt.setString (3, password);
	 
	      // execute the preparedstatement
	      preparedStmt.execute();
	       
	      conn.close();
	      
	      return "OK";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String outReturn;
			return e.getMessage();
		}
		
		//return "ERROR (???)";
		//User  new_user = new User(username,email);		
		
		//return new_user;
	}
	
	public User get_user_info(String email,String password)
	{
		
			User user = null;
		// create a mysql database connection
	      String myDriver = "com.mysql.jdbc.Driver";
	      String myUrl = "jdbc:mysql://localhost/mydb";
	      try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return e.getMessage();
			return user;
		}
	      Connection conn;
		  try {
			conn = DriverManager.getConnection(myUrl, "root", "1234");
		
		
	      
		  String query_user_exists = "SELECT * from user where email = ? and password = ?";
		  
		  PreparedStatement preparedStmtExists = conn.prepareStatement(query_user_exists);
		  preparedStmtExists.setString (1, email);
		  preparedStmtExists.setString (2, password);
		  //preparedStmtExists.setString (3, password);
	 
	      // execute the preparedstatement
		  ResultSet result_exist = preparedStmtExists.executeQuery();
		  if (result_exist.next())
		  {                         
            //then there are at least one rows.
			  //System.out.println("No records found");
			 // return "The user already exist!";
			  
			  user = new User(result_exist.getString("username"),result_exist.getString("email"));
		  }
		  else
		  {
			  return user;
		  }
		
		  } 
		  
		  catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return user;
			}
		return user;

		
	}
	
	
	public String plus_a(String in)
	{
		return in + " a";
	}
	
	public ArrayList<String> plus_a_intensifies(String in, int n){
		ArrayList<String> str = new ArrayList<String>();
		StringBuilder strb = new StringBuilder("");
		for (int i=0; i<n; i++){
			String aux;
			strb.append(in + " a!\n");
			aux = strb.toString();
			str.add(aux);
		}
		
		return str;
	}
	
	
}
