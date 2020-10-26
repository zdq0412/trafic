package com.jxqixin.trafic.config;

import com.jxqixin.trafic.constant.OriginIp;
import com.jxqixin.trafic.filters.VerifyCodeFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.ArrayList;
import java.util.List;

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
	@Autowired
	private VerifyCodeFilter verifyCodeFilter;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//在用户名密码认证过滤器前面添加登录验证码过滤器
		http.addFilterBefore(verifyCodeFilter, UsernamePasswordAuthenticationFilter.class);
		//解决跨域访问问题
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
		registry.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();//让Spring security放行所有preflight
		registry.antMatchers("/**").permitAll();
		http.formLogin().
				successHandler(customizeAuthenticationSuccessHandler)
				.failureHandler(customizeAuthenticationFailHandler).and()
				.logout().logoutSuccessHandler(customizeLogoutSuccessHandler).and()
				.authorizeRequests().anyRequest().authenticated()
		.and().csrf().disable().cors().configurationSource(CorsConfigurationSource());
		http.headers().frameOptions().sameOrigin();
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
	}
	//配置跨域访问资源,解决在header中添加token，springsecurity失效问题
	private CorsConfigurationSource CorsConfigurationSource() {
		CorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		List<String> list = new ArrayList<>();
		list.add(OriginIp.WEB_IP);
		list.add(OriginIp.SERVER_IP);
		corsConfiguration.setAllowedOrigins(list);
		corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
		corsConfiguration.addAllowedMethod("*");	//允许的请求方法，PSOT、GET等
		corsConfiguration.setAllowCredentials(true);
		((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**",corsConfiguration); //配置允许跨域访问的url
		return source;
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userInfoService)
				.passwordEncoder(new BCryptPasswordEncoder());
	}
}
