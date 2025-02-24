package cn.galaxy.ft.util;

import cn.galaxy.ft.protocol.attribute.Attributes;
import cn.galaxy.ft.protocol.session.Session;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.util.SessionUtil
 *
 * @author : gsq
 * @date : 2024-03-08 16:06
 * @note : It's not technology, it's art !
 **/
public final class SessionUtil {

    private static final Map<String, Channel> NODE_ID_CHANNEL_MAP = new HashMap<>();

    /**
     * @Description :绑定session与channel
     * @Param : [session, channel]
     * @Return : void
     * @Author : gsq
     * @Date : 09:14
     * @note : An art cell !
    **/
    public static void bindSession(Session session, Channel channel) {
        NODE_ID_CHANNEL_MAP.put(session.getNodeId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * @Description : 解除绑定
     * @Param : [channel]
     * @Return : void
     * @Author : gsq
     * @Date : 09:15
     * @note : An art cell !
    **/
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            NODE_ID_CHANNEL_MAP.remove(session.getNodeId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * @Description : 是否加入主机列表
     * @Param : [channel]
     * @Return : boolean
     * @Author : gsq
     * @Date : 09:17
     * @note : An art cell !
    **/
    private static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    /**
     * @Description : 根据session获取channel
     * @Param : [channel]
     * @Return : cn.galaxy.ft.protocol.session.Session
     * @Author : gsq
     * @Date : 09:16
     * @note : An art cell !
    **/
    private static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    /**
     * @Description : 获取session集合映射
     * @Param : []
     * @Return : java.util.Map
     * @Author : gsq
     * @Date : 09:17
     * @note : An art cell !
    **/
    public static Map getNodeIdChannelMap() {
        return NODE_ID_CHANNEL_MAP;
    }

}
