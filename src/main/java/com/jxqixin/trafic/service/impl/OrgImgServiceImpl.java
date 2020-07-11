package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.OrgImg;
import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.repository.OrgImgRepository;
import com.jxqixin.trafic.service.IOrgImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrgImgServiceImpl extends CommonServiceImpl<OrgImg> implements IOrgImgService {
	@Autowired
	private OrgImgRepository orgImgRepository;
	@Override
	public CommonRepository getCommonRepository() {
		return orgImgRepository;
	}

	@Override
	public void deleteById(String id) {
		orgImgRepository.deleteById(id);
	}

	@Override
	public List<OrgImg> findAll(Org org) {
		if(org==null){
			return new ArrayList<>();
		}
		return orgImgRepository.findByOrgId(org.getId());
	}

	@Override
	public void saveAll(List<OrgImg> orgImgList) {
		orgImgRepository.saveAll(orgImgList);
	}
}
