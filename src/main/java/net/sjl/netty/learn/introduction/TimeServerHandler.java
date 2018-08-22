package net.sjl.netty.learn.introduction;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description: 时间服务处理逻辑
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/8/16
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        ByteBuf time = ctx.alloc().buffer(4);// 创建消息缓冲
        long l = System.currentTimeMillis() / 1000L + 2208988800L;
        System.out.println(l);
        time.writeInt((int)l);// 写入时间
        final ChannelFuture channelFuture = ctx.writeAndFlush(time);// 发送时间信息
        channelFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                assert channelFuture == future; // 如果释放的通道等于发送时间信息的通道
                ctx.close(); // 关闭
            }
        });

    }

    @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
