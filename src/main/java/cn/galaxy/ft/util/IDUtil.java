package cn.galaxy.ft.util;

import java.util.UUID;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.util.IDUtil
 *
 * @author : gsq
 * @date : 2024-03-08 16:05
 * @note : It's not technology, it's art !
 **/
public final class IDUtil {

    /**
     * @Description : 随机短ID
     * @Param : []
     * @Return : java.lang.String
     * @Author : gsq
     * @Date : 16:06
     * @note : An art cell !
    **/
    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

}
