package net.sjl.netty.learn.simplechatserver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.SocketAddress;

/**
 * @Description: 简单聊天 - 处理逻辑
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/8/16
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();// 链接通道
        SocketAddress socketAddress = channel.remoteAddress();// 客户地址
        channels.writeAndFlush("[Server]:" + socketAddress + ":加入聊天\n");
        channels.add(ctx.channel());
    }

    @Override public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();// 链接通道
        SocketAddress socketAddress = channel.remoteAddress();// 客户地址
        channels.writeAndFlush("[Server]:" + socketAddress + ":退出聊天\n");
    }

    @Override protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channelThisTime = ctx.channel();
        for (Channel channel : channels) {
            if (channelThisTime == channel) {
                channel.writeAndFlush("[your]:" + msg + "\n");
            } else {
                channel.writeAndFlush("[" + channelThisTime.remoteAddress() + "]:" + msg + "\n");
            }
        }
    }

    @Override public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();// 链接通道
        SocketAddress socketAddress = channel.remoteAddress();// 客户地址
        System.out.println("[Server]:" + socketAddress + ":在线");
    }

    @Override public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();// 链接通道
        SocketAddress socketAddress = channel.remoteAddress();// 客户地址
        System.out.println("[Server]:" + socketAddress + ":掉线");
    }

    @Override public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
