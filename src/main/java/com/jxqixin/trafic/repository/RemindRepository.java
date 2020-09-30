package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.Remind;
import java.io.Serializable;
/**
 * 过期提醒repository
 * @param <ID>
 */
public interface RemindRepository<ID extends Serializable> extends CommonRepository<Remind,ID> {
}
