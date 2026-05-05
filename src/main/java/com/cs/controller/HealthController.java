package com.cs.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查控制器
 *
 * @author LivePulse
 */
@RestController
@RequestMapping("/google-ads")
@Tag(name = "健康检查", description = "服务健康检查接口")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "健康检查")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("service", "Google Ads Connector");
        result.put("version", "1.0");
        return ResponseEntity.ok(result);
    }
}
