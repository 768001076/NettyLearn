package net.sjl.netty.learn.introduction;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TimeDecoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
            throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        long beforeTime = byteBuf.readUnsignedInt();
        list.add(new UnixTime((beforeTime - 2208988800L) * 1000L));
    }
}
