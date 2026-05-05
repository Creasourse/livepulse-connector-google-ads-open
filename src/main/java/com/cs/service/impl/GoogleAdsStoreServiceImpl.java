package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.dto.GoogleAdsSyncResultDto;
import com.cs.entity.GoogleAdsStore;
import com.cs.mapper.GoogleAdsStoreMapper;
import com.cs.service.GoogleAdsCustomerMatchService;
import com.cs.service.GoogleAdsStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Google Ads 连接配置服务实现
 *
 * @author LivePulse
 */
@Slf4j
@Service
public class GoogleAdsStoreServiceImpl extends ServiceImpl<GoogleAdsStoreMapper, GoogleAdsStore> implements GoogleAdsStoreService {

    @Lazy
    @Autowired
    private GoogleAdsCustomerMatchService customerMatchService;

    @Override
    public List<GoogleAdsStore> findEnabled() {
        LambdaQueryWrapper<GoogleAdsStore> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoogleAdsStore::getEnabled, true);
        return this.list(queryWrapper);
    }

    @Override
    public GoogleAdsSyncResultDto manualSyncCustomerMatch(Long storeId) {
        log.info("开始手动同步客户匹配数据: storeId={}", storeId);

        GoogleAdsStore store = this.getById(storeId);
        if (store == null) {
            return GoogleAdsSyncResultDto.failure("Google Ads 连接不存在");
        }

        if (!store.getEnabled()) {
            return GoogleAdsSyncResultDto.failure("Google Ads 连接未启用");
        }

        long startTime = System.currentTimeMillis();

        try {
            // 更新同步状态
            store.setSyncStatus("syncing");
            this.updateById(store);

            // 执行同步
            String result = customerMatchService.syncCustomerMatchData(storeId);

            // 更新同步状态
            store.setSyncStatus("success");
            store.setLastSyncTime(LocalDateTime.now());
            store.setLastErrorMessage(null);
            this.updateById(store);

            long duration = System.currentTimeMillis() - startTime;
            log.info("客户匹配数据同步成功: storeId={}, result={}, duration={}ms", storeId, result, duration);

            return GoogleAdsSyncResultDto.success(1L, 1L, duration);

        } catch (Exception e) {
            log.error("客户匹配数据同步失败: storeId={}", storeId, e);

            // 更新同步状态
            store.setSyncStatus("failed");
            store.setLastErrorMessage(e.getMessage());
            this.updateById(store);

            return GoogleAdsSyncResultDto.failure("同步失败: " + e.getMessage());
        }
    }
}
