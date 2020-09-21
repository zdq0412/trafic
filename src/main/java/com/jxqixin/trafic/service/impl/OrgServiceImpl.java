package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.common.NameSpecification;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.Role;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.OrgRepository;
import com.jxqixin.trafic.repository.RoleRepository;
import com.jxqixin.trafic.repository.UserRepository;
import com.jxqixin.trafic.service.IOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@CacheConfig(cacheNames = "orgCache")
public class OrgServiceImpl /*extends CommonServiceImpl<Org>*/ implements IOrgService {
	/*@Value("${defaultPassword}")
	private String defaultPassword;*/
	@Autowired
	private OrgRepository orgRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Override
	public Page findOrgs(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return orgRepository.findAll(new NameSpecification(nameDto), pageable);
	}
	@Override
	public Org findByName(String name) {
		return orgRepository.findByName(name);
	}
	@Override
	public void deleteById(String id) {
		orgRepository.deleteById(id);
	}
	@Override
	public Org findByCode(String code) {
		return orgRepository.findByCode(code);
	}
	@Override
	@CacheEvict(value = "userCache",allEntries = true)
	public void addOrg(Org org) {
		//根据手机号查找用户
		User u = userRepository.findByUsername(org.getTel());
		if(u!=null){
			throw new RuntimeException("手机号已被使用!");
		}

		org = (Org) orgRepository.save(org);
		//添加企业管理员
		if(org.getOrgCategory()!=null){
			List<Role> roles = roleRepository.findByOrgCategoryId(org.getOrgCategory().getId());
			if(!CollectionUtils.isEmpty(roles)){
				Role role = roles.get(0);
				User user = new User();
				user.setRole(role);
				user.setUsername(org.getTel());
				//user.setPassword(new BCryptPasswordEncoder().encode(defaultPassword));
				user.setPassword(new BCryptPasswordEncoder().encode(org.getTel().substring(org.getTel().length()-6)));
				user.setCreateDate(new Date());
				user.setTel(org.getTel());
				user.setOrg(org);
				user.setStatus(User.DISABLED);
				userRepository.save(user);
			}
		}
	}

	@Override
	public String findFourColorPic(String id) {
		return orgRepository.findFourColorPic(id);
	}

	@Override
	@CacheEvict(value = {"userCache"},allEntries = true)
	public void updateObj(Org obj) {
		orgRepository.save(obj);
	}

	@Override
	public Org addObj(Org obj) {
		return (Org) orgRepository.save(obj);
	}

	@Override
	public Org queryObjById(Serializable id) {
		return (Org) orgRepository.findById(id).get();
	}

	@Override
	@CacheEvict(value = {"userCache"},allEntries = true)
	public void deleteObj(Serializable id) {
		orgRepository.deleteById(id);
	}

	@Override
	public Page<Org> findAll(Pageable pageable) {
		return orgRepository.findAll(pageable);
	}

	@Override
	public List<Org> findAll() {
		return orgRepository.findAll();
	}
}
