package com.sdjyyds.user.util;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * 密码加密工具类
 * 使用 PBKDF2WithHmacSHA256 算法进行密码哈希
 */
public class PasswordEncryptor {
    // 算法参数
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 10000;  // 迭代次数
    private static final int SALT_LENGTH = 16;     // 盐值长度(字节)
    private static final int KEY_LENGTH = 256;     // 密钥长度(位)

    /**
     * 生成随机盐值
     * @return Base64编码的盐值字符串
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 加密密码
     * @param password 明文密码
     * @param salt Base64编码的盐值
     * @return Base64编码的加密后的密码
     */
    public static String encryptPassword(String password, String salt) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    saltBytes,
                    ITERATIONS,
                    KEY_LENGTH);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }

    /**
     * 验证密码
     * @param inputPassword 用户输入的密码
     * @param storedSalt 存储的盐值
     * @param storedPassword 存储的加密密码
     * @return 是否匹配
     */
    public static boolean verifyPassword(String inputPassword, String storedSalt, String storedPassword) {
        String encryptedInput = encryptPassword(inputPassword, storedSalt);
        return encryptedInput.equals(storedPassword);
    }
}
