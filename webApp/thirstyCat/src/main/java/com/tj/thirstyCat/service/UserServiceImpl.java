package com.tj.thirstyCat.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tj.thirstyCat.model.Role;
import com.tj.thirstyCat.model.User;
import com.tj.thirstyCat.model.UserRegistration;
import com.tj.thirstyCat.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}
	
	public User save(UserRegistration registration) {
		User user = new User();
		user.setUsername(registration.getUsername());
		user.setPassword(encoder.encode(registration.getPassword()));
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		return userRepository.save(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		//If user doesn't exist, throw exception
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	//TODO: Following tutorial at https://www.javaguides.net/2018/11/spring-boot-spring-mvc-spring-security-hibernate-mysql-tutorial.html
	//and don't quite understand everything going on in this method - need to read up on authorities, lambda functions
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
