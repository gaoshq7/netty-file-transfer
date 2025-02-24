package cn.galaxy.ft.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.codec.DecodeHandler
 *
 * @author : gsq
 * @date : 2024-03-08 15:48
 * @note : It's not technology, it's art !
 **/
public class DecodeHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) {
        list.add(Codec.INSTANCE.decode(byteBuf));
    }

}
