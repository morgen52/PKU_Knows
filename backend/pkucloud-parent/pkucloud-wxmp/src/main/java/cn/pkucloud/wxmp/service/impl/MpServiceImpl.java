package cn.pkucloud.wxmp.service.impl;

import cn.pkucloud.wxmp.dto.wx.xml.XmlEntity;
import cn.pkucloud.wxmp.dto.wx.xml.XmlRequest;
import cn.pkucloud.wxmp.dto.wx.xml.XmlResponse;
import cn.pkucloud.wxmp.exception.AesException;
import cn.pkucloud.wxmp.feign.AuthClient;
import cn.pkucloud.wxmp.feign.MpClient;
import cn.pkucloud.wxmp.service.MpMsgService;
import cn.pkucloud.wxmp.service.AccessTokenService;
import cn.pkucloud.wxmp.service.MpService;
import cn.pkucloud.wxmp.util.ByteGroup;
import cn.pkucloud.wxmp.util.PKCS7Encoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

@Service
public class MpServiceImpl implements MpService {
    private final AccessTokenService accessTokenService;

    private final MpMsgService mpMsgService;

    private final MpClient mpClient;

    private final AuthClient authClient;

    private final XmlMapper xmlMapper;

    @Value("${wx.mp.appid}")
    private String APPID;

    @Value("${wx.mp.wxid}")
    private String WXID;

    @Value("${wx.mp.token}")
    private String TOKEN;

    @Value("${wx.mp.encoding_aes_key}")
    private String ENCODING_AES_KEY;

    public MpServiceImpl(AccessTokenService accessTokenService, MpMsgService mpMsgService, MpClient mpClient, AuthClient authClient) {
        this.accessTokenService = accessTokenService;
        this.mpMsgService = mpMsgService;
        this.xmlMapper = new XmlMapper();
        this.mpClient = mpClient;
        this.authClient = authClient;
    }

    @Override
    public String echo(String signature, String echostr, int timestamp, String nonce) {
        String sign = calcSignature(TOKEN, timestamp, nonce, "");
        if (sign.equals(signature)) {
            return echostr;
        }
        return null;
    }

    @Override
    public XmlResponse msgHandler(String signature, int timestamp, String nonce, String openid, String encrypt_type, String msg_signature, XmlRequest request) throws AesException, JsonProcessingException {
        String encrypt = request.getEncrypt();
        XmlEntity entity = decryptMsg(msg_signature, timestamp, nonce, encrypt);
        System.out.println("entity = " + entity);
        String toUserName = entity.getToUserName();
        String fromUserName = entity.getFromUserName();
        int createTime = entity.getCreateTime();
        String msgType = entity.getMsgType();

        switch (msgType) {
            case "text":
                String content = entity.getContent();
                return mpMsgService.textMsgHandler(fromUserName, content);

            case "image":
                String picUrl = entity.getPicUrl();
                String imageMediaId = entity.getMediaId();
                return mpMsgService.imageMsgHandler(fromUserName, picUrl, imageMediaId);

            case "voice":
                String voiceMediaId = entity.getMediaId();
                String format = entity.getFormat();
                String recognition = entity.getRecognition();
                return mpMsgService.voiceMsgHandler(fromUserName, voiceMediaId, format, recognition);

            case "video":
                String videoMediaId = entity.getMediaId();
                String videoThumbMediaId = entity.getThumbMediaId();
                return mpMsgService.videoMsgHandler(fromUserName, videoMediaId, videoThumbMediaId);

            case "shortvideo":
                String shortVideoMediaId = entity.getMediaId();
                String shortVideoThumbMediaId = entity.getThumbMediaId();
                return mpMsgService.shortVideoMsgHandler(fromUserName, shortVideoMediaId, shortVideoThumbMediaId);

            case "location":
                double location_x = entity.getLocation_X();
                double location_y = entity.getLocation_Y();
                int scale = entity.getScale();
                String label = entity.getLabel();
                return mpMsgService.locationMsgHandler(fromUserName, location_x, location_y, scale, label);

            case "link":
                String title = entity.getTitle();
                String description = entity.getDescription();
                String url = entity.getUrl();
                return mpMsgService.linkMsgHandler(fromUserName, title, description, url);

            case "event":
                String event = entity.getEvent();
                switch (event) {
                    case "subscribe":
                        return mpMsgService.subscribeEventHandler(fromUserName);

                    case "unsubscribe":
                        return mpMsgService.unsubscribeEventHandler(fromUserName);

                    default:
                        System.out.println(msgType);
                        break;
                }
                break;

            default:
                return null;
        }
        return null;
    }

    String decrypt(String text) throws AesException {
        byte[] aesKey = Base64.decodeBase64(ENCODING_AES_KEY + "=");

        byte[] original;
        try {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);

            // 使用BASE64对密文进行解码
            byte[] encrypted = Base64.decodeBase64(text);

            // 解密
            original = cipher.doFinal(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.DecryptAESError);
        }

        String xmlContent, from_appid;
        try {
            // 去除补位字符
            byte[] bytes = PKCS7Encoder.decode(original);

            // 分离16位随机字符串,网络字节序和AppId
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

            int xmlLength = recoverNetworkBytesOrder(networkOrder);

            xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), StandardCharsets.UTF_8);
            from_appid = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.IllegalBuffer);
        }

        // appid不相同的情况
        if (!from_appid.equals(APPID)) {
            throw new AesException(AesException.ValidateAppidError);
        }
        return xmlContent;
    }

    XmlEntity decryptMsg(String msgSignature, int timeStamp, String nonce, String encrypt) throws AesException, JsonProcessingException {
        String sign = calcSignature(TOKEN, timeStamp, nonce, encrypt);
        if (!sign.equals(msgSignature)) {
            throw new AesException(AesException.ValidateSignatureError);
        }
        String xmlEntityStr = decrypt(encrypt);
        return xmlMapper.readValue(xmlEntityStr, XmlEntity.class);
    }

    String encrypt(String randomStr, String text) throws AesException {
        byte[] aesKey = Base64.decodeBase64(ENCODING_AES_KEY + "=");

        ByteGroup byteCollector = new ByteGroup();
        byte[] randomStrBytes = randomStr.getBytes(StandardCharsets.UTF_8);
        byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
        byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
        byte[] appidBytes = APPID.getBytes(StandardCharsets.UTF_8);

        // randomStr + networkBytesOrder + text + appid
        byteCollector.addBytes(randomStrBytes)
                .addBytes(networkBytesOrder)
                .addBytes(textBytes)
                .addBytes(appidBytes);

        // ... + pad: 使用自定义的填充方式对明文进行补位填充
        byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
        byteCollector.addBytes(padBytes);

        // 获得最终的字节流, 未加密
        byte[] unencrypted = byteCollector.toBytes();

        try {
            // 设置加密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            // 加密
            byte[] encrypted = cipher.doFinal(unencrypted);

            // 使用BASE64对加密后的字符串进行编码

            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.EncryptAESError);
        }
    }

    XmlResponse encryptMsg(XmlEntity xmlEntity, int timeStamp, String nonce) throws AesException, JsonProcessingException {
        String xmlEntityStr = xmlMapper.writeValueAsString(xmlEntity);
        String encrypt = encrypt(getRandomStr(), xmlEntityStr);
        String signature = calcSignature(TOKEN, timeStamp, nonce, encrypt);
        return new XmlResponse(encrypt, signature, timeStamp, nonce);
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
        for (int i = 0; i < 4; i++) {
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
        for (int i = 0; i < 16; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    String calcSignature(String token, int timestamp, String nonce, String data) {
        String[] array = { token, String.valueOf(timestamp), nonce, data };
        Arrays.sort(array);
        String strToSign = StringUtils.join(array);
        return DigestUtils.sha1Hex(strToSign);
    }

    XmlResponse replyTextMsg(String toUserName, String content) throws JsonProcessingException, AesException {
        String fromUserName = WXID;
        int createTime = (int) (System.currentTimeMillis() / 1000);
        String nonce = UUID.randomUUID().toString().replace("-", "");
        XmlEntity xmlEntity = XmlEntity.builder()
                .toUserName(toUserName)
                .fromUserName(fromUserName)
                .createTime(createTime)
                .msgType("text")
                .content(content)
                .build();
        return encryptMsg(xmlEntity, createTime, nonce);
    }
}
