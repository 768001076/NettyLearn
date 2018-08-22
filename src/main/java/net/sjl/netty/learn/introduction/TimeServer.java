package net.sjl.netty.learn.introduction;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description: 时间服务
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/8/16
 */
public class TimeServer {

    private int port;

    public TimeServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);// 结束客户端消息
        EventLoopGroup workGroup = new NioEventLoopGroup();// 处理客户端消息
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();// 服务端
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new TimeServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture sync = serverBootstrap.bind(port).sync();
            sync.channel().closeFuture().sync();
        }
        catch (Exception e) {
            //资源优雅释放
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 3625;
        try {
            new TimeServer(port).run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
