package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.common.NameSpecification;
import com.jxqixin.trafic.constant.Status;
import com.jxqixin.trafic.dto.CategoryDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.repository.CategoryRepository;
import com.jxqixin.trafic.service.ICategoryService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@CacheConfig(cacheNames = "categoryCache")
public class CategoryServiceImpl /*extends CommonServiceImpl<Category>*/ implements ICategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	/*@Override
	public CommonRepository getCommonRepository() {
		return categoryRepository;
	}*/
	@Override
	@Cacheable(unless = "#result eq null")
	public List<Category> findAll(String type) {
		List<Category> list = categoryRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.and(criteriaBuilder.equal(root.get("deleted"),false),criteriaBuilder.isNull(root.get("parent")),criteriaBuilder.equal(root.get("type"),type));
			}
		});
		//构建树形结构
		if(!CollectionUtils.isEmpty(list)){
			list.forEach(category -> {
				buildAvailableChildren(category);
			});
		}
		return list;
	}

	@Override
	@CacheEvict(value = "categoryCache",allEntries = true)
	public void importCategory(List<Category> list) {
		if(CollectionUtils.isEmpty(list)){
			throw new RuntimeException("没有数据!");
		}
		list.forEach(category -> {
			Category c = categoryRepository.findByName(category.getName());
			if(c!=null){
				throw new RuntimeException("类别名称已经存在:" + category.getName());
			}
			Category parent = category.getParent();
			if(parent!=null){
				parent = categoryRepository.findByName(parent.getName());
				if(parent==null){
					parent = (Category) categoryRepository.save(parent);
				}
				category.setParent(parent);
			}
			categoryRepository.save(category);
		});
	}
	@Override
	@Cacheable(key = "#p0.name + '-' + #p0.limit + '-' + #p0.page")
	public Page findCategorys(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		if(!StringUtils.isEmpty(nameDto.getName())){
			return  categoryRepository.findAll(new Specification() {
				@Override
				public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
					List<Predicate> list = new ArrayList<>();
					//list.add(criteriaBuilder.equal(root.get("deleted"),false));
					if(!StringUtils.isEmpty(nameDto.getName())){
						list.add(criteriaBuilder.like(root.get("name"),"%"+nameDto.getName()+"%"));
					}
					Predicate[] predicates = new Predicate[list.size()];
					return criteriaBuilder.and(list.toArray(predicates));
				}
			}, pageable);
		}else{
			Page page = categoryRepository.findAll(new Specification() {
				@Override
				public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
					return criteriaBuilder.and(criteriaBuilder.isNull(root.get("parent")));
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
	/**
	 * 构建类别子类别
	 * @param category
	 */
	private void buildAvailableChildren(Category category){
		//根据id查找子类别
		List<Category> children = categoryRepository.findAvailableByParentId(category.getId());
		if(!CollectionUtils.isEmpty(children)){
			category.setChildren(children);
			children.forEach(c -> {
				buildAvailableChildren(c);
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
	@CacheEvict(value = "categoryCache",allEntries = true)
	public void deleteById(String id) {
		try {
			recycleDelete(id);
		}catch (DataIntegrityViolationException | ConstraintViolationException e){
			throw new RuntimeException("该记录已被其他数据引用,无法删除!");
		}
		//categoryRepository.deleteById(id);
	}
	@Override
	@CacheEvict(value = "categoryCache",allEntries = true)
	public void categoryStatus(CategoryDto categoryDto) {
		try {
			setStatus(categoryDto);
		}catch (DataIntegrityViolationException | ConstraintViolationException e){
			throw new RuntimeException("该记录已被其他数据引用,无法删除!");
		}
		//categoryRepository.deleteById(id);
	}
	/**
	 * 循环删除
	 * @param id
	 */
	private void recycleDelete(String id){
		//根据id查找子类别
		List<Category> children = categoryRepository.findByParentId(id);
		if(!CollectionUtils.isEmpty(children)){
			children.forEach(category -> {
				recycleDelete(category.getId());
			});
		}else{
			categoryRepository.deleteCategoryById(id);
		}
	}
	/**
	 * 设置删除状态
	 * @param categoryDto
	 */
	private void setStatus(CategoryDto categoryDto){
		//根据id查找子类别
		List<Category> children = categoryRepository.findByParentId(categoryDto.getId());
		if(!CollectionUtils.isEmpty(children)){
			children.forEach(category -> {
				CategoryDto dto = new CategoryDto();
				dto.setId(category.getId());
				dto.setOperType(categoryDto.getOperType());
				setStatus(dto);
			});
		}else{
			Category category = (Category) categoryRepository.findById(categoryDto.getId()).get();
			if(Status.PLAY.equals(categoryDto.getOperType())){
				category.setDeleted(false);
			}
			if(Status.PAUSE.equals(categoryDto.getOperType())){
				category.setDeleted(true);
			}
			categoryRepository.save(category);
		}
	}

	@Override
	@CacheEvict(value = "categoryCache",allEntries = true)
	public void updateObj(Category obj) {
		categoryRepository.save(obj);
	}

	@Override
	@CacheEvict(value = "categoryCache",allEntries = true)
	public Category addObj(Category obj) {
		return (Category) categoryRepository.save(obj);
	}

	@Override
	@Cacheable(key = "#id")
	public Category queryObjById(Serializable id) {
		return (Category) categoryRepository.findById(id).get();
	}

	@Override
	@CacheEvict(value = "categoryCache",allEntries = true)
	public void deleteObj(Serializable id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
}
