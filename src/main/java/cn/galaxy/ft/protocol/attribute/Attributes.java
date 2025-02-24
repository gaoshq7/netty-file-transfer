package cn.galaxy.ft.protocol.attribute;

import cn.galaxy.ft.protocol.session.Session;
import io.netty.util.AttributeKey;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.protocol.attribute.Attributes
 *
 * @author : gsq
 * @date : 2024-03-08 16:08
 * @note : It's not technology, it's art !
 **/
public interface Attributes {

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
