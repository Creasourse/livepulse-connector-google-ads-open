package com.cs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.dto.CustomerMatchDataDto;
import com.cs.entity.GoogleAdsCustomerMatch;
import com.cs.entity.GoogleAdsStore;
import com.cs.mapper.GoogleAdsCustomerMatchMapper;
import com.cs.service.GoogleAdsCustomerMatchService;
import com.cs.service.GoogleAdsStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Google Ads 客户匹配数据服务实现
 *
 * @author LivePulse
 */
@Slf4j
@Service
public class GoogleAdsCustomerMatchServiceImpl extends ServiceImpl<GoogleAdsCustomerMatchMapper, GoogleAdsCustomerMatch> implements GoogleAdsCustomerMatchService {

    @Lazy
    @Autowired
    private GoogleAdsStoreService storeService;

    @Override
    public GoogleAdsCustomerMatch addCustomerMatchData(Long storeId, CustomerMatchDataDto dataDto) {
        GoogleAdsCustomerMatch entity = new GoogleAdsCustomerMatch();
        entity.setGoogleAdsStoreId(storeId);
        entity.setUserId(dataDto.getUserId());

        // 存储原始数据
        entity.setRawEmail(dataDto.getEmail());
        entity.setRawPhone(dataDto.getPhone());
        entity.setRawFirstName(dataDto.getFirstName());
        entity.setRawLastName(dataDto.getLastName());
        entity.setRawStreetAddress(dataDto.getStreetAddress());
        entity.setRawCity(dataDto.getCity());
        entity.setRawState(dataDto.getState());
        entity.setRawPostalCode(dataDto.getPostalCode());
        entity.setRawCountryCode(dataDto.getCountryCode());

        // 存储移动设备 ID
        entity.setMobileDeviceId(dataDto.getMobileDeviceId());

        // SHA-256 加密
        entity.setHashedEmail(hashNormalized(dataDto.getEmail()));
        entity.setHashedPhone(hashNormalized(dataDto.getPhone()));
        entity.setHashedFirstName(hashNormalized(dataDto.getFirstName()));
        entity.setHashedLastName(hashNormalized(dataDto.getLastName()));
        entity.setHashedStreetAddress(hashNormalized(dataDto.getStreetAddress()));
        entity.setHashedCity(hashNormalized(dataDto.getCity()));
        entity.setHashedState(hashNormalized(dataDto.getState()));
        entity.setHashedPostalCode(hashNormalized(dataDto.getPostalCode()));
        entity.setHashedCountryCode(hashNormalized(dataDto.getCountryCode()));

        entity.setSyncStatus("pending");
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());

        this.save(entity);
        return entity;
    }

    @Override
    public int batchAddCustomerMatchData(Long storeId, List<CustomerMatchDataDto> dataDtoList) {
        for (CustomerMatchDataDto dataDto : dataDtoList) {
            addCustomerMatchData(storeId, dataDto);
        }
        return dataDtoList.size();
    }

    @Override
    public String syncCustomerMatchData(Long storeId) {
        GoogleAdsStore store = storeService.getById(storeId);
        if (store == null) {
            throw new IllegalArgumentException("Google Ads 连接不存在");
        }

        // TODO: 实现实际的 Google Ads API 调用
        // 这里需要使用 Google Ads API SDK 来上传客户匹配数据
        // 参考: https://developers.google.com/google-ads/api/docs/remarketing/ customer-match

        log.info("同步客户匹配数据到 Google Ads: storeId={}, customerId={}", storeId, store.getClientCustomerId());

        return "同步成功";
    }

    /**
     * SHA-256 加密并规范化
     * Google Ads 要求对用户数据进行规范化后再加密
     *
     * @param input 原始输入
     * @return SHA-256 哈希值（16进制）
     */
    private String hashNormalized(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }

        // 规范化：去除空格，转小写
        String normalized = input.trim().toLowerCase();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(normalized.getBytes(StandardCharsets.UTF_8));

            // 转换为 16 进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA-256 加密失败", e);
            throw new RuntimeException("SHA-256 加密失败", e);
        }
    }
}
