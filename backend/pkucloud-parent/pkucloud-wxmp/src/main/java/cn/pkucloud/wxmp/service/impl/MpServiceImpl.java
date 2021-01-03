package cn.pkucloud.wxmp.service.impl;

import cn.pkucloud.common.Result;
import cn.pkucloud.wxmp.crypto.CryptoException;
import cn.pkucloud.wxmp.crypto.CryptoUtil;
import cn.pkucloud.wxmp.dto.wx.BaseResult;
import cn.pkucloud.wxmp.dto.wx.WxmpUserInfoDto;
import cn.pkucloud.wxmp.dto.wx.common.*;
import cn.pkucloud.wxmp.dto.wx.custom.MiniProgramPageRequestEntity;
import cn.pkucloud.wxmp.dto.wx.template.Keyword;
import cn.pkucloud.wxmp.dto.wx.template.Keywords;
import cn.pkucloud.wxmp.dto.wx.template.TemplateMessage;
import cn.pkucloud.wxmp.dto.wx.xml.*;
import cn.pkucloud.wxmp.entity.Auth;
import cn.pkucloud.wxmp.entity.PkuUserInfo;
import cn.pkucloud.wxmp.entity.WxUserInfo;
import cn.pkucloud.wxmp.entity.WxmpUserInfo;
import cn.pkucloud.wxmp.feign.AuthClient;
import cn.pkucloud.wxmp.feign.MpClient;
import cn.pkucloud.wxmp.mapper.AuthMapper;
import cn.pkucloud.wxmp.mapper.PkuUserInfoMapper;
import cn.pkucloud.wxmp.mapper.WxUserInfoMapper;
import cn.pkucloud.wxmp.mapper.WxmpUserInfoMapper;
import cn.pkucloud.wxmp.service.AccessTokenService;
import cn.pkucloud.wxmp.service.MpService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MpServiceImpl implements MpService {
    private final AccessTokenService accessTokenService;

    private final WxmpUserInfoMapper wxmpUserInfoMapper;

    private final AuthMapper authMapper;

    private final WxUserInfoMapper wxUserInfoMapper;

    private final PkuUserInfoMapper pkuUserInfoMapper;

    private final MpClient mpClient;

    private final AuthClient authClient;

    private final CryptoUtil cryptoUtil;

    public MpServiceImpl(AccessTokenService accessTokenService, WxmpUserInfoMapper wxmpUserInfoMapper, AuthMapper authMapper, WxUserInfoMapper wxUserInfoMapper, PkuUserInfoMapper pkuUserInfoMapper, MpClient mpClient, AuthClient authClient, CryptoUtil cryptoUtil) {
        this.accessTokenService = accessTokenService;
        this.wxmpUserInfoMapper = wxmpUserInfoMapper;
        this.authMapper = authMapper;
        this.wxUserInfoMapper = wxUserInfoMapper;
        this.pkuUserInfoMapper = pkuUserInfoMapper;
        this.mpClient = mpClient;
        this.authClient = authClient;
        this.cryptoUtil = cryptoUtil;
    }

    @Override
    public String echo(String signature, String echostr, int timestamp, String nonce) {
        boolean b = cryptoUtil.verify(signature, timestamp, nonce);
        if (b) {
            return echostr;
        }
        return null;
    }

    @Override
    public XmlResponse msgHandler(String signature, int timestamp, String nonce, String openid, String encrypt_type, String msg_signature, XmlRequest request) throws JsonProcessingException, CryptoException {
        String encrypt = request.getEncrypt();
        XmlRequestEntity entity = cryptoUtil.decryptMsg(timestamp, nonce, msg_signature, encrypt);
        System.out.println("entity = " + entity);
        String toUserName = entity.getToUserName();
        String fromUserName = entity.getFromUserName();
        int createTime = entity.getCreateTime();
        String msgType = entity.getMsgType();
        long msgId = entity.getMsgId();

        switch (msgType) {
            case "text":
                String content = entity.getContent();
                int bizmsgmenuid = entity.getBizmsgmenuid();
                return textMsgHandler(fromUserName, content, bizmsgmenuid);

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
                        String subscribeEventKey = entity.getEventKey();
                        String subscribeEventTicket = entity.getTicket();
                        return subscribeEventHandler(fromUserName, subscribeEventKey, subscribeEventTicket);

                    case "unsubscribe":
                        return unsubscribeEventHandler(fromUserName);

                    case "SCAN":
                        String scanEventKey = entity.getEventKey();
                        String scanTicket = entity.getTicket();
                        return scanEventHandler(fromUserName, scanEventKey, scanTicket);

                    case "LOCATION":
                        double latitude = entity.getLatitude();
                        double longitude = entity.getLongitude();
                        double precision = entity.getPrecision();
                        return locationEventHandler(fromUserName, latitude, longitude, precision);

                    case "CLICK":
                        String clickEventKey = entity.getEventKey();
                        return clickEventHandler(fromUserName, clickEventKey);

                    case "VIEW":
                        String viewEventKey = entity.getEventKey();
                        return viewEventHandler(fromUserName, viewEventKey);

                    case "TEMPLATESENDJOBFINISH":
                        String status = entity.getStatus();
                        return templateMsgEventHandler(fromUserName, status);

                    default:
                        System.out.println("event = " + event);
                        return null;
                }
            default:
                System.out.println("msgType = " + msgType);
                return null;
        }
    }

    @Override
    public Result<?> getSubscribe(Long id) {
        return null;
    }

    @Override
    public Result<?> sendAnswerMsg(List<String> idList, String first, String title, String userName, String time, String remark) {
        List<Auth> authList = authMapper.selectBatchIds(idList);
        ArrayList<String> wxUnionIdList = new ArrayList<>();
        for (Auth auth : authList) {
            String wxUnionId = auth.getWxUnionId();
            wxUnionIdList.add(wxUnionId);
        }
        List<WxUserInfo> wxUserInfoList = wxUserInfoMapper.selectBatchIds(wxUnionIdList);
        String access_token = accessTokenService.getAccessToken();
        for (WxUserInfo wxUserInfo : wxUserInfoList) {
            String mpOpenId = wxUserInfo.getMpOpenId();
            if (null == mpOpenId) {
                continue;
            }
            sendOneAnswerMsg(access_token, mpOpenId, first, title, userName, time, remark);
//            Keywords keywords = Keywords.builder()
//                    .first(new Keyword(first))
//                    .keyword1(new Keyword(title))
//                    .keyword2(new Keyword(userName))
//                    .keyword3(new Keyword(time))
//                    .remark(new Keyword(remark))
//                    .build();
//            TemplateMessage templateMessage = TemplateMessage.builder()
//                    .touser(mpOpenId)
//                    .template_id("EjI3qinEYZZoEKbC3VLCHYj0D0Mk5vQfWsvJysm1sbU")
//                    .data(keywords)
//                    .build();
//            mpClient.sendTemplateMessage(access_token, templateMessage);
        }
        return new Result<>();
    }

    @Async
    void sendOneAnswerMsg(String access_token, String mpOpenId, String first, String title, String userName, String time, String remark) {
        Keywords keywords = Keywords.builder()
                .first(new Keyword(first))
                .keyword1(new Keyword(title))
                .keyword2(new Keyword(userName))
                .keyword3(new Keyword(time))
                .remark(new Keyword(remark))
                .build();
        TemplateMessage templateMessage = TemplateMessage.builder()
                .touser(mpOpenId)
                .template_id("EjI3qinEYZZoEKbC3VLCHYj0D0Mk5vQfWsvJysm1sbU")
                .data(keywords)
                .build();
        mpClient.sendTemplateMessage(access_token, templateMessage);
    }

    private XmlResponse textMsgHandler(String fromUserName, String content, int bizmsgmenuid) throws CryptoException, JsonProcessingException {
        return replyTextMsg(fromUserName, content);
    }

    private XmlResponse imageMsgHandler(String fromUserName, String picUrl, String imageMediaId) throws CryptoException, JsonProcessingException {
        return replyImageMsg(fromUserName, new Image(imageMediaId));
    }

    private XmlResponse voiceMsgHandler(String fromUserName, String voiceMediaId, String format, String recognition) throws CryptoException, JsonProcessingException {
        return replyVoiceMsg(fromUserName, new Voice(voiceMediaId));
    }

    private XmlResponse videoMsgHandler(String fromUserName, String videoMediaId, String videoThumbMediaId) throws CryptoException, JsonProcessingException {
        return replyVideoMsg(fromUserName, new Video(videoMediaId, videoThumbMediaId, "视频标题", "视频描述"));
    }

    private XmlResponse shortVideoMsgHandler(String fromUserName, String shortVideoMediaId, String shortVideoThumbMediaId) throws CryptoException, JsonProcessingException {
        return replyVideoMsg(fromUserName, new Video(shortVideoMediaId, shortVideoThumbMediaId, "小视频标题", "小视频描述"));
    }

    private XmlResponse locationMsgHandler(String fromUserName, double location_x, double location_y, int scale, String label) throws CryptoException, JsonProcessingException {
        return replyTextMsg(fromUserName, "位置\n纬度：" + location_x + "\n经度：" + location_y + "\n缩放：" + scale + "\n信息：" + label);
    }

    private XmlResponse linkMsgHandler(String fromUserName, String title, String description, String url) throws CryptoException, JsonProcessingException {
        return replyNewsMsg(fromUserName, new Article[]{new Article(title, description, url, null)});
    }

    private XmlResponse subscribeEventHandler(String fromUserName, String subscribeEventKey, String subscribeEventTicket) throws CryptoException, JsonProcessingException {
        String access_token = accessTokenService.getAccessToken();
        WxmpUserInfoDto wxmpUserInfoDto = mpClient.getUserInfo(access_token, fromUserName, "zh_CN");
        String unionid = wxmpUserInfoDto.getUnionid();
        if (null == unionid) {
            return null;
        }

        WxmpUserInfo newWxmpUserInfo = new WxmpUserInfo(wxmpUserInfoDto);
        WxmpUserInfo oldWxmpUserInfo = wxmpUserInfoMapper.selectById(fromUserName);
        if (null == oldWxmpUserInfo) {
            wxmpUserInfoMapper.insert(newWxmpUserInfo);
        } else {
            wxmpUserInfoMapper.updateById(newWxmpUserInfo);
        }

        WxUserInfo newWxUserInfo = WxUserInfo.builder()
                .mpOpenId(fromUserName)
                .unionId(unionid)
                .build();
        WxUserInfo oldWxUserInfo = wxUserInfoMapper.selectById(unionid);
        if (null == oldWxUserInfo) {
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            newWxUserInfo.setRegister(timestamp);
            newWxUserInfo.setAccess(timestamp);
            wxUserInfoMapper.insert(newWxUserInfo);
        } else {
            if (null == oldWxUserInfo.getMpOpenId()) {
                wxUserInfoMapper.updateById(newWxUserInfo);
            }
        }

        Auth auth = getAuthByWxUnionId(unionid);
        if (null == auth) {
            String nickname = wxmpUserInfoDto.getNickname();
            MiniProgramPage miniProgramPage = new MiniProgramPage("登录未名云", "wx4e9dcf33a0d1f74a", "pages/auth/index", "XCiNRTOFqj0VCatvTzlC-7N_LlqJeJ6IlJKGdK2GIpM");
            BaseResult result = mpClient.sendCustomMessage(access_token, new MiniProgramPageRequestEntity(fromUserName, miniProgramPage));
            System.out.println("result = " + result);
            return replyTextMsg(fromUserName, "你好，" + nickname + "。欢迎关注WePKU！\n你还没有注册，请点击小程序注册。");
        }
        String pkuId = auth.getPkuId();

        PkuUserInfo pkuUserInfo = pkuUserInfoMapper.selectById(pkuId);
        if (null == pkuUserInfo) {
            return null;
        } else {
            String dept = pkuUserInfo.getDept();
            String name = pkuUserInfo.getName();
            return replyTextMsg(fromUserName, "你好，" + dept + " " + name + "。欢迎关注WePKU！");
        }
    }

    private XmlResponse unsubscribeEventHandler(String fromUserName) {
        WxmpUserInfo oldWxmpUserInfo = wxmpUserInfoMapper.selectById(fromUserName);
        if (null != oldWxmpUserInfo) {
            WxmpUserInfo newWxmpUserInfo = WxmpUserInfo.builder()
                    .openid(fromUserName)
                    .subscribe(0)
                    .build();
            wxmpUserInfoMapper.updateById(newWxmpUserInfo);
        }
        return null;
    }

    private XmlResponse scanEventHandler(String fromUserName, String scanEventKey, String scanTicket) {
        return null;
    }

    private XmlResponse locationEventHandler(String fromUserName, double latitude, double longitude, double precision) {
        return null;
    }

    private XmlResponse clickEventHandler(String fromUserName, String clickEventKey) {
        return null;
    }

    private XmlResponse viewEventHandler(String fromUserName, String viewEventKey) {
        return null;
    }

    private XmlResponse templateMsgEventHandler(String fromUserName, String status) {
        return null;
    }


    private XmlResponse replyTextMsg(String toUserName, String content) throws CryptoException, JsonProcessingException {
        TextResponseEntity entity = new TextResponseEntity(toUserName, content);
        return cryptoUtil.encryptMsg(entity);
    }

    private XmlResponse replyImageMsg(String toUserName, Image image) throws CryptoException, JsonProcessingException {
        ImageResponseEntity entity = new ImageResponseEntity(toUserName, image);
        return cryptoUtil.encryptMsg(entity);
    }

    private XmlResponse replyVoiceMsg(String toUserName, Voice voice) throws CryptoException, JsonProcessingException {
        VoiceResponseEntity entity = new VoiceResponseEntity(toUserName, voice);
        return cryptoUtil.encryptMsg(entity);
    }

    private XmlResponse replyVideoMsg(String toUserName, Video video) throws CryptoException, JsonProcessingException {
        VideoResponseEntity entity = new VideoResponseEntity(toUserName, video);
        return cryptoUtil.encryptMsg(entity);
    }

    private XmlResponse replyMusicMsg(String toUserName, Music music) throws CryptoException, JsonProcessingException {
        MusicResponseEntity entity = new MusicResponseEntity(toUserName, music);
        return cryptoUtil.encryptMsg(entity);
    }

    private XmlResponse replyNewsMsg(String toUserName, Article[] articles) throws CryptoException, JsonProcessingException {
        NewsResponseEntity entity = new NewsResponseEntity(toUserName, articles);
        return cryptoUtil.encryptMsg(entity);
    }

    private Auth getAuthByWxUnionId(String wxUnionId) {
        QueryWrapper<Auth> wrapper = new QueryWrapper<>();
        wrapper.eq("wx_union_id", wxUnionId);
        return authMapper.selectOne(wrapper);
    }

    private PkuUserInfo getPkuUserInfoById(String pkuId) {
        return pkuUserInfoMapper.selectById(pkuId);
    }

    private WxUserInfo getWxUserInfoById(String unionId) {
        return wxUserInfoMapper.selectById(unionId);
    }
}
