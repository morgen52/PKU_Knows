package cn.pkucloud.auth.crypto;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class CryptoUtil {
    String token;
    byte[] keyBytes;

    public CryptoUtil(String token, String key) {
        this.token = token;
        this.keyBytes = Base64.decodeBase64(key);
    }

    public String decrypt(String encryptStr, String ivStr) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        byte[] encryptBytes = Base64.decodeBase64(encryptStr);
        byte[] ivBytes = Base64.decodeBase64(ivStr);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] bytes = cipher.doFinal(encryptBytes);
        return new String(bytes);
    }
}
