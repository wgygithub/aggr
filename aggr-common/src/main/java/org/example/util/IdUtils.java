package org.example.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.AppException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

@Slf4j
public final class IdUtils {
  static Snowflake snowflake = IdUtil.getSnowflake();
  private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  /**
   * 生成用户的userid
   *
   * @return
   */

  public static String genUserId() {
    return snowflake.nextIdStr();
  }

  public static String genInviterCode(String userId) {
    try {
      // Step 1: 将userId与当前时间戳关联
      long timestamp = DateUtil.current();
      // 下划线分隔防混淆
      String rawData = userId + "_" + timestamp;
      // Step 2: 计算哈希值
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hashBytes = digest.digest(rawData.getBytes(StandardCharsets.UTF_8));
      // Step 3: Hash转BigInteger
      BigInteger hashCode = new BigInteger(1, hashBytes);

      // Step 4: Base62编码并裁剪为8位
      return encodeBase62(hashCode).substring(0, 8);
    } catch (NoSuchAlgorithmException e) {
      log.error("Error generating hash code", e);
      throw new AppException("Error generating hash code");
    }
  }

  private static String encodeBase62(BigInteger value) {
    BigInteger base = BigInteger.valueOf(62);
    StringBuilder sb = new StringBuilder();

    while (value.compareTo(BigInteger.ZERO) > 0) {
      // 计算每位余数
      BigInteger[] divRem = value.divideAndRemainder(base);
      int remainder = divRem[1].intValue();
      // 映射字符
      sb.append(BASE62_CHARS.charAt(remainder));
      value = divRem[0];
    }

    // 处理结果并反转
    String code = sb.reverse().toString();
    return code.isEmpty() ? "0" : code;
  }


  public static void main(String[] args) {
    HashSet<String> userIdSet = new HashSet<>();
    HashSet<String> inviterCodeSet = new HashSet<>();
    Long start = System.currentTimeMillis();
    for (int i = 0; i < 100000000; i++) {
      String id = genUserId();
      if (userIdSet.contains(id)) {
        log.info("重复的id:{}", id);
      } else {
        userIdSet.add(id);
      }
      String inviterCode = genInviterCode(id);
      if (inviterCodeSet.contains(inviterCode)) {
        log.info("重复的inviterCode:{}", inviterCode);
      } else {
        inviterCodeSet.add(inviterCode);
      }


//      log.info("{},length:{},inviterCode:{}", id, id.length(), genInviterCode(id));

    }
    Long end = System.currentTimeMillis();
    log.info("耗时:{}ms", end - start);
  }

  /**
   * 获取指定长度的字符串，不带连字符，最多可以到32位
   *
   * @return
   */
  public static String getSimpleUUID(int length) {
    if (length < 0) {
      throw new IllegalArgumentException("param is illegal");
    }
    return IdUtil.fastSimpleUUID().substring(0, length);
  }


}
