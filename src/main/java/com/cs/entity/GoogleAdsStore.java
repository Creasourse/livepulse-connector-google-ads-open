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
 * Google Ads 连接配置实体
 * 对应 Google Ads API 连接配置
 *
 * @author LivePulse
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("google_ads_store")
@Schema(name = "GoogleAdsStore", description = "Google Ads 连接配置表")
public class GoogleAdsStore extends Model<GoogleAdsStore> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键 ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "Google Ads 开发者令牌")
    @TableField("developer_token")
    private String developerToken;

    @Schema(description = "客户 ID (格式: 123-456-7890)")
    @TableField("client_customer_id")
    private String clientCustomerId;

    @Schema(description = "OAuth2 客户端 ID")
    @TableField("client_id")
    private String clientId;

    @Schema(description = "OAuth2 客户端密钥")
    @TableField("client_secret")
    private String clientSecret;

    @Schema(description = "OAuth2 刷新令牌")
    @TableField("refresh_token")
    private String refreshToken;

    @Schema(description = "经理账户 ID")
    @TableField("manager_account_id")
    private String managerAccountId;

    @Schema(description = "登录客户 ID")
    @TableField("login_customer_id")
    private String loginCustomerId;

    @Schema(description = "是否启用")
    @TableField("enabled")
    private Boolean enabled;

    @Schema(description = "同步状态: pending/syncing/success/failed")
    @TableField("sync_status")
    private String syncStatus;

    @Schema(description = "最后同步时间")
    @TableField("last_sync_time")
    private LocalDateTime lastSyncTime;

    @Schema(description = "最后错误信息")
    @TableField("last_error_message")
    private String lastErrorMessage;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @Schema(description = "创建人")
    @TableField("create_by")
    private String createBy;

    @Schema(description = "更新人")
    @TableField("update_by")
    private String updateBy;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
