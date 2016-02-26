package com.dmitry.eventsaround.services;

import com.dmitry.eventsaround.db.dao.UserDAO;
import com.dmitry.eventsaround.db.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service is an object in the database for the login,
 * and returns the user to verify the authorization
 */
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	@Qualifier(value="userDAO")
	private UserDAO userDAO;

	//find user by login, and build authorities
	@Override
	public UserDetails loadUserByUsername(final String userLogin) throws UsernameNotFoundException {
		com.dmitry.eventsaround.db.entities.User user = userDAO.findByLogin(userLogin);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
		return buildUserForAuthentication(user, authorities);
	}
	//return found the user to verify the authorization
	private User buildUserForAuthentication(com.dmitry.eventsaround.db.entities.User user, List<GrantedAuthority> authorities) {
		return new User(user.getLogin(), user.getPassword(), true, true, true, true, authorities);
	}

	// Build user's authorities
	private List<GrantedAuthority> buildUserAuthority(Role userRoles) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority(userRoles.getRole()));
		return new ArrayList<GrantedAuthority>(setAuths);
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}