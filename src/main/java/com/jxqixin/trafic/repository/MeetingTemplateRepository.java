package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.MeetingTemplate;

import java.io.Serializable;

public interface MeetingTemplateRepository<ID extends Serializable> extends CommonRepository<MeetingTemplate,ID> {
    MeetingTemplate findByName(String name);
}
