package cn.pkucloud.wxa.netty;

import cn.pkucloud.wxa.service.WxaService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.regex.Pattern;

public class WebSocketSceneHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private final WxaService wxaService;

    public WebSocketSceneHandler(WxaService wxaService) {
        this.wxaService = wxaService;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            HttpHeaders headers = request.headers();
            String ip = headers.get("X-Real-IP");
            String ipListStr = headers.get("X-Forwarded-For");
            String ua = headers.get("User-Agent");
            if (Pattern.matches("^/ws/[0-9a-f]{32}$", uri)) {
                String scene = uri.substring(4, 36);
                boolean b = wxaService.connectScene(ip, ua, scene);
                if (b) {
                    super.channelRead(ctx, msg);
                } else {
                    ctx.close();
                }
            } else {
                ctx.close();
            }
        } else {
            ctx.close();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame) throws Exception {

    }
}
