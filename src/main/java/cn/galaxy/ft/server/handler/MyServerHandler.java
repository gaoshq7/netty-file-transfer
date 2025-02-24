package cn.galaxy.ft.server.handler;

import cn.galaxy.ft.codec.Codec;
import cn.galaxy.ft.protocol.FilePacket;
import cn.galaxy.ft.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.server.handler.MyServerHandler
 *
 * @author : gsq
 * @date : 2024-03-08 16:15
 * @note : It's not technology, it's art !
 **/
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    public static File file;
    public static FileOutputStream outputStream;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        ByteBuf byteBuf = (ByteBuf) msg;
        int type = byteBuf.getInt(0);
        if (type != Codec.TYPE) {
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            outputStream.write(bytes);
            byteBuf.release();
        } else {
            Packet packet = Codec.INSTANCE.decode(byteBuf);
            if (packet instanceof FilePacket) {
                FilePacket filePacket = (FilePacket) packet;
                outputStream = new FileOutputStream(new File("./receive-" + filePacket.getFile().getName()));

                ByteBuf byteBuf1 = ctx.channel().alloc().ioBuffer();
                Codec.INSTANCE.encode(byteBuf1, filePacket);
                ctx.channel().writeAndFlush(byteBuf1);
            }
        }

    }

}
