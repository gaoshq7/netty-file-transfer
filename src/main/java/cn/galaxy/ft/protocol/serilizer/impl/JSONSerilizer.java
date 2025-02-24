package cn.galaxy.ft.protocol.serilizer.impl;

import cn.galaxy.ft.protocol.serilizer.Serilizer;
import com.alibaba.fastjson.JSON;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.protocol.serilizer.impl.JSONSerilizer
 *
 * @author : gsq
 * @date : 2024-03-08 15:42
 * @note : It's not technology, it's art !
 **/
public class JSONSerilizer implements Serilizer {

    @Override
    public byte[] serilize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deSerilize(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }

}
