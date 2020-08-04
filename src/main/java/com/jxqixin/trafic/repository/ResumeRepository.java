package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Resume;

import java.io.Serializable;

public interface ResumeRepository<ID extends Serializable> extends CommonRepository<Resume,ID> {
}
