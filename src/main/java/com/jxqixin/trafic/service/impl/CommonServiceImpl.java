package com.jxqixin.trafic.service.impl;

import com.jxqixin.trafic.repository.CommonRepository;
import com.jxqixin.trafic.service.ICommonService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
@Service
@Transactional
public abstract  class CommonServiceImpl<T> implements ICommonService<T> {
    public abstract CommonRepository getCommonRepository();
    @Override
    public void updateObj(T obj) {
        getCommonRepository().save(obj);
    }

    @Override
    public T addObj(T obj) {
        return (T)getCommonRepository().save(obj);
    }

    @Override
    public T queryObjById(Serializable id) {
        return (T)getCommonRepository().findById(id).get();
    }

    @Override
    public void deleteObj(Serializable id) {
        getCommonRepository().deleteById(id);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return getCommonRepository().findAll(pageable);
    }

    @Override
    public Page<T> findAll(Pageable pageable, Example example) {
        return getCommonRepository().findAll(example,pageable);
    }
    @Override
    public List<T> findAll() {
        return getCommonRepository().findAll();
    }
}
