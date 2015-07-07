package com.oknion.conceptmap.Model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

public class SocialUserDetail extends SocialUser {

	public SocialUserDetail(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO Auto-generated constructor stub
	}

	private String s3bucketId;

	public String getS3bucketId() {
		return s3bucketId;
	}

	public void setS3bucketId(String s3bucketId) {
		this.s3bucketId = s3bucketId;
	}

}
