package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.annotations.DbSource;
import org.example.constant.DataSourceConstants;
import org.example.domain.Channel;
import org.example.param.channel.QueryChannelParam;
import org.example.vo.channel.ChannelVo;

import java.util.List;

/**
 * @author admin
 * @description 针对表【channel(支付渠道表)】的数据库操作Mapper
 * @createDate 2025-03-23 22:58:39
 * @Entity org.example.domain.Channel
 */
public interface ChannelMapper extends BaseMapper<Channel> {

  @DbSource(value = DataSourceConstants.DEFAULT_SLAVE_KEY)
  List<ChannelVo> selectList(@Param("param") QueryChannelParam param);

}