package me.temoa.baseutils.encrypt;

import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by lai
 * on 2018/3/20.
 */
@SuppressWarnings("WeakerAccess")
public class AES {

    private static final String SHA1PRNG = "SHA1PRNG";

    public static String encrypt(@NonNull String content, @NonNull String key) {
        return aes(content, key, Cipher.ENCRYPT_MODE);
    }

    public static String decrypt(@NonNull String encryptContent, @NonNull String key) {
        return aes(encryptContent, key, Cipher.DECRYPT_MODE);
    }

    private static String aes(@NonNull String content, @NonNull String key, int type) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom;
            if (Build.VERSION.SDK_INT >= 24) {
                secureRandom = SecureRandom.getInstance(SHA1PRNG, new CryptoProvider());
            } else if (Build.VERSION.SDK_INT >= 17) {
                secureRandom = SecureRandom.getInstance(SHA1PRNG, "Crypto");
            } else {
                secureRandom = SecureRandom.getInstance(SHA1PRNG);
            }
            secureRandom.setSeed(key.getBytes());
            generator.init(256, secureRandom);
            SecretKey secretKey = generator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            IvParameterSpec iv = new IvParameterSpec("ssl4i1xhwuc5ytae".getBytes("UTF-8"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(type, secretKeySpec, iv);
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

    final static class CryptoProvider extends Provider {

        public CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG",
                    "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }
}
