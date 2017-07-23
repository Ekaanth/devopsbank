package com.userfront.config;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.userfront.backend.service.UserSecurityService;
import com.userfront.web.controller.SignUpController;

/**
 * Created by root on 01/07/17.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private UserSecurityService userSecurityService;

    /** The encryption SALT. */
    private static final String SALT = "ddlsalt";

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }

    /** Public URLs */
    private static final String [] PUBLIC_MATCHES = {
            // Spring Security protects everything, so we need to be very precise on what
            // we wish to make public
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/",
            "/about/**",
            "/contact/**",
            "/errors/**/*",
            "/console/**",
            SignUpController.SIGNUP_URL_MAPPING,
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Getting the profiles from application.properties
        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        /** Block that disable csrf and frame options for the H2 console to work correctly */
        if (activeProfiles.contains("dev")) {
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }

        http
        		.authorizeRequests()
                .antMatchers(PUBLIC_MATCHES)
                .permitAll().anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/index").defaultSuccessUrl("/userFront")
                .failureUrl("/index?error").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index?logout").deleteCookies("remember-me").permitAll()
                .and()
                .csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .rememberMe();
                
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        /** In Memory authentication -->>
         auth
         .inMemoryAuthentication()
         .withUser("user").password("password")
         .roles("USER"); */

        auth
                .userDetailsService(userSecurityService)
                .passwordEncoder(passwordEncoder());
    }
    
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//            .allowedOrigins("http://domain2.com")
//            .allowedMethods("PUT", "DELETE")
//            .allowedHeaders("header1", "header2", "header3")
//            .exposedHeaders("header1", "header2")
//            .allowCredentials(false).maxAge(3600);
//    }
    
}

