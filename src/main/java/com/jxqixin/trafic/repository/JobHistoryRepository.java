package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.JobHistory;

import java.io.Serializable;

public interface JobHistoryRepository<ID extends Serializable> extends CommonRepository<JobHistory,ID> {
}
