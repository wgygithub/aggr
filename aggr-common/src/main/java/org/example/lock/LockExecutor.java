package org.example.lock;

/**
 * @author admin
 */
public interface LockExecutor {
  /**
   * 获取锁
   *
   * @param key
   * @param leaseTime
   * @return
   */
  boolean acquire(String key, long leaseTime);

  /**
   * 释放锁
   *
   * @param key
   */
  void release(String key);
}
