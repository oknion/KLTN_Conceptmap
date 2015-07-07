package com.oknion.conceptmap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

public class SimpleSocialUserDetailsService implements SocialUserDetailsService {

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	public SimpleSocialUserDetailsService(
			MyUserDetailsService myUserDetailsService) {
		this.myUserDetailsService = myUserDetailsService;
	}

	@Override
	public SocialUserDetails loadUserByUserId(String arg0)
			throws UsernameNotFoundException, DataAccessException {
		System.out.println("get user by username:" + arg0);

		UserDetails userDetails = myUserDetailsService.loadUserByUsername(arg0);

		return (SocialUserDetails) userDetails;
	}

}
