package com.cs.task;

import com.cs.dto.GoogleAdsSyncResultDto;
import com.cs.entity.GoogleAdsStore;
import com.cs.service.GoogleAdsStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Google Ads 定时同步任务
 *
 * @author LivePulse
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "google-ads.scheduled.enabled", havingValue = "true", matchIfMissing = true)
public class GoogleAdsScheduledTask {

    private final GoogleAdsStoreService storeService;

    /**
     * 定时同步客户匹配数据
     * Cron: 每 6 小时执行一次
     */
    @Scheduled(cron = "${google-ads.scheduled.customer-match-sync.cron:0 0 */6 * * ?}")
    public void syncCustomerMatch() {
        log.info("开始定时同步 Google Ads 客户匹配数据");
        List<GoogleAdsStore> stores = storeService.findEnabled();

        for (GoogleAdsStore store : stores) {
            try {
                GoogleAdsSyncResultDto result = storeService.manualSyncCustomerMatch(store.getId());
                log.info("客户匹配数据同步完成: {}", result.getMessage());
            } catch (Exception e) {
                log.error("客户匹配数据同步失败: storeId={}", store.getId(), e);
            }
        }
    }
}
