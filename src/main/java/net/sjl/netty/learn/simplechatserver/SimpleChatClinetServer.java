package net.sjl.netty.learn.simplechatserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Description: 简单聊天 - 客户端 - 服务启动
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/8/16
 */
public class SimpleChatClinetServer {

    private int port;

    private String host;

    public SimpleChatClinetServer(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void run() throws Exception {
        // 工作组
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // 客户端
            Bootstrap bootstrap = new Bootstrap();
            // 配置属性
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClinetChannelInitalizer())
                    .option(ChannelOption.SO_KEEPALIVE, true);
            // 启动客户端
            Channel channel = bootstrap.connect(host, port).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(true){
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
//            channel.closeFuture().sync();
        }
        catch (InterruptedException e) {
            workGroup.shutdownGracefully();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 3002;
        String host = "127.0.0.1";
        try {
            new SimpleChatClinetServer(port, host).run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
