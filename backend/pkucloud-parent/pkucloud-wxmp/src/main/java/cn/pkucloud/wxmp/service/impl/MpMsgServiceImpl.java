package cn.pkucloud.wxmp.service.impl;

import cn.pkucloud.wxmp.dto.wx.xml.XmlResponse;
import cn.pkucloud.wxmp.service.MpMsgService;
import org.springframework.stereotype.Component;

@Component
public class MpMsgServiceImpl extends BaseMpMsgServiceImpl implements MpMsgService {
    @Override
    public XmlResponse textMsgHandler(String fromUserName, String content) {
        return null;
    }

    @Override
    public XmlResponse imageMsgHandler(String fromUserName, String picUrl, String imageMediaId) {
        return null;
    }

    @Override
    public XmlResponse voiceMsgHandler(String fromUserName, String voiceMediaId, String format, String recognition) {
        return null;
    }

    @Override
    public XmlResponse videoMsgHandler(String fromUserName, String videoMediaId, String videoThumbMediaId) {
        return null;
    }

    @Override
    public XmlResponse shortVideoMsgHandler(String fromUserName, String shortVideoMediaId, String shortVideoThumbMediaId) {
        return null;
    }

    @Override
    public XmlResponse locationMsgHandler(String fromUserName, double location_x, double location_y, int scale, String label) {
        return null;
    }

    @Override
    public XmlResponse linkMsgHandler(String fromUserName, String title, String description, String url) {
        return null;
    }

    @Override
    public XmlResponse subscribeEventHandler(String fromUserName) {
        return null;
    }

    @Override
    public XmlResponse unsubscribeEventHandler(String fromUserName) {
        return null;
    }
}
