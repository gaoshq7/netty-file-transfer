package cn.galaxy.ft.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

import static cn.galaxy.ft.protocol.Constant.FILE_PACKET;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.protocol.FilePacket
 *
 * @author : gsq
 * @date : 2024-03-08 15:31
 * @note : It's not technology, it's art !
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilePacket extends Packet {

    File file;

    int ACK;

    @Override
    public Byte getCommand() {
        return FILE_PACKET;
    }

    public FilePacket(File file) {
        this.file = file;
    }

}
