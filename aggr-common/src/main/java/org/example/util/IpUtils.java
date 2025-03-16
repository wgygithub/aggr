package org.example.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Slf4j
@UtilityClass
public final class IpUtils {

  private static final String IP_ADDRESS_PATTERN =
      "^(25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})\\." +
          "(25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})\\." +
          "(25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})\\." +
          "(25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})$";

  private static final Pattern pattern = Pattern.compile(IP_ADDRESS_PATTERN);

  /**
   * 判断ipv4
   */
  public static boolean validIPv4(String ip) {
    if (ip == null) {
      return false;
    }
    return pattern.matcher(ip).matches();
  }

  /**
   * 获取IP地址
   * <p>
   * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
   * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
   */
  public static String getIpAddr(HttpServletRequest request) {
    String ip = null;
    try {
      ip = request.getHeader("x-forwarded-for");
      if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
      }
      if (StringUtils.isEmpty(ip) || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
      }
      if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_CLIENT_IP");
      }
      if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
      }
      if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
      }
    } catch (Exception e) {
      log.error("IpUtils ERROR ", e);
    }
    return ip;
  }


}
