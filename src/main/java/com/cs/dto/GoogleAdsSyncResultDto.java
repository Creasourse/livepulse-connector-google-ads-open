package com.cs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Google Ads 同步结果 DTO
 *
 * @author LivePulse
 */
@Getter
@Setter
@Schema(description = "Google Ads 同步结果")
public class GoogleAdsSyncResultDto {

    @Schema(description = "同步状态")
    private String status;

    @Schema(description = "成功数量")
    private Long successCount;

    @Schema(description = "失败数量")
    private Long failureCount;

    @Schema(description = "总数量")
    private Long totalCount;

    @Schema(description = "消息")
    private String message;

    @Schema(description = "耗时（毫秒）")
    private Long duration;

    public static GoogleAdsSyncResultDto success(Long successCount, Long totalCount, Long duration) {
        GoogleAdsSyncResultDto result = new GoogleAdsSyncResultDto();
        result.setStatus("success");
        result.setSuccessCount(successCount);
        result.setTotalCount(totalCount);
        result.setFailureCount(totalCount - successCount);
        result.setDuration(duration);
        result.setMessage(String.format("同步完成，成功 %d 条，失败 %d 条，耗时 %d ms", successCount, totalCount - successCount, duration));
        return result;
    }

    public static GoogleAdsSyncResultDto failure(String errorMessage) {
        GoogleAdsSyncResultDto result = new GoogleAdsSyncResultDto();
        result.setStatus("failed");
        result.setMessage(errorMessage);
        return result;
    }
}
