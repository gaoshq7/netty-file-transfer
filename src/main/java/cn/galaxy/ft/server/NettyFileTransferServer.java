package cn.galaxy.ft.server;

import cn.galaxy.ft.FileTransferServer;
import cn.galaxy.ft.codec.DecodeHandler;
import cn.galaxy.ft.codec.EncodeHandler;
import cn.galaxy.ft.protocol.Constant;
import cn.galaxy.ft.server.handler.FileDirectoryServerHandler;
import cn.galaxy.ft.server.handler.FilePacketServerHandler;
import cn.galaxy.ft.server.handler.FileSendServerHandler;
import cn.galaxy.ft.server.handler.JoinClusterRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.AllArgsConstructor;

/**
 * Project : galaxy
 * Class : cn.galaxy.ft.server.FileTransferServer
 *
 * @author : gsq
 * @date : 2024-03-08 13:55
 * @note : It's not technology, it's art !
 **/
@AllArgsConstructor
public class NettyFileTransferServer extends FileTransferServer {

    private int port = Constant.DEFAULT_SERVER_PORT;    // 默认端口8989

    private final String uploadPath;

    private final String downloadPath;

    /**
     * @Description : 启动文件传输服务
     * @Param : []
     * @Return : void
     * @Author : gsq
     * @Date : 14:48
     * @note : An art cell !
    **/
    @Override
    public void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) {
                        ChannelPipeline pipeline = channel.pipeline();

                        pipeline.addLast(new FileSendServerHandler(downloadPath));
                        pipeline.addLast(new DecodeHandler());
                        pipeline.addLast(new EncodeHandler());
                        pipeline.addLast(new JoinClusterRequestHandler());
                        pipeline.addLast(new FilePacketServerHandler(uploadPath));
                        pipeline.addLast(new FileDirectoryServerHandler());


                        // pipeline.addLast("handler", new MyServerHandler());
                    }
                });

        ChannelFuture future = bootstrap.bind(this.port).sync();
        Channel channel = future.channel();
        future.channel().closeFuture().sync();
    }

    /**
     * @Description : 上传下载文件服务构造器
     * @Param : [uploadPath, downloadPath]
     * @Return :
     * @Author : gsq
     * @Date : 14:46
     * @note : An art cell !
     **/
    public NettyFileTransferServer(String uploadPath, String downloadPath) {
        this.uploadPath = uploadPath;
        this.downloadPath = downloadPath;
    }

    /**
     * @Description : 绑定文件服务端口号
     * @Param : [port]
     * @Return : void
     * @Author : gsq
     * @Date : 14:16
     * @note : An art cell !
     **/
    public void bind(Integer port) {
        this.port = port;
    }

}
