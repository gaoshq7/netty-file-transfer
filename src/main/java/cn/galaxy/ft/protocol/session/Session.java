package cn.galaxy.ft.protocol.session;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.protocol.session.Session
 *
 * @author : gsq
 * @date : 2024-03-08 16:09
 * @note : It's not technology, it's art !
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    String nodeId;
    String nodeName;

    @Override
    public String toString() {
        return "Session{" +
                "nodeName='" + nodeName + "-" + nodeId + '\'' +
                '}';
    }

}
