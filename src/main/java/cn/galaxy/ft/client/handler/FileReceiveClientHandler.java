package cn.galaxy.ft.client.handler;

import cn.galaxy.ft.codec.Codec;
import cn.hutool.core.io.FileUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.client.handler.FileReceiveClientHandler
 *
 * @author : gsq
 * @date : 2024-03-08 16:37
 * @note : It's not technology, it's art !
 **/
@Slf4j
public class FileReceiveClientHandler extends ChannelInboundHandlerAdapter {

    private FileOutputStream outputStream;

    private String downloadPath;

    public FileReceiveClientHandler(String downloadPath) throws FileNotFoundException {
        this.downloadPath = downloadPath;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof ByteBuf) {
            // 处理接收到的数据
            ByteBuf byteBuf = (ByteBuf) msg;
            int type = byteBuf.getInt(0);
            if (type != Codec.TYPE) {
                writeToFile(byteBuf);
            } else {
                super.channelRead(ctx, msg);
            }
        } else {
            // 接收到结束标志
            log.info("接收完成...");
//            System.out.println("接收完成...");
            ctx.close();
        }
    }

    private void mkdir() {
        File file = new File(this.downloadPath);
        String name = file.getName();
        String substring = this.downloadPath.substring(0, this.downloadPath.length() - name.length() - 1);
        if(!FileUtil.exist(substring)) {
            FileUtil.mkdir(substring);
        }
    }


    private void writeToFile(ByteBuf byteBuf) throws IOException {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        mkdir();
        if(this.outputStream==null){
            this.outputStream=new FileOutputStream(downloadPath);
        }
        outputStream.write(bytes);
        byteBuf.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
