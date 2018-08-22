package net.sjl.netty.learn.simplechatserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description: 简单聊天 - 客户端 - 处理逻辑
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/8/16
 */
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {


    @Override protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println(msg);
    }
}
