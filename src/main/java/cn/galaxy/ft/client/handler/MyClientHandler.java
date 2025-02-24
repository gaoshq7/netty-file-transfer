package cn.galaxy.ft.client.handler;

import cn.galaxy.ft.codec.Codec;
import cn.galaxy.ft.protocol.FilePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;
import java.io.IOException;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.client.handler.MyClientHandler
 *
 * @author : gsq
 * @date : 2024-03-08 16:44
 * @note : It's not technology, it's art !
 **/
public class MyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {
        System.out.println(msg);
        ByteBuf byteBuf = (ByteBuf) msg;
        FilePacket filePacket = (FilePacket) Codec.INSTANCE.decode(byteBuf);
        File file = filePacket.getFile();
        System.out.println("prepared send: " + file.getName());

        Channel channel = ctx.channel();
        channel.writeAndFlush(new ChunkedFile(filePacket.getFile()));
        // channel.writeAndFlush(new DefaultFileRegion(file, 0, file.length()));

    }

}
