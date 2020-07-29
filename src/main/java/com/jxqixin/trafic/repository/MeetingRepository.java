package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Meeting;

import java.io.Serializable;

public interface MeetingRepository<ID extends Serializable> extends CommonRepository<Meeting,ID> {
}
