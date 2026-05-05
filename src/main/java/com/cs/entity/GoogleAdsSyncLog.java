package com.cs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Google Ads 同步日志实体
 * 记录 Google Ads 同步任务的执行情况
 *
 * @author LivePulse
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("google_ads_sync_log")
@Schema(name = "GoogleAdsSyncLog", description = "Google Ads 同步日志表")
public class GoogleAdsSyncLog extends Model<GoogleAdsSyncLog> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键 ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "关联的 Google Ads 连接 ID")
    @TableField("google_ads_store_id")
    private Long googleAdsStoreId;

    @Schema(description = "同步类型: customer_match(客户匹配)")
    @TableField("sync_type")
    private String syncType;

    @Schema(description = "同步状态: pending/running/success/failed/partial_success")
    @TableField("sync_status")
    private String syncStatus;

    @Schema(description = "开始时间")
    @TableField("start_time")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;

    @Schema(description = "成功记录数")
    @TableField("success_count")
    private Long successCount;

    @Schema(description = "失败记录数")
    @TableField("failure_count")
    private Long failureCount;

    @Schema(description = "总记录数")
    @TableField("total_count")
    private Long totalCount;

    @Schema(description = "错误信息")
    @TableField("error_message")
    private String errorMessage;

    @Schema(description = "执行耗时（毫秒）")
    @TableField("duration")
    private Long duration;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
