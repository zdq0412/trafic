package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.common.NameSpecification;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.DirectoryFunctionsRepository;
import com.jxqixin.trafic.repository.DirectoryRepository;
import com.jxqixin.trafic.repository.CategoryRepository;
import com.jxqixin.trafic.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl extends CommonServiceImpl<Category> implements ICategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return categoryRepository;
	}
	@Override
	public List<Category> findAll(String type) {
		List<Category> list = categoryRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.and(criteriaBuilder.isNull(root.get("parent")),criteriaBuilder.equal(root.get("type"),"区域"));
			}
		});
		//构建树形结构
		if(!CollectionUtils.isEmpty(list)){
			list.forEach(category -> {
				buildChildren(category);
			});
		}
		return list;
	}
	@Override
	public Page findCategorys(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		if(!StringUtils.isEmpty(nameDto.getName())){
			return  categoryRepository.findAll(new NameSpecification(nameDto),pageable);
		}else{
			Page page = categoryRepository.findAll(new Specification() {
				@Override
				public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
					return criteriaBuilder.isNull(root.get("parent"));
				}
			},pageable);
			//构建树形结构
			if(page.getTotalElements()>0){
				List<Category> list = page.getContent();
				list.forEach(category -> {
					buildChildren(category);
				});
			}
			return page;
		}
	}

	/**
	 * 构建类别子类别
	 * @param category
	 */
	private void buildChildren(Category category){
		//根据id查找子类别
		List<Category> children = categoryRepository.findByParentId(category.getId());
		if(!CollectionUtils.isEmpty(children)){
			category.setChildren(children);
			children.forEach(c -> {
				buildChildren(c);
			});
		}else{
			return ;
		}
	}

	@Override
	public Category findByName(String name) {
		return categoryRepository.findByName(name);
	}

	@Override
	public void deleteById(String id) {
		//根据id查找子类别
		List<Category> children = categoryRepository.findByParentId(id);
		if(!CollectionUtils.isEmpty(children)){
			children.forEach(category -> {
				deleteById(category.getId());
			});
		}
		categoryRepository.deleteById(id);
	}
}
