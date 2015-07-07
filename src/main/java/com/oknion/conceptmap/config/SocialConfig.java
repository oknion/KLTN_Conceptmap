package com.oknion.conceptmap.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import com.oknion.conceptmap.services.SpringSecuritySignInAdapter;
import com.oknion.conceptmap.services.UserService;

@Configuration
@EnableSocial
@PropertySource("/WEB-INF/application.properties")
public class SocialConfig implements SocialConfigurer {
	@Autowired
	private DataSource dataSource;
	@Autowired
	Environment env;
	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(SocialConfig.class);

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig,
			Environment env) {
		// System.out.println(env.getProperty("facebook.app.id"));
		logger.info("addConnectionFactories");
		cfConfig.addConnectionFactory(new FacebookConnectionFactory(
				"784736944955285", "dc7ab81a3514c29041e19ee7bac92030"));
	}

	@Override
	public UserIdSource getUserIdSource() {
		System.out.println("getUserIdSource				");
		logger.info("getUserIdSource");
		return new AuthenticationNameUserIdSource();
		// return new UserIdSource() {
		// @Override
		// public String getUserId() {
		// Authentication authentication = SecurityContextHolder
		// .getContext().getAuthentication();
		// if (authentication == null) {
		// throw new IllegalStateException(
		// "Unable to get a ConnectionRepository: no user signed in");
		// }
		// return authentication.getName();
		// }
		// };
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(
			ConnectionFactoryLocator connectionFactoryLocator) {
		System.out.println(" UsersConnectionRepository");
		logger.info("UsersConnectionRepository");
		return new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator, Encryptors.noOpText());
	}

	// @Bean
	// public ConnectController connectController(
	// ConnectionFactoryLocator connectionFactoryLocator,
	// ConnectionRepository connectionRepository) {
	// ConnectController connectController = new ConnectController(
	// connectionFactoryLocator, connectionRepository);
	// System.out.println(env.getProperty("applicationUrl"));
	// System.out.println(connectController == null);
	//
	// // connectController.setApplicationUrl("http://vnexpress.com");
	// return connectController;
	// }

	// @Bean
	// @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	// public ConnectionRepository connectionRepository() {
	// Authentication authentication = SecurityContextHolder.getContext()
	// .getAuthentication();
	// if (authentication == null) {
	// throw new IllegalStateException(
	// "Unable to get a ConnectionRepository: no user signed in");
	// }
	// return usersConnectionRepository().createConnectionRepository(
	// authentication.getName());
	// }

	@Bean
	@Scope(value = "singleton", proxyMode = ScopedProxyMode.INTERFACES)
	public UsersConnectionRepository usersConnectionRepository() {
		System.out.println(" UsersConnectionRepository");
		logger.info("UsersConnectionRepository");
		return new JdbcUsersConnectionRepository(dataSource,
				connectionFactoryLocator(), Encryptors.noOpText());
	}

	@Bean
	@Scope(value = "singleton", proxyMode = ScopedProxyMode.INTERFACES)
	public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
		System.out.println(" ConnectionFactoryLocator");
		logger.info("ConnectionFactoryLocator");
		registry.addConnectionFactory(new FacebookConnectionFactory(
				"784736944955285", "dc7ab81a3514c29041e19ee7bac92030"));

		return registry;
	}

	@Bean
	public ProviderSignInController providerSignInController() {
		ProviderSignInController providerSignInController = new ProviderSignInController(
				connectionFactoryLocator(), usersConnectionRepository(),
				signInAdapter(userService));
		System.out.println("provider sign controller");
		logger.info("provider sign controller");
		providerSignInController.setPostSignInUrl("/listmap");

		return providerSignInController;
	}

	@Bean
	public SignInAdapter signInAdapter(UserService userService) {
		return new SpringSecuritySignInAdapter(userService);
	}

}
