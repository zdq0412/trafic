package com.jxqixin.trafic.common;

import com.jxqixin.trafic.dto.NameDto;
import org.springframework.data.jpa.domain.Specification;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过名称查找的Specification
 */
public class NameSpecification implements Specification {
    private NameDto nameDto;
    public NameSpecification(NameDto nameDto){
        this.nameDto = nameDto;
    }
    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> list = new ArrayList<>();

        if(!StringUtils.isEmpty(nameDto.getName())){
            list.add(criteriaBuilder.like(root.get("name"),"%"+nameDto.getName()+"%"));
        }

        Predicate[] predicates = new Predicate[list.size()];
        return criteriaBuilder.and(list.toArray(predicates));
    }
}
