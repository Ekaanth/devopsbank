package com.userfront;

import com.userfront.backend.domain.User;
import com.userfront.backend.domain.security.Role;
import com.userfront.backend.service.UserService;
import com.userfront.enums.RolesEnum;
import com.userfront.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class UserfrontApplication implements CommandLineRunner {

	/** The application logger */
	private static final Logger LOG = LoggerFactory.getLogger(UserfrontApplication.class);

	@Value("${webmaster.username}")
	private String webmasterUsername; // gets username value from .properties file.

	@Value("${webmaster.password}")
	private String webmasterPassword; // gets password value from .properties file.

	@Value("${webmaster.email}")
	private String webmasterEmail; // gets email value from .properties file.

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(UserfrontApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		LOG.info("Creating Basic role in the database");

		User user = UserUtils.createUser(webmasterUsername, webmasterEmail);
		user.setPassword(webmasterPassword);

		Set<Role> roles = new HashSet<>();
		roles.add(new Role(RolesEnum.ADMIN));

		user.setRoles(roles);
		LOG.debug("Creating user with username: {}", user.getUsername());
		userService.create(user);
		LOG.info("User {} created", user.getUsername());

	}
}
