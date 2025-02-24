package cn.galaxy.ft.client.handler;

import cn.galaxy.ft.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.client.handler.LoginResponseHandler
 *
 * @author : gsq
 * @date : 2024-03-08 16:40
 * @note : It's not technology, it's art !
 **/
@Slf4j
@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket packet) {
        log.debug(new Date() + " " + packet.getId() + " " + packet.getName() + " 登陆成功");
//        System.out.println(new Date() + " " + packet.getId() + " " + packet.getName() + " 登陆成功");
    }

}