package com.restservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.restservice.dao.UserDAO;
import com.restservice.resource.model.User;
import com.restservice.security.JwtTokenUtil;

/**
 * The type User service.
 */
@Component
public class UserService {

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public User createUser(User user) {
    	return userDAO.saveUser(user);
        //return user;
    }
    
    public List<User> getUsers() {
        return userDAO.getUsersList();
    }

	public Long ValidateLogin(User user) {
		return userDAO.checkLogin(user);
	}

	public String checkAuthentication(String token, String id) {
		if(id.equalsIgnoreCase(jwtTokenUtil.validateTokenWithClientId(token)) && userDAO.updateLogoutUser(id)){
			//userDAO.updateLogoutUser(id);
			id="Ok";
		}else{
			id="401";
		}
		return id;
		
	}

	public List<User> getLoginUsers() {
		return userDAO.getLoginUsers();
	}

	public String checkAuthenticationExpiry(String token, String id) {
		if(id.equalsIgnoreCase(jwtTokenUtil.validateTokenWithClientId(token)) && userDAO.checkLogoutUser(id)){
			id="Ok";
		}else{
			id="401";
		}
		return id;
	}
	
	
}
