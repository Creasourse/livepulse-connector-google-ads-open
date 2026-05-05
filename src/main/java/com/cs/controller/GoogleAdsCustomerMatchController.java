package com.cs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.dto.CustomerMatchDataDto;
import com.cs.entity.GoogleAdsCustomerMatch;
import com.cs.service.GoogleAdsCustomerMatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Google Ads 客户匹配数据控制器
 *
 * @author LivePulse
 */
@Slf4j
@RestController
@RequestMapping("/google-ads/customer-match")
@RequiredArgsConstructor
@Tag(name = "Google Ads 客户匹配", description = "Google Ads 客户匹配数据管理接口")
public class GoogleAdsCustomerMatchController {

    private final GoogleAdsCustomerMatchService customerMatchService;

    @GetMapping("/{id}")
    @Operation(summary = "获取客户匹配数据")
    public ResponseEntity<GoogleAdsCustomerMatch> getById(
            @Parameter(description = "数据 ID") @PathVariable Long id) {
        GoogleAdsCustomerMatch data = customerMatchService.getById(id);
        if (data == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询客户匹配数据")
    public ResponseEntity<Page<GoogleAdsCustomerMatch>> page(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "Google Ads 连接 ID") @RequestParam(required = false) Long storeId) {
        Page<GoogleAdsCustomerMatch> page = new Page<>(current, size);
        // TODO: 添加查询条件
        return ResponseEntity.ok(customerMatchService.page(page));
    }

    @PostMapping("/store/{storeId}")
    @Operation(summary = "添加客户匹配数据")
    public ResponseEntity<GoogleAdsCustomerMatch> add(
            @Parameter(description = "Google Ads 连接 ID") @PathVariable Long storeId,
            @RequestBody CustomerMatchDataDto dataDto) {
        GoogleAdsCustomerMatch data = customerMatchService.addCustomerMatchData(storeId, dataDto);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/batch/store/{storeId}")
    @Operation(summary = "批量添加客户匹配数据")
    public ResponseEntity<Integer> batchAdd(
            @Parameter(description = "Google Ads 连接 ID") @PathVariable Long storeId,
            @RequestBody List<CustomerMatchDataDto> dataDtoList) {
        int count = customerMatchService.batchAddCustomerMatchData(storeId, dataDtoList);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/sync/{storeId}")
    @Operation(summary = "同步客户匹配数据到 Google Ads")
    public ResponseEntity<String> sync(
            @Parameter(description = "Google Ads 连接 ID") @PathVariable Long storeId) {
        String result = customerMatchService.syncCustomerMatchData(storeId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除客户匹配数据")
    public ResponseEntity<Void> delete(@Parameter(description = "数据 ID") @PathVariable Long id) {
        customerMatchService.removeById(id);
        return ResponseEntity.ok().build();
    }
}
