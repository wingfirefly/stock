package vip.linhs.stock.service;

import java.util.List;

import vip.linhs.stock.model.po.SystemConfig;

public interface SystemConfigService {

    boolean isMock();

    boolean isApplyNewConvertibleBond();

    boolean isCr();

    List<SystemConfig> getAll();

}
