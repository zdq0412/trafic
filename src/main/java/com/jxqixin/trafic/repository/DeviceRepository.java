package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.Device;
import java.io.Serializable;
public interface DeviceRepository<ID extends Serializable> extends CommonRepository<Device,ID> {
}
