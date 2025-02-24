package cn.galaxy.ft.server.handler;

import cn.galaxy.ft.codec.Codec;
import cn.galaxy.ft.protocol.FilePacket;
import cn.galaxy.ft.protocol.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultFileRegion;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.server.handler.FileSendServerHandler
 *
 * @author : gsq
 * @date : 2024-03-08 15:58
 * @note : It's not technology, it's art !
 **/
@Slf4j
public class FileSendServerHandler extends ChannelInboundHandlerAdapter {

    private final String downloadPath;

    public FileSendServerHandler(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        int type = byteBuf.getInt(0);
        if (type == Codec.TYPE) {
            Packet packet = Codec.INSTANCE.decode(byteBuf);
            if (packet instanceof FilePacket) {
                FilePacket filePacket = (FilePacket) packet;
                if(filePacket.getACK() != 0){
                    String filepath=this.downloadPath+filePacket.getFile();
                    // 创建Path对象
                    Path path = Paths.get(filepath);
                    // 检查文件是否存在
                    boolean exists = Files.exists(path);
                    if(!exists){
                        throw new RuntimeException("下载的文件："+filePacket.getFile()+"不存在");
                    }

                    filePacket.setFile(new File(filepath));
                    writeAndFlushFileRegion(ctx, filePacket);
                }
                else {
                    super.channelRead(ctx, packet);
                }
            } else {
                super.channelRead(ctx, packet);
            }
        }
    }

    private void writeAndFlushFileRegion(ChannelHandlerContext ctx, FilePacket packet) {

        File file = packet.getFile();
        DefaultFileRegion fileRegion = new DefaultFileRegion(file, 0, file.length());
        ctx.writeAndFlush(fileRegion).addListener(future -> {
            if (future.isSuccess()) {
                log.info("发送完成...");
                ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 异常处理器，处理由于读取或写入数据时产生的异常
        cause.printStackTrace();
        ctx.close(); // 关闭Channel
    }

}
