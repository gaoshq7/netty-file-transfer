package cn.galaxy.ft.server.handler;

import cn.galaxy.ft.protocol.attribute.Attributes;
import cn.galaxy.ft.protocol.request.LoginPacket;
import cn.galaxy.ft.protocol.response.LoginResponsePacket;
import cn.galaxy.ft.protocol.session.Session;
import cn.galaxy.ft.util.IDUtil;
import cn.galaxy.ft.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.server.handler.JoinClusterRequestHandler
 *
 * @author : gsq
 * @date : 2024-03-08 16:02
 * @note : It's not technology, it's art !
 **/
@Slf4j
public class JoinClusterRequestHandler extends SimpleChannelInboundHandler<LoginPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginPacket loginPacket) {
        String id = IDUtil.randomId();
        log.debug(new Date() + " [" + loginPacket.getName() + "]加入集群");
        SessionUtil.bindSession(new Session(id, loginPacket.getName()), ctx.channel());

        ctx.writeAndFlush(new LoginResponsePacket(id, loginPacket.getName()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        log.info(new Date() + " " +ctx.channel().attr(Attributes.SESSION).get() + "退出集群");
    }

}
