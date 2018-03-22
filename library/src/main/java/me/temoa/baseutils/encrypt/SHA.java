package me.temoa.baseutils.encrypt;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lai
 * on 2018/3/20.
 */
@SuppressWarnings("WeakerAccess")
public class SHA {

    public static final String SHA224 = "sha-224";
    public static final String SHA256 = "sha-256";
    public static final String SHA384 = "sha-384";
    public static final String SHA512 = "sha-512";

    @StringDef({SHA224, SHA256, SHA384, SHA512})
    public @interface SHAType {
    }

    public static String sha(@NonNull String content, @NonNull @SHAType String shaType) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        if (TextUtils.isEmpty(shaType)) {
            return null;
        }

        try {
            MessageDigest sha = MessageDigest.getInstance(shaType);
            byte[] bytes = sha.digest(content.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                sb.append(temp);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
