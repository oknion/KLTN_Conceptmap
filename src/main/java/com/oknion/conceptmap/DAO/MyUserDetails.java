package com.oknion.conceptmap.DAO;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
	private String userName;
	private String password;
	private List<GrantedAuthority> authorities;
	private String s3bucketId;

	public String getS3bucketId() {
		return s3bucketId;
	}

	public MyUserDetails(String userName, String password,
			List<GrantedAuthority> authorities, String s3bucketId) {
		this.userName = userName;
		this.password = password;
		this.authorities = authorities;
		this.s3bucketId = s3bucketId;

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}