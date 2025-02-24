package cn.galaxy.ft;

import cn.galaxy.ft.protocol.Constant;

import java.util.List;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.FileTransferClient
 *
 * @author : gsq
 * @date : 2024-03-11 13:49
 * @note : It's not technology, it's art !
 **/
public abstract class FileTransferClient {
    public abstract void upload(String sourcePath,String ip,int port );

    public  void upload(String sourcePath,String ip ){
        upload(sourcePath,ip,Constant.DEFAULT_SERVER_PORT);
    }

    public abstract void download(String filename,String destPath,String ip,int port ) throws Exception;

    public  void download(String filename,String destPath,String ip) throws Exception {
        download(filename, destPath, ip, Constant.DEFAULT_SERVER_PORT);
    }

    public abstract List<String> getDirection(String sourcePath,String ip,int port);
    public  List<String> getDirection(String sourcePath,String ip){
        return getDirection( sourcePath, ip,  Constant.DEFAULT_SERVER_PORT);
    }
}
