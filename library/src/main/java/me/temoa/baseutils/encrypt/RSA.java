package me.temoa.baseutils.encrypt;

import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * Created by lai
 * on 2018/3/20.
 */
@SuppressWarnings("WeakerAccess")
public class RSA {

    public static final int RSA_PUBLIC_ENCRYPT = 0;
    public static final int RSA_PUBLIC_DECRYPT = 1;

    public static final int RSA_PRIVATE_ENCRYPT = 2;
    public static final int RSA_PRIVATE_DECRYPT = 3;

    @IntDef({RSA_PUBLIC_ENCRYPT, RSA_PUBLIC_DECRYPT,
            RSA_PRIVATE_ENCRYPT, RSA_PRIVATE_DECRYPT})
    public @interface RSAType {
    }

    /**
     * 一般公钥加密/私钥解密
     *
     * @param type 操作类型：
     *             {@link #RSA_PUBLIC_ENCRYPT}
     *             {@link #RSA_PUBLIC_DECRYPT}
     *             {@link #RSA_PRIVATE_ENCRYPT}
     *             {@link #RSA_PRIVATE_DECRYPT}
     */
    public static byte[] rsa(@NonNull byte[] data, @NonNull String keyStr, @RSAType int type)
            throws Exception {

        byte[] keyBytes = Base64.decode(keyStr, Base64.DEFAULT);
        Key key;
        KeyFactory keyFactory = getKeyFactory();
        if (type == RSA_PUBLIC_ENCRYPT || type == RSA_PRIVATE_ENCRYPT) {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            key = keyFactory.generatePublic(x509EncodedKeySpec);
        } else {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            key = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        }

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        if (type == RSA_PUBLIC_ENCRYPT || type == RSA_PRIVATE_ENCRYPT) {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }
        byte[] result;
        result = cipher.doFinal(data);
        return result;
    }

    /**
     * 随机获取密钥, 客户端公钥加密, 服务器密钥加密
     */
    public static Map<String, Object> getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = getKeyPairGenerator();
        keyPairGenerator.initialize(1024);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put("RSAPublicKey", publicKey);
        keyMap.put("RSAPrivateKey", privateKey);
        return keyMap;
    }

    public static String getKey(Map<String, Object> keyMap, boolean isPublicKey) {
        Key key = (Key) keyMap.get(isPublicKey ? "RSAPublicKey" : "RSAPrivateKey");
        return new String(Base64.encode(key.getEncoded(), Base64.DEFAULT));
    }

    /**
     * 数字签名
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey.getBytes(), Base64.DEFAULT);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = getKeyFactory();
        PrivateKey pk = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(pk);
        signature.update(data);
        return new String(Base64.encode(signature.sign(), Base64.DEFAULT));
    }

    /**
     * 数字签名校验
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64.encode(publicKey.getBytes(), Base64.DEFAULT);

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = getKeyFactory();
        PublicKey pk = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(pk);
        signature.update(data);
        return signature.verify(Base64.decode(sign.getBytes(), Base64.DEFAULT));
    }

    private static KeyPairGenerator getKeyPairGenerator()
            throws NoSuchProviderException, NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator;
        if (Build.VERSION.SDK_INT >= 16) {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        } else {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        }
        return keyPairGenerator;
    }

    private static KeyFactory getKeyFactory()
            throws NoSuchProviderException, NoSuchAlgorithmException {

        KeyFactory keyFactory;
        if (Build.VERSION.SDK_INT >= 16) {
            keyFactory = KeyFactory.getInstance("RSA", "BC");
        } else {
            keyFactory = KeyFactory.getInstance("RSA");
        }
        return keyFactory;
    }
}
