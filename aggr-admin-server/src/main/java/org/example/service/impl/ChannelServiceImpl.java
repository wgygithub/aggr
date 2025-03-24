package org.example.service.impl;

import org.example.mapper.ChannelMapper;
import org.example.param.channel.QueryChannelParam;
import org.example.service.ChannelService;
import org.example.vo.channel.ChannelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @description 针对表【channel(支付渠道表)】的数据库操作Service实现
 * @createDate 2025-03-23 22:58:39
 */
@Service
public class ChannelServiceImpl implements ChannelService {

  @Autowired
  private ChannelMapper channelMapper;

  @Override
  public List<ChannelVo> queryChannel(QueryChannelParam param) {
    return channelMapper.selectList(param);
  }
}




