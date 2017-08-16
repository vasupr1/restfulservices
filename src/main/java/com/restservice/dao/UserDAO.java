package com.restservice.dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.restservice.resource.model.User;

@Component
public class UserDAO {

	@Value("${db_driver}")
	private String db_driver;
	
	@Value("${db_username}")
	private String db_username;
	
	@Value("${db_password}")
	private String db_password;
	
	@Value("${db_url}")
	private String db_url;
	
  public UserDAO() {
  }
  
  public User saveUser(User user) {
	  return addUser(user);
  }
  
  private boolean checkExistingUser(User user){
	 boolean ret=false;
	 Iterator ite=getUsersList().iterator();
	  while(ite.hasNext()){
		  User usr=(User) ite.next();
		  if(usr.getUsername().equalsIgnoreCase(user.getUsername())){
			try{
			  ret=true;
			  user.setId(usr.getId());
			  break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }
	  }
	  return ret;
  }
  
  private User addUser(User user) {
	  Connection con=null;
	  try {
		  if(!checkExistingUser(user)){
			  con=dbConnection();
		      String query = " insert into users (username, password, phone)"
		        + " values (?, ?, ?)";
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setString (1, user.getUsername());
		      preparedStmt.setString (2, user.getPassword());
		      preparedStmt.setString(3, user.getPhone());
		      preparedStmt.execute();
		  }else{
			  return null;
		  }
	} catch (SQLException e) {
		e.printStackTrace();
	} 
	finally{
		try {
			if(con!=null && !con.isClosed()){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return user;
  }
  
  public List<User> getUsersList() {
	Connection con=null;
	List<User> userslist = null;
	try {
		con=dbConnection();
		userslist = getUsers(con);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	finally{
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return userslist;
  }

  private Connection dbConnection(){
	  Connection con=null;
		try{  
			Class.forName(db_driver);  
			con=DriverManager.getConnection(db_url,db_username,db_password);  
			//con=DriverManager.getConnection("jdbc:mysql://localhost:3306/restservice","root","root");
			//here restservice is database name, root is username and password  
		}catch(Exception e){
			return null;
		}  
		return con;
  }
  
  private List<User> getUsers(Connection con) throws SQLException{
	List<User> userList=new ArrayList<User>();
	User user=new User();
	Statement stmt=con.createStatement();  
	ResultSet rs=stmt.executeQuery("select * from users");  
	while(rs.next()){
		user=new User();
		user.setId(rs.getLong(1));
		user.setUsername(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setPhone(rs.getString(4));
		userList.add(user);
	}
	
	return userList;
  }
  
  public Long checkLogin(User user) {
	  User usr=checkLoginUser(user);
	  if(usr!=null){
		
		  return usr.getId();
	  }
	  return null;
  }
private User checkLoginUser(User usr) {
	Statement stmt;
	Connection con=dbConnection();
	User user=null;
	try {
		stmt = con.createStatement();
		ResultSet rs=stmt.executeQuery("select * from users where username='"+usr.getUsername()+"' and password='"+usr.getPassword()+"'");  
		while(rs.next()){
			user=new User();
			user.setId(rs.getLong(1));
			user.setUsername(rs.getString(2));
			user.setPassword(rs.getString(3));
			user.setPhone(rs.getString(4));
			
			//update timestamp
			SimpleDateFormat formate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			PreparedStatement ps = con.prepareStatement("UPDATE users SET login = ?  WHERE username = ? ");
		    ps.setString(1,formate.format(new Date()));
		    ps.setString(2,usr.getUsername());
		    ps.executeUpdate();
		    ps.close();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}  
	finally{
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return user;
}

public List<User> getLoginUsers() {
	List<User> userList=new ArrayList<User>();
	Connection con=null;
	User user=new User();
	Statement stmt;
	try {
		con=dbConnection();
		stmt = con.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT username  FROM restservice.users WHERE TIMESTAMPDIFF(MINUTE, login, NOW()) <6");  
		while(rs.next()){
			user=new User();
			user.setUsername(rs.getString(1));
			userList.add(user);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally{
		try {
		con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return userList;
}
  
}
