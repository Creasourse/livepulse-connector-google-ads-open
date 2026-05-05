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
 * Google Ads 客户匹配数据实体
 * 对应 Google Ads Customer Match 功能
 *
 * @author LivePulse
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("google_ads_customer_match")
@Schema(name = "GoogleAdsCustomerMatch", description = "Google Ads 客户匹配数据表")
public class GoogleAdsCustomerMatch extends Model<GoogleAdsCustomerMatch> {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键 ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "关联的 Google Ads 连接 ID")
    @TableField("google_ads_store_id")
    private Long googleAdsStoreId;

    @Schema(description = "用户 ID")
    @TableField("user_id")
    private String userId;

    @Schema(description = "SHA-256 加密后的邮箱")
    @TableField("hashed_email")
    private String hashedEmail;

    @Schema(description = "SHA-256 加密后的手机号")
    @TableField("hashed_phone")
    private String hashedPhone;

    @Schema(description = "SHA-256 加密后的名字")
    @TableField("hashed_first_name")
    private String hashedFirstName;

    @Schema(description = "SHA-256 加密后的姓氏")
    @TableField("hashed_last_name")
    private String hashedLastName;

    @Schema(description = "SHA-256 加密后的街道地址")
    @TableField("hashed_street_address")
    private String hashedStreetAddress;

    @Schema(description = "SHA-256 加密后的城市")
    @TableField("hashed_city")
    private String hashedCity;

    @Schema(description = "SHA-256 加密后的州/省")
    @TableField("hashed_state")
    private String hashedState;

    @Schema(description = "SHA-256 加密后的邮编")
    @TableField("hashed_postal_code")
    private String hashedPostalCode;

    @Schema(description = "SHA-256 加密后的国家代码")
    @TableField("hashed_country_code")
    private String hashedCountryCode;

    @Schema(description = "移动设备 ID (IDFA 或 AdID)")
    @TableField("mobile_device_id")
    private String mobileDeviceId;

    @Schema(description = "原始邮箱")
    @TableField("raw_email")
    private String rawEmail;

    @Schema(description = "原始手机号")
    @TableField("raw_phone")
    private String rawPhone;

    @Schema(description = "原始名字")
    @TableField("raw_first_name")
    private String rawFirstName;

    @Schema(description = "原始姓氏")
    @TableField("raw_last_name")
    private String rawLastName;

    @Schema(description = "原始街道地址")
    @TableField("raw_street_address")
    private String rawStreetAddress;

    @Schema(description = "原始城市")
    @TableField("raw_city")
    private String rawCity;

    @Schema(description = "原始州/省")
    @TableField("raw_state")
    private String rawState;

    @Schema(description = "原始邮编")
    @TableField("raw_postal_code")
    private String rawPostalCode;

    @Schema(description = "原始国家代码")
    @TableField("raw_country_code")
    private String rawCountryCode;

    @Schema(description = "同步状态: pending/syncing/success/failed")
    @TableField("sync_status")
    private String syncStatus;

    @Schema(description = "同步时间")
    @TableField("sync_time")
    private LocalDateTime syncTime;

    @Schema(description = "错误信息")
    @TableField("error_message")
    private String errorMessage;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
