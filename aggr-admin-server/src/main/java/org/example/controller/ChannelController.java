package org.example.controller;

import org.example.param.channel.QueryChannelParam;
import org.example.resp.Result;
import org.example.service.ChannelService;
import org.example.vo.channel.ChannelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author admin
 */
@RestController
@RequestMapping("/channel")
public class ChannelController {

  @Autowired
  private ChannelService channelService;

  @GetMapping
  public Result<List<ChannelVo>> queryChannel(QueryChannelParam param) {
    return Result.OK(channelService.queryChannel(param));
  }

}
