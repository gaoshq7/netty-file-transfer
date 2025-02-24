package cn.gsq.ft;

import cn.galaxy.ft.FileTransferClient;
import cn.galaxy.ft.client.NettyFileTransferClient;
import cn.hutool.core.thread.ThreadUtil;

public class MtestClient {

    public static void main(String[] args)  {
        FileTransferClient client = new NettyFileTransferClient();
        int time = 4;
//        download(client, time);
        upload(client, time);
    }

    static void download(FileTransferClient client, int time) {
        for (int i = 0; i<time; i++) {
            int finalI = i;
            new Thread(() -> {
                ThreadUtil.safeSleep(2000);
                try {
                    client.download("hhh/hadoop-auth-3.2.1.jar", "/Users/gsq/Downloads/" + finalI, "127.0.0.1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    static void upload(FileTransferClient client, int time) {
        for (int i = 0; i<time; i++) {
            int finalI = i;
            new Thread(() -> {
                ThreadUtil.safeSleep(2000);
                try {
                    client.upload("d://xyy//word"+ finalI +".txt", "127.0.0.1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
