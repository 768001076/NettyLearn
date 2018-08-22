package net.sjl.netty.learn.introduction;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class TimeClient {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 3625;

        NioEventLoopGroup workGroup = new NioEventLoopGroup();// 工作组
        try {
            Bootstrap bootstrap = new Bootstrap();// 客户端
            bootstrap.group(workGroup)// 绑定组
                    .channel(NioSocketChannel.class)// 设置通道
                    .option(ChannelOption.SO_KEEPALIVE, true)// 心跳机制
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeDecoder(), new TimeClientHandler());// 添加处理逻辑
                        }
                    });
            // 启动客户端
            ChannelFuture connect = bootstrap.connect(host, port).sync();
            // 等待链接关闭
            connect.channel().closeFuture().sync();
        }
        catch (InterruptedException e) {
            workGroup.shutdownGracefully();
            e.printStackTrace();
        }

    }

}
