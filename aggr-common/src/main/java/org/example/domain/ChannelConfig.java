package org.example.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 支付渠道配置表
 *
 * @author admin
 * @TableName channel_config
 */
@TableName(value = "channel_config")
@Data
public class ChannelConfig {
  /**
   * 渠道唯一标识
   */
  @TableId
  private String channelLabel;

  /**
   * 支付网关域名
   */
  private String domain;

  /**
   * 商户id
   */
  private String appId;

  /**
   * 支付平台公钥
   */
  private String publicKey;

  /**
   * 商户私钥
   */
  private String privateKey;

  /**
   * 是否激活,1启用，0禁止
   */
  private Boolean active;

  /**
   * 其它配置信息
   */
  private String otherConfig;

  /**
   * 创建时间
   */
  private Long createTime;

  /**
   * 更新时间
   */
  private Long updateTime;
}