package com.restservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.sql.DriverManager;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	private UserDetailsService userDetailsService;

	public WebSecurityConfig() {
		//need
	}

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
				.antMatchers("/health").permitAll()
				.antMatchers("/info").permitAll()
				.antMatchers("/trace").permitAll()
				.antMatchers("/mappings").permitAll()
				.antMatchers("/metrics").permitAll()

				.antMatchers("/restservice/v1/api/user*/**").permitAll()
				.antMatchers("/restservice/v1/api/user/").permitAll()
				.antMatchers("/restservice/v1/api/user").permitAll()
				.antMatchers("/restservice/v1/api/user/add").permitAll()
				
				.antMatchers("/restservice/v1/api/user/logout/**").permitAll()
				.antMatchers("/restservice/v1/api/user/login/").permitAll()
				.antMatchers("/restservice/v1/api/user/login").permitAll()
				.antMatchers("/restservice/v1/api/users/loginCount").permitAll()
				
				.antMatchers("/restservice/v1/users*/**").permitAll()
				
				.antMatchers("https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js").permitAll()
				
				.antMatchers("/login.html").permitAll()
				.antMatchers("/logout.html").permitAll()
				.antMatchers("/home.html").permitAll()
				.antMatchers("/userList.html").permitAll()
				.antMatchers("/UsersList.html").permitAll()
				
				.antMatchers("/loginUsersCount.html").permitAll()
				.antMatchers("/loginUsersCount.js").permitAll()
				
				.antMatchers("/*.html").permitAll()
				.antMatchers("/hello.js").permitAll()
				.antMatchers("/static/hello.js").permitAll()
				.antMatchers("/static/login.view.html").permitAll()
				
				.antMatchers("/hello.js").permitAll()
				.antMatchers("/addUser.html").permitAll()
				.antMatchers("/addUser.js").permitAll()
				
				.antMatchers("/getUsers.js").permitAll()
				.antMatchers("/getUsers.html").permitAll()
				
				.antMatchers("com.mysql.jdbc.Driver").permitAll()
				.antMatchers("jdbc:mysql://localhost:3306/restservice").permitAll()
				
				//Class.forName("com.mysql.jdbc.Driver");  
				//con=DriverManager.getConnection("jdbc:mysql://localhost:3306/restservice","root","root");
				
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.antMatchers("/**").authenticated()
		.and()
		.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
		.headers().cacheControl();
		}

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService);
    }

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Collection<? extends GrantedAuthority> authorities  = Collections.EMPTY_LIST;
				return new User(username, "",  authorities);
			}
		};
	}
}