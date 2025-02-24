package cn.galaxy.ft.server.handler;

import cn.galaxy.ft.codec.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.server.handler.FileReceiveServerHandler
 *
 * @author : gsq
 * @date : 2024-03-08 15:17
 * @note : It's not technology, it's art !
 **/
@Slf4j
public class FileReceiveServerHandler extends ChannelInboundHandlerAdapter {

    private FileOutputStream outputStream;

    @Setter
    private long fileLength;

    private long readLength;

    @Setter
    private String path; // 存储客户端传输的文件名

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (outputStream == null) {
            // 创建输出流，使用客户端传输的文件名
            outputStream = new FileOutputStream(path);
        }

        ByteBuf byteBuf = (ByteBuf) msg;
        int type = byteBuf.getInt(0);
        if (type != Codec.TYPE) {
            readLength += byteBuf.readableBytes();
            writeToFile(byteBuf);
            sendComplete(readLength);
        } else {
            super.channelRead(ctx, msg);
        }
    }

    private void writeToFile(ByteBuf byteBuf) throws IOException {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        outputStream.write(bytes);
        byteBuf.release();
    }

    private void sendComplete(long readLength) throws IOException {
        if (readLength >= fileLength) {
            log.info("文件接收完成...");
            outputStream.close();
            // 重置状态，准备接收下一个文件
            outputStream = null;
            fileLength = 0;
            readLength = 0;
            path = null; // 清除文件名，以便接收下一个文件
        }
    }
}
