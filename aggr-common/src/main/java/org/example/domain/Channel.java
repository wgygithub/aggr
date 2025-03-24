package org.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付渠道表
 *
 * @TableName channel
 */
@TableName(value = "channel")
@Data
public class Channel {
  /**
   * 主键
   */
  @TableId(type = IdType.AUTO)
  private Integer id;

  /**
   * 渠道名称
   */
  private String channelName;

  /**
   * 渠道唯一标识
   */
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
   * 创建时间
   */
  private Long createTime;

  /**
   * 更新时间
   */
  private Long updateTime;
}