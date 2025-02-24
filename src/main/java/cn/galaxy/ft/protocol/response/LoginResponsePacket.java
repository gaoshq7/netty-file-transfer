package cn.galaxy.ft.protocol.response;

import cn.galaxy.ft.protocol.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static cn.galaxy.ft.protocol.Constant.LOGIN_PACKET_RESPONSE;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.protocol.response.LoginResponsePacket
 *
 * @author : gsq
 * @date : 2024-03-08 15:39
 * @note : It's not technology, it's art !
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponsePacket extends Packet {

    String id;
    String name;

    @Override
    public Byte getCommand() {
        return LOGIN_PACKET_RESPONSE;
    }

}
