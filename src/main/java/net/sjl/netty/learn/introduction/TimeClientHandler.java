package net.sjl.netty.learn.introduction;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @Description: 时间服务客户端处理逻辑
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/8/16
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UnixTime timeMessage = (UnixTime) msg;
        try {
            System.out.println(timeMessage);
            ctx.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
