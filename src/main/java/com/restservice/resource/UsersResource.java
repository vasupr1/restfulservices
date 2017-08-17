package com.restservice.resource;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.restservice.resource.model.User;
import com.restservice.security.JwtTokenUtil;
import com.restservice.service.UserService;

/**
 * The type Users resource.
 */
@Path("v1")
@RestController
public class UsersResource {
  private static final Logger LOG = LoggerFactory.getLogger(UsersResource.class);
  private static final String AUTHORIZATION = "Authorization";

  @Autowired
  private UserService userService;
  
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  
  @Value("${jwt.secret}")
  private String secret;

  public UsersResource() {
  //NEED
  }
  
  UsersResource( String secret) {
      this.secret = secret;
  }
  
  /**
   * add the user
   *   
   */
  @PUT
  @Path("/api/user/add")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @RequestMapping(method = RequestMethod.PUT, value = "/api/user/add", produces = "application/json")
  public Response addUser(@Valid @NotNull @RequestBody User user) {
    if(user.getUsername()!=null && user.getPassword()!=null && userService.createUser(user)!=null)
    	return Response.ok().entity(user).build();
    else
    	return	Response.status(400).entity("user already exists or username or pwd empty").build();
  }
  
  /**
   * get user returns the list
   */
  @GET
  @Path("/api/users")
  @Produces(MediaType.APPLICATION_JSON)
  @RequestMapping(method = RequestMethod.GET, value = "/api/users", produces = MediaType.APPLICATION_JSON)
  public List<User> getUsers() {
    return userService.getUsers();
  }
  
  /**
   * getLoginUsers returns the list of login users within last 5min
   */
  @GET
  @Path("/api/users/loginCount")
  @Produces(MediaType.APPLICATION_JSON)
  @RequestMapping(method = RequestMethod.GET, value = "/api/users/loginCount", produces = MediaType.APPLICATION_JSON)
  public List<User> getLoginUsers() {
    return userService.getLoginUsers();
  }

  /**
   * user long in.
   * @return list list
   */
  @POST
  @Path("/api/user/login")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @RequestMapping(method = RequestMethod.POST, value = "/api/user/login", produces = MediaType.APPLICATION_JSON)
  public Response loingUser(@Valid @NotNull @RequestBody User user) {
	String token=user.getUsername();
    Long id=userService.ValidateLogin(user);
    if(id!=null){
		token=jwtTokenUtil.generateToken(""+id, user.getUsername());
		user=new User();
		user.setId(id);
		user.setToken(token);
    }else{
    	return	Response.status(400).entity("login failed").type("application/json").build();
    }
    return Response.ok().entity(user).build();
  }

  /**
   * user logout
  */
  @POST
  @Path("/api/user/logout/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @RequestMapping(method = RequestMethod.POST, value = "/api/user/logout/{id}", produces = MediaType.APPLICATION_JSON)
  public Response logout(@PathParam("id") @PathVariable(name = "id") String id, @Valid @NotNull @RequestBody User user) {
    String token=user.getToken();
    String str=userService.checkAuthentication(token, id);
   if(str.equalsIgnoreCase("Ok")){
	   return Response.ok().build();
   }else{
	   return	Response.status(400).entity("token expired/logout unsuccessful").type("application/json").build();
   }
  }
  
  /**
   * user logout
  */
  @POST
  @Path("/api/user/check/expiry/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @RequestMapping(method = RequestMethod.POST, value = "/api/user/check/expiry/{id}", produces = MediaType.APPLICATION_JSON)
  public Response checkExpiry(@PathParam("id") @PathVariable(name = "id") String id, @Valid @NotNull @RequestBody User user) {
    String token=user.getToken();
    String str=userService.checkAuthenticationExpiry(token, id);
   if(str.equalsIgnoreCase("Ok")){
	   return Response.ok().build();
   }else{
	   return	Response.status(400).entity("token expired/logout unsuccessful").type("application/json").build();
   }
  }
}