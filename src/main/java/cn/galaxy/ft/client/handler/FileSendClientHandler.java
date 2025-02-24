package cn.galaxy.ft.client.handler;

import cn.galaxy.ft.codec.Codec;
import cn.galaxy.ft.protocol.FilePacket;
import cn.galaxy.ft.protocol.Packet;
import cn.hutool.core.thread.ThreadUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultFileRegion;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.client.handler.FileSendClientHandler
 *
 * @author : gsq
 * @date : 2024-03-08 16:38
 * @note : It's not technology, it's art !
 **/
@Slf4j
@ChannelHandler.Sharable
public class FileSendClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        int type = byteBuf.getInt(0);
        if (type == Codec.TYPE) {
            Packet packet = Codec.INSTANCE.decode(byteBuf);
            if (packet instanceof FilePacket) {
                FilePacket filePacket = (FilePacket) packet;
                if (filePacket.getACK() != 0) {
                    writeAndFlushFileRegion(ctx, filePacket);
                } else {
                    super.channelRead(ctx, packet);
                }
            } else {
                super.channelRead(ctx, packet);
            }
        } else {
//            System.out.println("无法识别此类数据包");
            log.info("无法识别此类数据包");
        }
    }

    private void writeAndFlushFileRegion(ChannelHandlerContext ctx, FilePacket packet) {
        File file = packet.getFile();
        DefaultFileRegion fileRegion = new DefaultFileRegion(file, 0, file.length());
        ctx.writeAndFlush(fileRegion).addListener(future -> {
            if (future.isSuccess()) {
//                System.out.println("发送完成...");
                log.info("发送完成...");
                ThreadUtil.safeSleep(2000);
                ctx.channel().close();
            }
        });
    }

}