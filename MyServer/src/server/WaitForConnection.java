package server;

import config.ServerInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WaitForConnection implements Runnable {
    // 监听端口是否有用户接入
    private static ServerSocket serverSocket;
    // 静态初始化块
    static {
        serverSocket = (new ServerListener(ServerInfo.VERIFY_PORT)).getServerSocket();
    }

    // 等待用户连接，为每个连接分配一个单独的线程处理
    @Override
    public void run() {
        try {
            while (true) {
                // 等待用户连接
                Socket userSocket = serverSocket.accept();
                System.out.println(userSocket.getInetAddress()+"用户接入");
                // 处理用户连接
                new Thread(new Verification(userSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("验证端口服务异常 ：" + e.getMessage());
        }
    }
}
