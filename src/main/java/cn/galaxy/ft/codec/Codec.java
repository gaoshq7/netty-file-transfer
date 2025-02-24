package cn.galaxy.ft.codec;

import cn.galaxy.ft.protocol.FileDictionaryPacket;
import cn.galaxy.ft.protocol.FilePacket;
import cn.galaxy.ft.protocol.Packet;
import cn.galaxy.ft.protocol.request.LoginPacket;
import cn.galaxy.ft.protocol.response.LoginResponsePacket;
import cn.galaxy.ft.protocol.serilizer.Serilizer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static cn.galaxy.ft.protocol.Constant.*;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.codec.Codec
 *
 * @author : gsq
 * @date : 2024-03-08 15:22
 * @note : It's not technology, it's art !
 **/
public class Codec {

    public static final int TYPE = 0x12345678;

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;

    public static Codec INSTANCE = new Codec();

    private Codec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(FILE_PACKET, FilePacket.class);
        packetTypeMap.put(LOGIN_PACKET_REQUEST, LoginPacket.class);
        packetTypeMap.put(LOGIN_PACKET_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(FILE_DICTIONARY, FileDictionaryPacket.class);
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        byte[] bytes = Serilizer.DEFAULT.serilize(packet);
        byteBuf.writeInt(TYPE);
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        // return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        byteBuf.readInt();
        Byte command = byteBuf.readByte();
        int len = byteBuf.readInt();
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);

        Class clazz = packetTypeMap.get(command);
        if (clazz == null) {
            throw new NullPointerException("解析失败，没有该类型的数据包");
        }

        return (Packet) Serilizer.DEFAULT.deSerilize(bytes, clazz);

    }

}
