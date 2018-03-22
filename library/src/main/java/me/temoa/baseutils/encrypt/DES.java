package me.temoa.baseutils.encrypt;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by lai
 * on 2018/3/20.
 */
@SuppressWarnings("WeakerAccess")
public class DES {

    public static String encrypt(@NonNull String content, @NonNull String key) {
        return des(content, key, Cipher.ENCRYPT_MODE);
    }

    public static String decrypt(@NonNull String encryptContent, @NonNull String key) {
        return des(encryptContent, key, Cipher.DECRYPT_MODE);
    }

    private static String des(@NonNull String content, @NonNull String key, int type) {
        try {
//            SecureRandom secureRandom = new SecureRandom();
            IvParameterSpec iv = new IvParameterSpec("ssl4i1xh".getBytes("UTF-8"));
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(type, secretKeyFactory.generateSecret(desKeySpec), iv);
            if (type == Cipher.ENCRYPT_MODE) {
                byte[] byteContent = content.getBytes("UTF-8");
                return parseByte2HexStr(cipher.doFinal(byteContent));
            } else {
                byte[] byteContent = parseHexStr2Bytes(content);
                return new String(cipher.doFinal(byteContent));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @NonNull
    public static String parseByte2HexStr(@NonNull byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    @Nullable
    public static byte[] parseHexStr2Bytes(@NonNull String hex) {
        if (hex.length() < 1) {
            return null;
        }
        byte[] result = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length() / 2; i++) {
            int high = Integer.parseInt(hex.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hex.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
