package logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Класс {@code PasswordManager} содержит статические методы для работы с паролями.
 * @author Артемий Кульбако
 * @version 1.2.1
 * @since 10.12.19
 */
public class PasswordManager {

    /**
     * Генерирует новый 8-ми значный пароль, содержащий прописные и строчные символы латинского алфавита и цифры.
     * За генерацию символов отвечает {@link java.security.SecureRandom}.
     * @return новый пароль.
     */
    public static String generateNewPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) password.append(chars.charAt(random.nextInt(chars.length())));
        System.out.println("Новый пароль сгенерирован.");
        return password.toString();
    }


    /**
     * Получает хэш-последовательность для переданной строки, согласно указанному алгоритму (соль не добавляется).
     * @param nudeString строка, для которой необходимо получить хэш.
     * @param algorithm алгоритм хэширования.
     * @return хэш-последовательность.
     */
    public static String getHash(String nudeString, String algorithm) {
        byte[] hash;
        try {
            MessageDigest mDigest = MessageDigest.getInstance(algorithm);
            hash = mDigest.digest(nudeString.getBytes());
            System.out.println("Использован алгоритм: " + algorithm);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Запрощенный алгоритм отсутствует. Будет использован алгоритм ROT13.");
            char[] values = nudeString.toCharArray();
            for (int i = 0; i < values.length; i++) {
                char letter = values[i];
                if (letter >= 'a' && letter <= 'z') {
                    if (letter > 'm') letter -= 13;
                    else letter += 13;
                } else if (letter >= 'A' && letter <= 'Z') {
                    if (letter > 'M') letter -= 13;
                    else letter += 13;
                }
                values[i] = letter;
            }
            hash = new String(values).getBytes();
        }
        StringBuilder strongPassword = new StringBuilder();
        for (byte b : hash) strongPassword.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return strongPassword.toString();
    }
}
