package server;

import config.ServerInfo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public final class InteractWithClient implements Runnable {
    // 监听聊天端口
    private static ServerSocket interactSocket;
    // 静态初始化块
    static {
        interactSocket = (new ServerListener(ServerInfo.CHAT_PORT)).getServerSocket();
    }

    private String userId;
    /// 该用户连接所生成的Socket对象
    private Socket userSocket;

    // 初始化用户ID
    public InteractWithClient(String userId) {
        this.userId = userId;
    }

    // 等待客户端连接，成功连接之后为其创建一个聊天数据流对象
    @Override
    public void run() {
        // 先与客户端建立聊天端口的连接
        try {
            userSocket = interactSocket.accept();
        } catch (IOException e) {
            System.out.println("未能与客户端成功建立连接：" + e.getMessage());
            return;
        }

        // 成功接入之后建立数据流
        DataStream dataStream = new DataStream(userSocket, userId);

        // 加入到在线人员映射里面
        ServerListener.getClientUser().put(userId, dataStream);

        System.out.println("用户 " + userId + " 已成功登录 ,Info: " + userSocket.getInetAddress());
        System.out.println("当前在线人数： " + ServerListener.getClientUser().size());

        dataStream.run();
    }
}
