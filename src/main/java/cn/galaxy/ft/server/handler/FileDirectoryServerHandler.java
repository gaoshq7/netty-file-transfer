package cn.galaxy.ft.server.handler;

import cn.galaxy.ft.protocol.FileDictionaryPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;


public class FileDirectoryServerHandler extends ChannelInboundHandlerAdapter  {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(msg instanceof FileDictionaryPacket) {
            FileDictionaryPacket packet = (FileDictionaryPacket) msg;
            StringBuilder fileList = new StringBuilder();
            listFiles(new File(packet.getSourcePath()), fileList);
            packet.setRes(fileList.toString());
            packet.setRes(fileList.toString());
            ctx.writeAndFlush(packet);
        }else {
            ctx.writeAndFlush("Invalid command\n");
        }
    }

    private void listFiles(File directory, StringBuilder fileList) {
        File[] listOfFiles = directory.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    fileList.append(directory.getPath()+"\\"+file.getName()).append("\n");
                } else if (file.isDirectory()) {
                    fileList.append(file.getName()).append(" (directory)\n");
                }
            }
        }
    }
}
