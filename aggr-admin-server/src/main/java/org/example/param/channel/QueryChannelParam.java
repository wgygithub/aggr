package org.example.param.channel;

import lombok.Data;

import java.io.Serializable;

/**
 * @author admin
 */
@Data
public class QueryChannelParam implements Serializable {
  //通道名称
  private String channelName;
  //通道类型:BOTH - 全部,PAYIN - 代收通道,PAYOUT - 代付通道
  private String channelType;
}
