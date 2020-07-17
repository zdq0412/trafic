package com.jxqixin.trafic.service.impl;
import com.jxqixin.trafic.common.NameSpecification;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Law;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.LawRepository;
import com.jxqixin.trafic.service.ILawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
public class LawServiceImpl extends CommonServiceImpl<Law> implements ILawService {
	@Autowired
	private LawRepository lawRepository;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy");
	@Override
	public CommonRepository getCommonRepository() {
		return lawRepository;
	}
	@Override
	public Page findLaws(NameDto nameDto) {
		Pageable pageable = PageRequest.of(nameDto.getPage(),nameDto.getLimit());
		return lawRepository.findAll(new NameSpecification(nameDto),pageable);
	}
	@Override
	public void deleteById(String id) {
		lawRepository.deleteById(id);
	}
	@Override
	public void addLaw(Law law, Org org) {
		String num = law.getNum();
		String maxNum = "";
		//查找最大发文字号
		if(org == null){
			maxNum = lawRepository.findMaxNumWhereOrgIdIsNull();
		}else{
			maxNum = lawRepository.findMaxNumByOrgId(org.getId());
		}
		String newNum = generateNewNum(num,maxNum);
		law.setNum(newNum);

		lawRepository.save(law);
	}
	/**
	 * 根据企业简称生成新的发文字号
	 * @param num 企业简称
	 * @return
	 */
	private String generateNewNum(String num,String maxNum) {
		Date now = new Date();
		String currentYear = format.format(now);
		if(StringUtils.isEmpty(maxNum)){
			if(StringUtils.isEmpty(num)){
				return currentYear + "0001";
			}else{
				return num + currentYear + "0001";
			}
		}else{
			//截取后倒数第八位到倒数第四位
			String year = maxNum.substring(maxNum.length()-8,maxNum.length()-4);
			if(currentYear.compareTo(year)>0){
				//截取后四位加一
				String last4 = maxNum.substring(maxNum.length()-4);
				int intNum = Integer.parseInt(last4) + 1;
				String strNum = String.valueOf(intNum);
				switch (strNum.length()){
					case 1:{
						last4 = "000" + strNum;
						break;
					}
					case 2:{
						last4 = "00" + strNum;
						break;
					}
					case 3:{
						last4 = "0" + strNum;
						break;
					}case 4:{
						last4 = strNum;
						break;
					}
				}
				return num==null?currentYear+last4:num+currentYear+last4;
			}else{
				return num==null?currentYear+"0001":num+currentYear+"0001";
			}
		}
	}

	public static void main(String[] args) {
		String year = "20200001";
		System.out.println(year.substring(year.length()-4));
	}
}
