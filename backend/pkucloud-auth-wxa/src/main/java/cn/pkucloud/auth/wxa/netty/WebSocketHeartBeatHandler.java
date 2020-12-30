package cn.pkucloud.auth.wxa.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class WebSocketHeartBeatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
                    System.out.println("read idle triggered");
                    break;
                case WRITER_IDLE:
                    System.out.println("write idle triggered");
                    break;
                case ALL_IDLE:
                    ctx.close();
                    System.out.println("all idle triggered");
                    break;
                default:
                    break;
            }
        }

    }
}
