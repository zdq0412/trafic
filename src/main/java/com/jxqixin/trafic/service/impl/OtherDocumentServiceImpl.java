package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.constant.EmpArchiveType;
import com.jxqixin.trafic.dto.OtherDocumentDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.OtherDocument;
import com.jxqixin.trafic.model.Resume;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.OtherDocumentRepository;
import com.jxqixin.trafic.service.IEmployeeService;
import com.jxqixin.trafic.service.IOtherDocumentService;
import com.jxqixin.trafic.util.StringUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class OtherDocumentServiceImpl extends CommonServiceImpl<OtherDocument> implements IOtherDocumentService {
	@Autowired
	private OtherDocumentRepository otherDocumentRepository;
	@Autowired
	private IEmployeeService employeeService;
	@Override
	public CommonRepository getCommonRepository() {
		return otherDocumentRepository;
	}
	@Override
	public Page findOtherDocuments(OtherDocumentDto otherDocumentDto) {
		Pageable pageable = PageRequest.of(otherDocumentDto.getPage(),otherDocumentDto.getLimit(), Sort.Direction.DESC,"createDate");
		return otherDocumentRepository.findAll(new Specification() {
			@Override
			public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				list.add(criteriaBuilder.equal(root.get("deleted"),false));
				if(!StringUtils.isEmpty(otherDocumentDto.getEmpId())){
					Join<OtherDocument, Employee> employeeJoin = root.join("employee",JoinType.INNER);
					list.add(criteriaBuilder.equal(employeeJoin.get("id"),otherDocumentDto.getEmpId()));
				}
				Predicate[] predicates = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(predicates));
			}
		}, pageable);
	}
	@Override
	public void deleteById(String id) {
		OtherDocument otherDocument = (OtherDocument) otherDocumentRepository.findById(id).get();
		otherDocument.setDeleted(true);
		otherDocumentRepository.save(otherDocument);

		setArchiveCode(otherDocument);
	}
	@Override
	public void addOtherDocument(OtherDocument otherDocument) {
		addObj(otherDocument);
		setArchiveCode(otherDocument);
	}
	/**
	 * 设置档案码
	 * @param otherDocument
	 */
	private void setArchiveCode(OtherDocument otherDocument){
		Long count = otherDocumentRepository.count((root, criteriaQuery, criteriaBuilder) -> {
			Join<Resume,Employee> employeeJoin = root.join("employee",JoinType.INNER);
			return criteriaBuilder.and(criteriaBuilder.equal(root.get("deleted"),false),criteriaBuilder.equal(employeeJoin.get("id"),otherDocument.getEmployee().getId()));
		});
		if(count == null)count = 0l;
		Employee employee = employeeService.queryObjById(otherDocument.getEmployee().getId());
		employee.setArchiveCode(StringUtil.handleEmpArchiveCode(EmpArchiveType.OTHERDOCUMENT,employee.getArchiveCode(),count.intValue()));
		employeeService.updateObj(employee);
	}
}
