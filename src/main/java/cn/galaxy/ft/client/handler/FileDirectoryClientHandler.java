package cn.galaxy.ft.client.handler;

import cn.galaxy.ft.protocol.FileDictionaryPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class FileDirectoryClientHandler extends ChannelInboundHandlerAdapter {
    private String result;

    public String getResult() {
        return result;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FileDictionaryPacket){
            FileDictionaryPacket packet = (FileDictionaryPacket) msg;
            result=packet.getRes();
            ctx.close();
        }else {
            super.channelRead(ctx,msg);
        }

    }

}
