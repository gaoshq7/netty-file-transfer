package cn.gsq.ft;

import cn.galaxy.ft.FileTransferClient;
import cn.galaxy.ft.FileTransferServer;
import cn.galaxy.ft.client.NettyFileTransferClient;
import cn.galaxy.ft.server.NettyFileTransferServer;
import cn.hutool.core.thread.ThreadUtil;
import org.junit.Test;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.FileTransferTest
 *
 * @author : gsq
 * @date : 2024-03-08 15:04
 * @note : It's not technology, it's art !
 **/
public class FileTransferTest {

    @Test
    public void server() throws InterruptedException {
        FileTransferServer server = new NettyFileTransferServer("d:\\xyy\\upload", "d:\\xyy\\upload");
        server.start();
    }

    @Test
    public void client() throws InterruptedException {
//        FileTransferClient client = new NettyFileTransferClient();
//        client.upload("d:\\xyy\\word1.txt");
//        List<String> fileList = client.getDirection("d:\\xyy","127.0.0.1");
//        System.out.println("Files in directory:");
//        for (String file : fileList) {
//            System.out.println(file);
//        }
//        System.out.println("*****************************************************");
//        List<String> fileList1 = client.getDirection("d:\\docker","127.0.0.1");
//        for (String file : fileList1) {
//            System.out.println(file);
//        }
//        client.download("word0.txt","d:\\xyy\\xx","127.0.0.1");
//        client.download("word0.txt","d:\\xyy\\xx","127.0.0.1");
//        FileTransferClient client = new NettyFileTransferClient();
//        for (int i = 0; i < 10; i++) {
//            int finalI1 = i;
//            new Thread(
//                    ()->{
//                        try {
//                            client.download("word0.txt","d:\\xyy\\xx"+ finalI1,"127.0.0.1");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//            ).start();
//        }
//
//        ThreadUtil.safeSleep(10000);
    }

}
