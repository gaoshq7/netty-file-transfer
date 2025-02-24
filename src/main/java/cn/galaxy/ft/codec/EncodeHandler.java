package cn.galaxy.ft.codec;

import cn.galaxy.ft.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.codec.EncodeHandler
 *
 * @author : gsq
 * @date : 2024-03-08 15:49
 * @note : It's not technology, it's art !
 **/
@ChannelHandler.Sharable
public class EncodeHandler extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf byteBuf) {
        Codec.INSTANCE.encode(byteBuf, (Packet) o);
    }

}
