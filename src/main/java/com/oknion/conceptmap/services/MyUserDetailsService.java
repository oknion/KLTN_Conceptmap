package com.oknion.conceptmap.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oknion.conceptmap.DAO.MyUserDetails;
import com.oknion.conceptmap.DAO.UserDAO;
import com.oknion.conceptmap.Model.User;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
	private UserDAO userDao;

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		System.out.println("Before get user in MyUserdetailsService");
		User user = userDao.getUser(userName);
		if (user == null)
			throw new UsernameNotFoundException("User name not found");
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
				("ROLE_" + user.getRole().toUpperCase().trim()).trim());
		authorities.add(authority);
		MyUserDetails userDetail = new MyUserDetails(userName,
				user.getPassword(), authorities, user.getS3bucketId());
		System.out.println("Create userDetail");
		return userDetail;
	}

}
