package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.GoogleAdsSyncLog;

/**
 * Google Ads 同步日志服务
 *
 * @author LivePulse
 */
public interface GoogleAdsSyncLogService extends IService<GoogleAdsSyncLog> {

    /**
     * 创建同步日志
     *
     * @param googleAdsStoreId Google Ads 连接 ID
     * @param syncType 同步类型
     * @param syncStatus 同步状态
     * @return 创建的同步日志
     */
    GoogleAdsSyncLog createSyncLog(Long googleAdsStoreId, String syncType, String syncStatus);

    /**
     * 更新同步日志
     *
     * @param syncLog 同步日志
     * @param syncStatus 同步状态
     * @param successCount 成功数量
     * @param failureCount 失败数量
     * @param totalCount 总数量
     * @param errorMessage 错误信息
     */
    void updateSyncLog(GoogleAdsSyncLog syncLog, String syncStatus, Long successCount, Long failureCount, Long totalCount, String errorMessage);
}
