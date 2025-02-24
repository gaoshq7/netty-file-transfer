package cn.galaxy.ft.protocol.serilizer;

import cn.galaxy.ft.protocol.serilizer.impl.JSONSerilizer;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.protocol.serilizer.Serilizer
 *
 * @author : gsq
 * @date : 2024-03-08 15:41
 * @note : It's not technology, it's art !
 **/
public interface Serilizer {

    Serilizer DEFAULT = new JSONSerilizer();

    byte[] serilize(Object object);

    <T> T deSerilize(byte[] bytes, Class<T> clazz);

}
