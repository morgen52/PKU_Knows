package cn.pkucloud.wxmp.service;

import cn.pkucloud.wxmp.dto.wx.xml.XmlResponse;

public interface BaseMpMsgService {
    XmlResponse replyTextMsg(String toUserName, String content);

    XmlResponse replyImageMsg(String toUserName, String mediaId);

    XmlResponse replyVoiceMsg(String toUserName, String mediaId);

    XmlResponse replyVideoMsg(String toUserName, String mediaId, String title, String description);

    XmlResponse replyMusicMsg(String toUserName, String title, String description, String musicUrl, String hqMusicUrl, String thumbMediaId);

    XmlResponse replyNewsMsg(String toUserName, int articleCount);
}
