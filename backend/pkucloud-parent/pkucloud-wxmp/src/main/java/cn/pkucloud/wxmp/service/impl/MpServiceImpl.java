package cn.pkucloud.wxmp.service.impl;

import cn.pkucloud.wxmp.crypto.CryptoException;
import cn.pkucloud.wxmp.crypto.CryptoUtil;
import cn.pkucloud.wxmp.crypto.SHA1;
import cn.pkucloud.wxmp.dto.wx.common.*;
import cn.pkucloud.wxmp.dto.wx.custom.MiniProgramPageRequestEntity;
import cn.pkucloud.wxmp.dto.wx.xml.*;
import cn.pkucloud.wxmp.feign.AuthClient;
import cn.pkucloud.wxmp.feign.MpClient;
import cn.pkucloud.wxmp.service.AccessTokenService;
import cn.pkucloud.wxmp.service.MpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MpServiceImpl implements MpService {
    private final AccessTokenService accessTokenService;

    private final MpClient mpClient;

    private final AuthClient authClient;

    @Value("${wx.mp.appid}")
    private String APPID;

    @Value("${wx.mp.wxid}")
    private String WXID;

    @Value("${wx.mp.token}")
    private String TOKEN;

    @Value("${wx.mp.encoding_aes_key}")
    private String ENCODING_AES_KEY;

    public MpServiceImpl(AccessTokenService accessTokenService, MpClient mpClient, AuthClient authClient) {
        this.accessTokenService = accessTokenService;
        this.mpClient = mpClient;
        this.authClient = authClient;
    }

    @Override
    public String echo(String signature, String echostr, int timestamp, String nonce) {
        String sign = SHA1.calcSHA1(TOKEN, timestamp, nonce, "");
        if (sign.equals(signature)) {
            return echostr;
        }
        return null;
    }

    @Override
    public XmlResponse msgHandler(String signature, int timestamp, String nonce, String openid, String encrypt_type, String msg_signature, XmlRequest request) throws JsonProcessingException, CryptoException {
        CryptoUtil cryptoUtil = new CryptoUtil(TOKEN, ENCODING_AES_KEY, APPID);
        String encrypt = request.getEncrypt();
        XmlRequestEntity entity = cryptoUtil.decryptMsg(timestamp, nonce, msg_signature, encrypt);
        System.out.println("entity = " + entity);
        String toUserName = entity.getToUserName();
        String fromUserName = entity.getFromUserName();
        int createTime = entity.getCreateTime();
        String msgType = entity.getMsgType();

        switch (msgType) {
            case "text":
                String content = entity.getContent();
                return textMsgHandler(fromUserName, content);

            case "image":
                String picUrl = entity.getPicUrl();
                String imageMediaId = entity.getMediaId();
                return imageMsgHandler(fromUserName, picUrl, imageMediaId);

            case "voice":
                String voiceMediaId = entity.getMediaId();
                String format = entity.getFormat();
                String recognition = entity.getRecognition();
                return voiceMsgHandler(fromUserName, voiceMediaId, format, recognition);

            case "video":
                String videoMediaId = entity.getMediaId();
                String videoThumbMediaId = entity.getThumbMediaId();
                return videoMsgHandler(fromUserName, videoMediaId, videoThumbMediaId);

            case "shortvideo":
                String shortVideoMediaId = entity.getMediaId();
                String shortVideoThumbMediaId = entity.getThumbMediaId();
                return shortVideoMsgHandler(fromUserName, shortVideoMediaId, shortVideoThumbMediaId);

            case "location":
                double location_x = entity.getLocation_X();
                double location_y = entity.getLocation_Y();
                int scale = entity.getScale();
                String label = entity.getLabel();
                return locationMsgHandler(fromUserName, location_x, location_y, scale, label);

            case "link":
                String title = entity.getTitle();
                String description = entity.getDescription();
                String url = entity.getUrl();
                return linkMsgHandler(fromUserName, title, description, url);

            case "event":
                String event = entity.getEvent();
                switch (event) {
                    case "subscribe":
                        return subscribeEventHandler(fromUserName);

                    case "unsubscribe":
                        return unsubscribeEventHandler(fromUserName);

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

    // 接收普通消息
    // https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_standard_messages.html

    /**
     * 接收文本消息
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param content
     * @return
     * @throws CryptoException
     * @throws JsonProcessingException
     */
    private XmlResponse textMsgHandler(String fromUserName, String content) throws CryptoException, JsonProcessingException {
        return replyTextMsg(fromUserName, content);
    }

    /**
     * 接收图片消息
     * @param fromUserName 发送方帐号（一个OpenID）
     * @param picUrl
     * @param imageMediaId
     * @return
     * @throws CryptoException
     * @throws JsonProcessingException
     */
    private XmlResponse imageMsgHandler(String fromUserName, String picUrl, String imageMediaId) throws CryptoException, JsonProcessingException {
        return replyImageMsg(fromUserName, new Image(imageMediaId));
    }

    /**
     * 接收语音消息
     * @param fromUserName
     * @param voiceMediaId
     * @param format
     * @param recognition
     * @return
     * @throws CryptoException
     * @throws JsonProcessingException
     */
    private XmlResponse voiceMsgHandler(String fromUserName, String voiceMediaId, String format, String recognition) throws CryptoException, JsonProcessingException {
        return replyVoiceMsg(fromUserName, new Voice(voiceMediaId));
    }

    private XmlResponse videoMsgHandler(String fromUserName, String videoMediaId, String videoThumbMediaId) {
        return null;
    }

    private XmlResponse shortVideoMsgHandler(String fromUserName, String shortVideoMediaId, String shortVideoThumbMediaId) {
        return null;
    }

    private XmlResponse locationMsgHandler(String fromUserName, double location_x, double location_y, int scale, String label) {
        return null;
    }

    private XmlResponse linkMsgHandler(String fromUserName, String title, String description, String url) {
        return null;
    }

    // 接收事件推送
    // https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_event_pushes.html

    /**
     * 关注事件
     * @param fromUserName 发送方帐号（一个OpenID）
     * @return xml响应体
     */
    private XmlResponse subscribeEventHandler(String fromUserName) {
        String access_token = accessTokenService.getAccessToken();
        MiniProgramPage miniProgramPage = new MiniProgramPage("测试小程序", "wx4e9dcf33a0d1f74a", "pages/auth/index", "DfYb6hy12M4EHKjosI_11ZxHfNW1HDayliGrOjDW-rijycQV5KR8N94hr5pwmF2C");
        String s = mpClient.sendCustomMessage(access_token, new MiniProgramPageRequestEntity(fromUserName, miniProgramPage));
        System.out.println("s = " + s);
        return null;
    }

    /**
     * 取消关注事件
     * @param fromUserName 发送方帐号（一个OpenID）
     * @return xml响应体
     */
    private XmlResponse unsubscribeEventHandler(String fromUserName) {
        return null;
    }

    // 被动回复用户消息
    // https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Passive_user_reply_message.html

    /**
     * 回复文本消息
     * @param toUserName 接收方帐号（收到的OpenID）
     * @param content 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
     * @return xml响应体
     * @throws CryptoException
     * @throws JsonProcessingException
     */
    private XmlResponse replyTextMsg(String toUserName, String content) throws CryptoException, JsonProcessingException {
        TextResponseEntity entity = new TextResponseEntity(toUserName, content);
        CryptoUtil cryptoUtil = new CryptoUtil(TOKEN, ENCODING_AES_KEY, APPID);
        return cryptoUtil.encryptMsg(entity);
    }

    /**
     * 回复图片消息
     * @param toUserName 接收方帐号（收到的OpenID）
     * @param image 图片对象
     * @return xml响应体
     * @throws CryptoException
     * @throws JsonProcessingException
     */
    private XmlResponse replyImageMsg(String toUserName, Image image) throws CryptoException, JsonProcessingException {
        ImageResponseEntity entity = new ImageResponseEntity(toUserName, image);
        CryptoUtil cryptoUtil = new CryptoUtil(TOKEN, ENCODING_AES_KEY, APPID);
        return cryptoUtil.encryptMsg(entity);
    }

    /**
     * 回复语音消息
     * @param toUserName 接收方帐号（收到的OpenID）
     * @param voice 语音对象
     * @return xml响应体
     * @throws CryptoException
     * @throws JsonProcessingException
     */
    private XmlResponse replyVoiceMsg(String toUserName, Voice voice) throws CryptoException, JsonProcessingException {
        VoiceResponseEntity entity = new VoiceResponseEntity(toUserName, voice);
        CryptoUtil cryptoUtil = new CryptoUtil(TOKEN, ENCODING_AES_KEY, APPID);
        return cryptoUtil.encryptMsg(entity);
    }

    /**
     * 回复视频消息
     * @param toUserName 接收方帐号（收到的OpenID）
     * @param video 视频对象
     * @return xml响应体
     * @throws CryptoException
     * @throws JsonProcessingException
     */
    private XmlResponse replyVideoMsg(String toUserName, Video video) throws CryptoException, JsonProcessingException {
        VideoResponseEntity entity = new VideoResponseEntity(toUserName, video);
        CryptoUtil cryptoUtil = new CryptoUtil(TOKEN, ENCODING_AES_KEY, APPID);
        return cryptoUtil.encryptMsg(entity);
    }

    /**
     * 回复音乐消息
     * @param toUserName 接收方帐号（收到的OpenID）
     * @param music 音乐对象
     * @return xml响应体
     * @throws CryptoException
     * @throws JsonProcessingException
     */
    private XmlResponse replyMusicMsg(String toUserName, Music music) throws CryptoException, JsonProcessingException {
        MusicResponseEntity entity = new MusicResponseEntity(toUserName, music);
        CryptoUtil cryptoUtil = new CryptoUtil(TOKEN, ENCODING_AES_KEY, APPID);
        return cryptoUtil.encryptMsg(entity);
    }

    /**
     * 回复图文消息
     * @param toUserName 接收方帐号（收到的OpenID）
     * @param articles 图文消息信息，注意，如果图文数超过限制，则将只发限制内的条数
     * @return xml响应体
     * @throws CryptoException
     * @throws JsonProcessingException
     */
    private XmlResponse replyNewsMsg(String toUserName, Article[] articles) throws CryptoException, JsonProcessingException {
        NewsResponseEntity entity = new NewsResponseEntity(toUserName, articles);
        CryptoUtil cryptoUtil = new CryptoUtil(TOKEN, ENCODING_AES_KEY, APPID);
        return cryptoUtil.encryptMsg(entity);
    }
}
