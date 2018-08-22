package net.sjl.netty.learn.introduction;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @Description: 入门Netty例子
 *
 * @Author:shijialei
 * @Version:1.0
 * @Date:2018/8/14
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * @Description: 该方法用来处理客户端的消息
     *
     * @Auther: shijialei
     * @Date: 2018/8/14 15:13
     * @Version: 1.0
     * @Param: [ctx, msg]
     * @Return: void
     * @Throws: [Exception]
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //Discard the received data silently
        //ByteBuf是一个引用计数对象实现ReferenceCounted，他就是在有对象引用的时候计数+1，无的时候计数-1，当为0对象释放内存
        StringBuffer message = new StringBuffer();
        ByteBuf repMessage = Unpooled.buffer();
        ByteBuf in=(ByteBuf)msg;
        try {
            while(in.isReadable()){
                message.append((char)in.readByte());
            }
            System.out.print(message.toString());
            repMessage.writeBytes(message.toString().getBytes());
            ctx.write(repMessage);
            ctx.flush();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * @Description:
     *
     * @Auther: shijialei
     * @Date: 2018/8/14 15:15
     * @Version: 1.0
     * @Param: [ctx, cause]
     * @Return: void
     * @Throws: [Exception]
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
