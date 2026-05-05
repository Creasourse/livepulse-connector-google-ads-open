package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.dto.CustomerMatchDataDto;
import com.cs.entity.GoogleAdsCustomerMatch;

import java.util.List;

/**
 * Google Ads 客户匹配数据服务
 *
 * @author LivePulse
 */
public interface GoogleAdsCustomerMatchService extends IService<GoogleAdsCustomerMatch> {

    /**
     * 添加客户匹配数据
     *
     * @param storeId Google Ads 连接 ID
     * @param dataDto 客户匹配数据 DTO
     * @return 保存后的实体
     */
    GoogleAdsCustomerMatch addCustomerMatchData(Long storeId, CustomerMatchDataDto dataDto);

    /**
     * 批量添加客户匹配数据
     *
     * @param storeId Google Ads 连接 ID
     * @param dataDtoList 客户匹配数据 DTO 列表
     * @return 保存的记录数
     */
    int batchAddCustomerMatchData(Long storeId, List<CustomerMatchDataDto> dataDtoList);

    /**
     * 同步客户匹配数据到 Google Ads
     *
     * @param storeId Google Ads 连接 ID
     * @return 同步结果
     */
    String syncCustomerMatchData(Long storeId);
}
