package org.example.service;

import org.example.param.channel.QueryChannelParam;
import org.example.vo.channel.ChannelVo;

import java.util.List;

/**
 * @author admin
 * @description 针对表【channel(支付渠道表)】的数据库操作Service
 * @createDate 2025-03-23 22:58:39
 */
public interface ChannelService {
  List<ChannelVo> queryChannel(QueryChannelParam param);
}
