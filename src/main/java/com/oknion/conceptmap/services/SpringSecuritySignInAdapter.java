package com.oknion.conceptmap.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import com.oknion.conceptmap.DAO.MyUserDetails;
import com.oknion.conceptmap.Model.User;

public class SpringSecuritySignInAdapter implements SignInAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(SpringSecuritySignInAdapter.class);
	private UserService userService;

	@Autowired
	public SpringSecuritySignInAdapter(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String signIn(String userId, Connection<?> connection,
			NativeWebRequest request) {
		System.out.println("inside SpringSecuritySignInAdapter,userId:"
				+ userId);

		if (userService == null) {
			System.out.println("userservice == null");
		}
		User user = userService.getUser(userId);
		if (user == null)

			throw new UsernameNotFoundException("User name not found");
		else {
			System.out.println("get user success");
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
					("ROLE_" + user.getRole().toUpperCase().trim()).trim());
			authorities.add(authority);
			MyUserDetails userDetail = new MyUserDetails(user.getUserId(),
					user.getPassword(), authorities, user.getS3bucketId());
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					userDetail, null, userDetail.getAuthorities());
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);
		}
		return null;
	}
}
