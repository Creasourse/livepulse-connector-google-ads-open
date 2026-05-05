package com.cs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.dto.GoogleAdsSyncResultDto;
import com.cs.entity.GoogleAdsStore;
import com.cs.service.GoogleAdsStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Google Ads 连接配置控制器
 *
 * @author LivePulse
 */
@Slf4j
@RestController
@RequestMapping("/google-ads/store")
@RequiredArgsConstructor
@Tag(name = "Google Ads 连接配置", description = "Google Ads 连接配置管理接口")
public class GoogleAdsStoreController {

    private final GoogleAdsStoreService storeService;

    @GetMapping("/{id}")
    @Operation(summary = "获取 Google Ads 连接配置")
    public ResponseEntity<GoogleAdsStore> getById(
            @Parameter(description = "连接 ID") @PathVariable Long id) {
        GoogleAdsStore store = storeService.getById(id);
        if (store == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(store);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询 Google Ads 连接配置")
    public ResponseEntity<Page<GoogleAdsStore>> page(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer size) {
        Page<GoogleAdsStore> page = new Page<>(current, size);
        return ResponseEntity.ok(storeService.page(page));
    }

    @PostMapping
    @Operation(summary = "创建 Google Ads 连接配置")
    public ResponseEntity<GoogleAdsStore> create(@RequestBody GoogleAdsStore store) {
        storeService.save(store);
        return ResponseEntity.ok(store);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新 Google Ads 连接配置")
    public ResponseEntity<GoogleAdsStore> update(
            @Parameter(description = "连接 ID") @PathVariable Long id,
            @RequestBody GoogleAdsStore store) {
        store.setId(id);
        storeService.updateById(store);
        return ResponseEntity.ok(store);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除 Google Ads 连接配置")
    public ResponseEntity<Void> delete(@Parameter(description = "连接 ID") @PathVariable Long id) {
        storeService.removeById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/sync")
    @Operation(summary = "手动同步客户匹配数据")
    public ResponseEntity<GoogleAdsSyncResultDto> manualSync(
            @Parameter(description = "连接 ID") @PathVariable Long id) {
        GoogleAdsSyncResultDto result = storeService.manualSyncCustomerMatch(id);
        return ResponseEntity.ok(result);
    }
}
