package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IAreaManagerService;
import com.jxqixin.trafic.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service("userInfoService")
@Transactional
public class UserInfoService implements UserDetailsService {
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	@Autowired
	@Qualifier("areaManagerService")
	private IAreaManagerService areaManagerService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.queryUserByUsername(username);
		List<GrantedAuthority> list = new ArrayList<>();
		List<Object[]> powers = userService.queryPowersByUsername(username);
		if(powers!=null && powers.size()>0) {
			for(Object[] obj : powers) {
				list.add(new SimpleGrantedAuthority(obj[0]+""));
			}
		}
		UserDetails ud = new org.springframework.security.core.userdetails.User(username, user.getPassword(), list);
		return ud;
	}
}
