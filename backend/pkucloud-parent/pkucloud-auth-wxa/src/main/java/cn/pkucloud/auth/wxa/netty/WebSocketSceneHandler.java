package cn.pkucloud.auth.wxa.netty;

import cn.pkucloud.auth.wxa.service.WxaAuthService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSocketSceneHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private final WxaAuthService wxaAuthService;

    public WebSocketSceneHandler(WxaAuthService wxaAuthService) {
        this.wxaAuthService = wxaAuthService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            Pattern pattern = Pattern.compile("^/ws/[0-9a-f]{32}$");
            Matcher matcher = pattern.matcher(uri);
            if (matcher.matches()) {
                String scene = uri.substring(4, 36);
                HttpHeaders headers = request.headers();
                String ip = headers.get("X-Real-IP");
                String ipListStr = headers.get("X-Forwarded-For");
                String ua = headers.get("User-Agent");
                boolean b = wxaAuthService.connectScene(ip, ua, scene);
                if (true) {
                    SceneChannelMap.put(scene, ctx.channel());
                    request.setUri("/ws");
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
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("WebSocketSceneHandler.exceptionCaught");
        String channelIdStr = ctx.channel().id().asLongText();
        String scene = SceneChannelMap.removeByChannelIdStr(channelIdStr);
        wxaAuthService.deleteScene(scene);
        ctx.close();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("WebSocketSceneHandler.handlerRemoved");
        String channelIdStr = ctx.channel().id().asLongText();
        String scene = SceneChannelMap.removeByChannelIdStr(channelIdStr);
        wxaAuthService.deleteScene(scene);
    }
}
