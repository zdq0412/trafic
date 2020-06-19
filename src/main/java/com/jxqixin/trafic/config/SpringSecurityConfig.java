package com.jxqixin.trafic.config;
import com.jxqixin.trafic.handler.CustomizeAuthenticationFailHandler;
import com.jxqixin.trafic.handler.CustomizeAuthenticationSuccessHandler;
import com.jxqixin.trafic.handler.CustomizeLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsUtils;
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	@Qualifier("userInfoService")
	private UserDetailsService userInfoService;
	@Autowired
	private CustomizeAuthenticationFailHandler customizeAuthenticationFailHandler;
	@Autowired
	private CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;
	@Autowired
	public AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private CustomizeLogoutSuccessHandler customizeLogoutSuccessHandler;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//解决跨域访问问题
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
		registry.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();//让Spring security放行所有preflight
		registry.antMatchers("/**").permitAll();

		http.formLogin().loginProcessingUrl("/user/login").
				successHandler(customizeAuthenticationSuccessHandler)
				.failureHandler(customizeAuthenticationFailHandler).and()
				.logout().logoutSuccessHandler(customizeLogoutSuccessHandler).and()
				.authorizeRequests().anyRequest().authenticated()
		.and().csrf().disable().cors();
		http.headers().frameOptions().sameOrigin();
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userInfoService)
				.passwordEncoder(new BCryptPasswordEncoder());
	}
}
