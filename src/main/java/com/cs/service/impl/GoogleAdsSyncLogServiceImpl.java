package com.cs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.GoogleAdsSyncLog;
import com.cs.mapper.GoogleAdsSyncLogMapper;
import com.cs.service.GoogleAdsSyncLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Google Ads 同步日志服务实现
 *
 * @author LivePulse
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleAdsSyncLogServiceImpl extends ServiceImpl<GoogleAdsSyncLogMapper, GoogleAdsSyncLog> implements GoogleAdsSyncLogService {

    @Override
    public GoogleAdsSyncLog createSyncLog(Long googleAdsStoreId, String syncType, String syncStatus) {
        GoogleAdsSyncLog syncLog = new GoogleAdsSyncLog();
        syncLog.setGoogleAdsStoreId(googleAdsStoreId);
        syncLog.setSyncType(syncType);
        syncLog.setSyncStatus(syncStatus);
        syncLog.setStartTime(LocalDateTime.now());
        syncLog.setSuccessCount(0L);
        syncLog.setFailureCount(0L);
        syncLog.setTotalCount(0L);
        syncLog.setCreateTime(LocalDateTime.now());

        this.save(syncLog);
        return syncLog;
    }

    @Override
    public void updateSyncLog(GoogleAdsSyncLog syncLog, String syncStatus, Long successCount, Long failureCount, Long totalCount, String errorMessage) {
        syncLog.setSyncStatus(syncStatus);
        syncLog.setEndTime(LocalDateTime.now());
        syncLog.setSuccessCount(successCount);
        syncLog.setFailureCount(failureCount);
        syncLog.setTotalCount(totalCount);
        syncLog.setErrorMessage(errorMessage);

        // 计算耗时
        if (syncLog.getStartTime() != null && syncLog.getEndTime() != null) {
            Duration duration = Duration.between(syncLog.getStartTime(), syncLog.getEndTime());
            syncLog.setDuration(duration.toMillis());
        }

        this.updateById(syncLog);
    }
}
