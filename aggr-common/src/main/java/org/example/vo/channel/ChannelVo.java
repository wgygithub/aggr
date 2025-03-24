package org.example.vo.channel;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author admin
 */
@Data
public class ChannelVo implements Serializable {
  private static final long serialVersionUID = 1L;

  private String channelId;

  private String channelName;

  private String channelLabel;

  /**
   * 枚举：ChannelType
   * 类型: PAYIN-代收,PAYOUT-代付,BOTH-都支持
   */
  private String type;

  /**
   * 是否激活,1启用，0禁止
   */
  private Boolean active;

  /**
   * 权重(用于分配优先级)
   */
  private Integer weigh;

  /**
   * 代收手续费比例
   */
  private BigDecimal payinFeeRate;

  /**
   * 代付手续费比例
   */
  private BigDecimal payoutFeeRate;

  /**
   * 代付单笔手续费
   */
  private BigDecimal payoutSingleTransactionFee;

  /**
   * 代收最小金额
   */
  private BigDecimal payinMinAmount;

  /**
   * 代收最大金额
   */
  private BigDecimal payinMaxAmount;

  /**
   * 代付最小金额
   */
  private BigDecimal payoutMinAmount;

  /**
   * 代付最大金额
   */
  private BigDecimal payoutMaxAmount;

  /**
   * 支付平台域名
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
  private Boolean configActive;

  /**
   * 其它配置信息
   */
  private String otherConfig;
}
