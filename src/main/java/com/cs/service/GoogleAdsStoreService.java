package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.dto.GoogleAdsSyncResultDto;
import com.cs.entity.GoogleAdsStore;

import java.util.List;

/**
 * Google Ads 连接配置服务
 *
 * @author LivePulse
 */
public interface GoogleAdsStoreService extends IService<GoogleAdsStore> {

    /**
     * 查找所有启用的 Google Ads 连接
     *
     * @return 启用的 Google Ads 连接列表
     */
    List<GoogleAdsStore> findEnabled();

    /**
     * 手动同步客户匹配数据
     *
     * @param storeId Google Ads 连接 ID
     * @return 同步结果
     */
    GoogleAdsSyncResultDto manualSyncCustomerMatch(Long storeId);
}
