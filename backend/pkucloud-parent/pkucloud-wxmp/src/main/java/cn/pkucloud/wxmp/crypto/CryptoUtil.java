package cn.pkucloud.wxmp.crypto;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.pkucloud.wxmp.dto.wx.xml.BaseResponseEntity;
import cn.pkucloud.wxmp.dto.wx.xml.XmlRequestEntity;
import cn.pkucloud.wxmp.dto.wx.xml.XmlResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.codec.binary.Base64;

public class CryptoUtil {
    static Charset CHARSET = StandardCharsets.UTF_8;
    String appId;
    String token;
    byte[] aesKey;

    public CryptoUtil(String appId, String token, String encodingAesKey) {
        this.token = token;
        this.appId = appId;
        aesKey = Base64.decodeBase64(encodingAesKey + "=");
    }

    // 生成4个字节的网络字节序
    byte[] getNetworkBytesOrder(int sourceNumber) {
        byte[] orderBytes = new byte[4];
        orderBytes[3] = (byte) (sourceNumber & 0xFF);
        orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
        orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
        orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
        return orderBytes;
    }

    // 还原4个字节的网络字节序
    int recoverNetworkBytesOrder(byte[] orderBytes) {
        int sourceNumber = 0;
        for (int i = 0; i < 4; ++i) {
            sourceNumber <<= 8;
            sourceNumber |= orderBytes[i] & 0xff;
        }
        return sourceNumber;
    }

    // 随机生成16位字符串
    String getRandomStr() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    String encrypt(String unencryptedStr) throws CryptoException {
        String randomStr = getRandomStr();

        byte[] randomStrBytes = randomStr.getBytes(CHARSET);
        byte[] textBytes = unencryptedStr.getBytes(CHARSET);
        byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
        byte[] appidBytes = appId.getBytes(CHARSET);

        ByteGroup byteCollector = new ByteGroup();
        byteCollector
                .addBytes(randomStrBytes)
                .addBytes(networkBytesOrder)
                .addBytes(textBytes)
                .addBytes(appidBytes);

        byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
        byteCollector.addBytes(padBytes);

        byte[] unencryptedBytes = byteCollector.toBytes();

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            Key key = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encryptedBytes = cipher.doFinal(unencryptedBytes);
            return Base64.encodeBase64String(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CryptoException(CryptoException.EncryptAESError);
        }
    }

    String decrypt(String encryptedStr) throws CryptoException {
        byte[] decryptedBytes;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            Key key = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] encryptedBytes = Base64.decodeBase64(encryptedStr);
            decryptedBytes = cipher.doFinal(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CryptoException(CryptoException.DecryptAESError);
        }

        String decryptedStr, from_appid;
        try {
            byte[] bytes = PKCS7Encoder.decode(decryptedBytes);
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
            int xmlLength = recoverNetworkBytesOrder(networkOrder);

            decryptedStr = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
            from_appid = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CryptoException(CryptoException.IllegalBuffer);
        }

        if (!from_appid.equals(appId)) {
            throw new CryptoException(CryptoException.ValidateAppidError);
        }
        return decryptedStr;
    }

    public XmlResponse encryptMsg(BaseResponseEntity entity) throws CryptoException, JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        String strToEncrypt = xmlMapper.writeValueAsString(entity);
        System.out.println("strToEncrypt = " + strToEncrypt);
        String encrypt = encrypt(strToEncrypt);
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        String nonce = getRandomStr();
        String signature = SHA1.calcSHA1(token, timestamp, nonce, encrypt);
        return new XmlResponse(encrypt, signature, timestamp, nonce);
    }

    public XmlRequestEntity decryptMsg(int timestamp, String nonce, String msg_signature, String encrypt) throws CryptoException, JsonProcessingException {
        String sign = SHA1.calcSHA1(token, timestamp, nonce, encrypt);
        if (!sign.equals(msg_signature)) {
            throw new CryptoException(CryptoException.ValidateSignatureError);
        }
        String xmlRequestStr = decrypt(encrypt);
//        System.out.println("xmlRequestStr = " + xmlRequestStr);
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        return xmlMapper.readValue(xmlRequestStr, XmlRequestEntity.class);
    }

    public boolean verify(String signature, int timestamp, String nonce) {
        String sign = SHA1.calcSHA1(token, timestamp, nonce, "");
        return sign.equals(signature);
    }
}