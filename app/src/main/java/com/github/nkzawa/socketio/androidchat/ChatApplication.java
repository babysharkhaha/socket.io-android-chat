package com.github.nkzawa.socketio.androidchat;

import android.app.Application;
import android.util.Log;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class ChatApplication extends Application {

    private Socket socket;
    {
//        try {
//            socket = IO.socket(Constants.CHAT_SERVER_URL);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }

//        // 配置连接参数
//        IO.Options options = new IO.Options();
//        // 设置连接路由上下文
//        options.path = RouterBuilder.router("/socket-center/ws-abc");
        //http://13.251.58.33:16001/socket-center/ws-abc?transport=websocket&uid=600810
        IO.Options opts = new IO.Options();
        opts.path = "/socket-center/ws-abc";
        opts.query = "?transport=websocket&uid=" + 600810;
        try {

            socket = IO.socket("http://13.251.58.33:16001", opts);//这里的地址我们用后台提供的
//            socket = IO.socket(Constants.CHAT_SERVER_URL + "?transport=websocket&uid=" + 600810);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        // 开启重连
        socket.io().reconnection(true);
        socket.io().reconnectionDelay(100);
        socket.io().reconnectionDelayMax(10000);

        // 连接错误处理
        socket.on(Socket.EVENT_CONNECT_ERROR, objs -> {
            Exception e = (Exception) objs[0];
            Log.i("socket","Socket连接异常：" + e.getMessage(), e);
        });

        // 连接处理
        socket.on(Socket.EVENT_CONNECT, objs -> {
            Log.i("socket","Socket连接成功"+ objs.toString());
        });

        // 断连处理
        socket.on(Socket.EVENT_DISCONNECT, objs -> {
            Log.i("socket","Socket断开连接");
        });

        // 注册连接
        socket.connect();


    }

    public Socket getSocket() {
        return socket;
    }
}
