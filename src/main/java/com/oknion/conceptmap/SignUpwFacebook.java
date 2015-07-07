package com.oknion.conceptmap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.oknion.conceptmap.DAO.MyUserDetails;
import com.oknion.conceptmap.Model.User;
import com.oknion.conceptmap.services.UserService;
import com.oknion.conceptmap.utils.AmazonUtils;

@Controller
public class SignUpwFacebook {

	private UserService userService;

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(WebRequest request, HttpSession session) {
		Connection<?> connection = ProviderSignInUtils.getConnection(request);
		if (connection != null) {

			// if (user == null)
			// throw new UsernameNotFoundException("User name not found");
			// List<GrantedAuthority> authorities = new
			// ArrayList<GrantedAuthority>();
			// SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
			// ("ROLE_" + user.getRole().toUpperCase().trim()).trim());
			// authorities.add(authority);
			// MyUserDetails userDetail = new MyUserDetails(user.getUserId(),
			// user.getPassword(), authorities, user.getS3bucketId());
			// Authentication authentication = new
			// UsernamePasswordAuthenticationToken(
			// userDetail, null, userDetail.getAuthorities());
			// SecurityContextHolder.getContext()
			// .setAuthentication(authentication);
			// ProviderSignInUtils.handlePostSignUp(user.getUserId(), request);
			return "registerFromFacebook";

		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signupForm(WebRequest request,
			@RequestParam(value = "studentId") String studentId,
			@RequestParam(value = "fullName") String fullName,
			@RequestParam(value = "classes") String classes,
			@RequestParam(value = "khoa") String khoa, HttpSession session) {
		Connection<?> connection = ProviderSignInUtils.getConnection(request);

		User user = fromProviderUser(connection.fetchUserProfile());
		user.setUserId(connection.getKey().getProviderUserId());
		user.setMssv(studentId);
		user.setFullName(fullName);
		user.setClasses(userService.getClasses(classes));
		user.setKhoa(khoa);
		userService.addUser(user);

		if (user == null)
			throw new UsernameNotFoundException("User name not found");
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
				("ROLE_" + user.getRole().toUpperCase().trim()).trim());
		authorities.add(authority);
		MyUserDetails userDetail = new MyUserDetails(user.getUserId(),
				user.getPassword(), authorities, user.getS3bucketId());
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userDetail, null, userDetail.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		ProviderSignInUtils.handlePostSignUp(user.getUserId(), request);
		return "redirect:listmap";

	}

	public static User fromProviderUser(UserProfile providerUser) {
		User user = new User();
		System.out.println(providerUser.getFirstName());
		System.out.println(providerUser.getLastName());
		System.out.println(providerUser.getUsername());
		System.out.println(providerUser.getEmail());

		System.out.println(providerUser.getName());

		user.setUserId(providerUser.getFirstName() + providerUser.getLastName());
		user.setEmail(providerUser.getFirstName() + providerUser.getLastName());
		user.setFullName(providerUser.getFirstName() + " "
				+ providerUser.getLastName());
		boolean temp = false;
		do {
			user.setS3bucketId(UUID.randomUUID().toString());
			temp = AmazonUtils.createBucket(AmazonUtils.CLIENT,
					user.getS3bucketId());
		} while (!temp);

		return user;
	}
}
