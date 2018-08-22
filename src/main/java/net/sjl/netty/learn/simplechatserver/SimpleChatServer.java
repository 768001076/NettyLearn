package net.sjl.netty.learn.simplechatserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description: 简单聊天 - server
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/8/16
 */
public class SimpleChatServer {

    private int port;

    public SimpleChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // 接收客户端消息
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 消息处理
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // 服务端
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 添加属性
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleChatServerChannelInitalizer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 启动服务
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            // 等待关闭
            channelFuture.channel().closeFuture().sync();
        }
        catch (Exception e) {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 3002;
        try {
            new SimpleChatServer(port).run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
