package cn.galaxy.ft.server.handler;

import cn.galaxy.ft.protocol.FilePacket;
import cn.hutool.core.io.FileUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.server.handler.FilePacketServerHandler
 *
 * @author : gsq
 * @date : 2024-03-08 16:13
 * @note : It's not technology, it's art !
 **/
@Slf4j
public class FilePacketServerHandler extends SimpleChannelInboundHandler<FilePacket> {

    private final String uploadPath;

    public FilePacketServerHandler(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FilePacket packet) throws Exception {
        File file = packet.getFile();
        log.info("从客户端接收文件: " + file.getName());
        long fileLength = file.length();
        mkdir();

        packet.setACK(packet.getACK() + 1);
        ctx.writeAndFlush(packet);

        // 创建一个新的处理器，用于处理当前文件的接收
        FileReceiveServerHandler handler = new FileReceiveServerHandler();
        handler.setFileLength(fileLength);
        handler.setPath(this.uploadPath + "/" + file.getName());

        ctx.pipeline().addFirst(handler);
        // 将事件传递给下一个处理器
        ctx.fireChannelRead(packet);

    }

    private void mkdir() {
        if(!FileUtil.exist(this.uploadPath)) {
            FileUtil.mkdir(this.uploadPath);
        }
    }

}
