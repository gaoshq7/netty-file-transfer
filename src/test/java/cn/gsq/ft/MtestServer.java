package cn.gsq.ft;

import cn.galaxy.ft.FileTransferServer;
import cn.galaxy.ft.server.NettyFileTransferServer;

public class MtestServer {

    public static void main(String[] args) throws InterruptedException {
        FileTransferServer server = new NettyFileTransferServer("d://xyy//upload", "d://xyy//download");
        server.start();
    }

}
