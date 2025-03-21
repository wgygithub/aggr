package org.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName event_login
 */
@TableName(value = "event_login")
@Data
public class EventLogin {
  /**
   *
   */
  @TableId(type = IdType.AUTO)
  private Integer id;

  /**
   *
   */
  private Long createTime;

  /**
   *
   */
  private String username;

  /**
   *
   */
  private String totp;

  /**
   *
   */
  private Boolean success;

  /**
   *
   */
  private String remark;
}