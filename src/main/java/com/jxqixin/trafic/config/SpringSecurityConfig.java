package com.jxqixin.trafic.config;

import com.jxqixin.trafic.handler.LoginFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsUtils;
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	@Qualifier("userInfoService")
	private UserDetailsService userInfoService;
@Autowired
	private LoginFailureHandler loginFailureHandler;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.formLogin()
		.loginPage("/authentication/requireAuthenticate.do")
		.loginProcessingUrl("/login")//.successHandler(successHandler)
				.failureHandler(loginFailureHandler)
		.defaultSuccessUrl("/console")
		//.defaultSuccessUrl("/resume/console.html")
		.and().logout().logoutUrl("/logout").logoutSuccessUrl("/index.html")
		.and().authorizeRequests()
		.antMatchers("/authentication/requireAuthenticate.do").permitAll()
				.antMatchers("/lib/**").permitAll()
				.antMatchers("/index.html","/index.html?error=*","/").permitAll()
		.anyRequest().authenticated()
		.and().csrf().disable().cors();
		http.headers().frameOptions().sameOrigin();
		//解决跨域访问问题
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests(); 
		registry.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();//让Spring security放行所有preflight
		registry.antMatchers("/**").permitAll();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userInfoService)
				.passwordEncoder(new BCryptPasswordEncoder());
	}
}
