package com.mithun.db.mysql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.mithun.db.mysql.dao.RoleDao;
import com.mithun.db.mysql.dao.UserDao;
import com.mithun.db.mysql.entity.UserRole;
import com.mithun.db.mysql.entity.RestUser;
import com.mithun.db.mysql.model.ApplicationUser;

@Service
public class UserServiceImpl implements UserService {

	// need to inject user dao
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional("mysqlTransactionManager")
	public RestUser findByUserName(String userName) {
		// check the database if the user already exists
		return userDao.findByUserName(userName);
	}

	@Override
	@Transactional("mysqlTransactionManager")
	public void save(ApplicationUser appUser) {
		RestUser restUser = new RestUser();
		// assign user details to the user object
		restUser.setUserName(appUser.getUserName());
		restUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		restUser.setFirstName(appUser.getFirstName());
		restUser.setLastName(appUser.getLastName());
		restUser.setEmail(appUser.getEmail());

		// give user default role of "employee"
		restUser.setUserRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

		// save user in the database
		userDao.save(restUser);
	}

	@Override
	@Transactional("mysqlTransactionManager")
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		RestUser restUser = userDao.findByUserName(userName);
		if (restUser == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(restUser.getUserName(), restUser.getPassword(),
				mapRolesToAuthorities(restUser.getUserRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<UserRole> userRoles) {
		return userRoles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
