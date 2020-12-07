package cn.pkucloud.wxmp.service;

import cn.pkucloud.wxmp.dto.wx.xml.XmlResponse;

public interface MpMsgService extends BaseMpMsgService {
    XmlResponse textMsgHandler(String fromUserName, String content);

    XmlResponse imageMsgHandler(String fromUserName, String picUrl, String imageMediaId);

    XmlResponse voiceMsgHandler(String fromUserName, String voiceMediaId, String format, String recognition);

    XmlResponse videoMsgHandler(String fromUserName, String videoMediaId, String videoThumbMediaId);

    XmlResponse shortVideoMsgHandler(String fromUserName, String shortVideoMediaId, String shortVideoThumbMediaId);

    XmlResponse locationMsgHandler(String fromUserName, double location_x, double location_y, int scale, String label);

    XmlResponse linkMsgHandler(String fromUserName, String title, String description, String url);

    XmlResponse subscribeEventHandler(String fromUserName);

    XmlResponse unsubscribeEventHandler(String fromUserName);
}
