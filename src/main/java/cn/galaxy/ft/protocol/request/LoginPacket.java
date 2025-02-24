package cn.galaxy.ft.protocol.request;

import cn.galaxy.ft.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static cn.galaxy.ft.protocol.Constant.LOGIN_PACKET_REQUEST;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.protocol.request.LoginPacket
 *
 * @author : gsq
 * @date : 2024-03-08 15:35
 * @note : It's not technology, it's art !
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginPacket extends Packet {

    String name;

    String id;

    @Override
    public Byte getCommand() {
        return LOGIN_PACKET_REQUEST;
    }

    public LoginPacket(String name) {
        this.name = name;
    }

}
