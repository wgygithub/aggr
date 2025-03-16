package org.example.util;


import cn.hutool.crypto.digest.BCrypt;

public final class PasswordUtils {
    private PasswordUtils() {
    }

    /**
     * 生成加密密码
     *
     * @param rawPassword 明文密码
     * @return 带盐的BCrypt哈希
     */
    public static String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword);
    }

    /**
     * 密码验证
     *
     * @param rawPassword     明文密码
     * @param encodedPassword 存储的哈希密码
     * @return 验证结果
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }

    /**
     * 生成盐值
     */
    public static String gensalt() {
        return BCrypt.gensalt();
    }

    public static void main(String[] args) {
        String gensalt = BCrypt.gensalt();
        System.out.println(gensalt);
        String password = "123456";
        String encodePassword = encode(password);
        System.out.println(encodePassword);
        boolean matches = matches(password, encodePassword);
        System.out.println(matches);
    }
}
