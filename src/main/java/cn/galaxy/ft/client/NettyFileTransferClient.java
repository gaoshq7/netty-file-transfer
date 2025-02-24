package cn.galaxy.ft.client;

import cn.galaxy.ft.FileTransferClient;
import cn.galaxy.ft.client.handler.FileDirectoryClientHandler;
import cn.galaxy.ft.client.handler.FileReceiveClientHandler;
import cn.galaxy.ft.client.handler.FileSendClientHandler;
import cn.galaxy.ft.client.handler.LoginResponseHandler;
import cn.galaxy.ft.codec.DecodeHandler;
import cn.galaxy.ft.codec.EncodeHandler;
import cn.galaxy.ft.protocol.FileDictionaryPacket;
import cn.galaxy.ft.protocol.FilePacket;
import cn.galaxy.ft.protocol.request.LoginPacket;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.system.SystemUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.client.FileTransferClient
 *
 * @author : gsq
 * @date : 2024-03-08 13:56
 * @note : It's not technology, it's art !
 **/
public class NettyFileTransferClient extends FileTransferClient {

    private Channel channel;
    private NioEventLoopGroup group;

    /**
     * @Description : 链接文件服务器
     * @Param : [host, port]
     * @Return : void
     * @Author : gsq
     * @Date : 16:47
     * @note : An art cell !
    **/
    public Bootstrap initBootstrap()  {
        Bootstrap bootstrap=new Bootstrap();
         group= new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);
        return bootstrap;
    }

    /**
     * @Description : 验证合法性
     * @Param : []
     * @Return : void
     * @Author : gsq
     * @Date : 09:43
     * @note : An art cell !
    **/
    private void verify() {
        if(this.channel != null && this.channel.isOpen()) {
            LoginPacket loginPacket = new LoginPacket(SystemUtil.getHostInfo().getName());
            channel.writeAndFlush(loginPacket);
            ThreadUtil.safeSleep(2000);
        }
    }

    /**
     * @Description : 上传文件
     * @Param : [filePaths]
     * @Return : void
     * @Author : gsq
     * @Date : 16:53
     * @note : An art cell !
    **/
    public void upload(String... filePaths) {
        if(this.channel == null || !this.channel.isOpen()) {
            throw new RuntimeException("服务链接已关闭，请重新链接!");
        }
        for(String filePath : CollUtil.toList(filePaths)) {
            File file = new File(filePath);
            FilePacket filePacket = new FilePacket(file);
            this.channel.writeAndFlush(filePacket);
        }
    }

    /**
     * @Description : 关闭与服务器的链接
     * @Param : []
     * @Return : void
     * @Author : gsq
     * @Date : 16:55
     * @note : An art cell !
    **/
    public void close() {
        if(this.channel != null) {
            channel.close();
            this.channel = null;
        }
    }

    @Override
    public void upload(String sourcePath,String ip,int port) {
        NettyFileTransferClient nettyFileTransferClient = new NettyFileTransferClient();
        nettyFileTransferClient.doUpload(sourcePath, ip, port);
    }

    @Override
    public void download(String filename, String destPath,String ip,int port) throws Exception {
        NettyFileTransferClient nettyFileTransferClient = new NettyFileTransferClient();
        nettyFileTransferClient.doDownload(filename, destPath, ip, port);
    }

    @Override
    public List<String> getDirection(String sourcePath,String ip,int port)  {
        NettyFileTransferClient nettyFileTransferClient = new NettyFileTransferClient();
        return nettyFileTransferClient.doGetDirection(sourcePath,  ip, port);
    }

    private void doUpload(String sourcePath, String ip, int port) {
        try {
            Bootstrap bootstrap = initBootstrap();
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new FileSendClientHandler());
                    pipeline.addLast(new DecodeHandler());
                    pipeline.addLast(new EncodeHandler());
                    pipeline.addLast(new LoginResponseHandler());

                }
            });
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            this.channel= future.channel();
            // 验证合法性
            verify();

            File file = new File(sourcePath);
            FilePacket filePacket = new FilePacket(file);
            filePacket.setACK(0);
            future.channel().writeAndFlush(filePacket);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException("上传文件异常："+e.getMessage());
        }finally {
            group.shutdownGracefully();
        }
    }

    private List<String> doGetDirection(String sourcePath, String ip, int port) {
        Bootstrap bootstrap = initBootstrap();
        FileDirectoryClientHandler handler = new FileDirectoryClientHandler();
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new EncodeHandler());
                pipeline.addLast(new DecodeHandler());
                pipeline.addLast(handler);
            }
        });

        try {
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            this.channel = future.channel();
            verify();
            FileDictionaryPacket packet = new FileDictionaryPacket(sourcePath);
            this.channel.writeAndFlush(packet);

            future.channel().closeFuture().sync();

            handler.getResult();
        } catch (Exception e) {
            throw new RuntimeException("获取目录异常："+e.getMessage());
        }finally {
            group.shutdownGracefully();
        }
        return Arrays.asList(handler.getResult().split("\n"));
    }

    private void doDownload(String filename, String destPath, String ip, int port) throws Exception {
        File file = new File("/"+filename);

        try {
            Bootstrap bootstrap = initBootstrap();
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new FileReceiveClientHandler(destPath+"/"+file.getName()));
                    pipeline.addLast(new DecodeHandler());
                    pipeline.addLast(new EncodeHandler());
                    pipeline.addLast(new LoginResponseHandler());

                }
            });
            ChannelFuture future = bootstrap.connect(ip, port).sync();
            this.channel= future.channel();
            // 验证合法性
            verify();

            FilePacket filePacket = new FilePacket(file);
            filePacket.setACK(1);
            this.channel.writeAndFlush(filePacket);
            future.channel().closeFuture().sync();

            // 创建Path对象
            Path path = Paths.get(destPath+"/"+file.getName());
            // 检查文件是否存在
            boolean exists = Files.exists(path);
            if(!exists){
                throw new RuntimeException("文件下载失败，检查日志");
            }
        } catch (Exception e) {
            throw new RuntimeException("下载文件异常："+e.getMessage());
        }finally {
            group.shutdownGracefully();
        }
    }

}
