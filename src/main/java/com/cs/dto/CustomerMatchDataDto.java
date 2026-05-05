package com.cs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 客户匹配数据 DTO
 *
 * @author LivePulse
 */
@Getter
@Setter
@Schema(description = "客户匹配数据")
public class CustomerMatchDataDto {

    @Schema(description = "用户 ID")
    private String userId;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "名字")
    private String firstName;

    @Schema(description = "姓氏")
    private String lastName;

    @Schema(description = "街道地址")
    private String streetAddress;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "州/省")
    private String state;

    @Schema(description = "邮编")
    private String postalCode;

    @Schema(description = "国家代码")
    private String countryCode;

    @Schema(description = "移动设备 ID (IDFA 或 AdID)")
    private String mobileDeviceId;
}
