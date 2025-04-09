package org.example.lock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author admin
 */
@Component
@ConditionalOnProperty(name = "lock.type", havingValue = "LOCAL")
public class LocalLockExecutor implements LockExecutor {
  private final ConcurrentMap<String, Lock> lockStore = new ConcurrentHashMap<>();

  @Override
  public boolean acquire(String key, long leaseTime) {
    Lock lock = lockStore.computeIfAbsent(key, k -> new ReentrantLock());
    try {
      return lock.tryLock(leaseTime, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return false;
    }
  }

  @Override
  public void release(String key) {
    Lock lock = lockStore.get(key);
    if (lock != null) {
      lock.unlock();
    }
  }
}
