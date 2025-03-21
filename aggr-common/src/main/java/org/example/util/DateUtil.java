package org.example.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public final class DateUtil {
  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  private DateUtil() {
  }

  /**
   * 获取当前时间戳
   *
   * @return
   */
  public static Long current() {
    return Instant
      .now()
      .toEpochMilli();
  }

  public static Long minusDay(long days) {
    LocalDateTime localDateTime = LocalDateTime.now().minusDays(days);
    return localDateTimeToTimestamp(localDateTime);
  }

  public static Long minusHours(long hours) {
    LocalDateTime localDateTime = LocalDateTime.now().minusHours(hours);
    return localDateTimeToTimestamp(localDateTime);
  }


  public static Long minusMinutes(long minutes) {
    LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(minutes);
    return localDateTimeToTimestamp(localDateTime);
  }

  public static Long plusMinutes(long minutes) {
    LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(minutes);
    return localDateTimeToTimestamp(localDateTime);
  }

  public static Long plusDays(long days) {
    LocalDateTime localDateTime = LocalDateTime.now().plusDays(days);
    return localDateTimeToTimestamp(localDateTime);
  }

  public static Long plusHours(long hours) {
    LocalDateTime localDateTime = LocalDateTime.now().plusHours(hours);
    return localDateTimeToTimestamp(localDateTime);
  }

  public static Long localDateTimeToTimestamp(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

}
