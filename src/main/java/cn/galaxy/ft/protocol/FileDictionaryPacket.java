package cn.galaxy.ft.protocol;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static cn.galaxy.ft.protocol.Constant.FILE_DICTIONARY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDictionaryPacket extends Packet{
    String sourcePath;
    String res;
    @Override
    public Byte getCommand() {
        return FILE_DICTIONARY;
    }

    public FileDictionaryPacket(String sourcePath) {
        this.sourcePath=sourcePath;
    }
}
