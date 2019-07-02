package server;
import config.ServerInfo;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public final class VerifyThread implements Runnable {

    /**
     * @Fields serverSocket : 验证端口监听者对象
     */
    private static ServerSocket serverSocket;

    static {
        serverSocket = new ChatServer(ServerInfo.VERIFY_PORT).getServerSocket();
    }

    /**
     * @Title: run
     * @Description: 等待用户连接，如果连接成功为其单独分配线程处理
     * @see java.lang.Runnable#run()
     * @throws: IOException
     */
    @Override
    public void run() {
        try {
            while (true) {
                // 等待用户连接
                Socket userSocket = serverSocket.accept();

                System.out.println(userSocket.getInetAddress() + " 发送来的新的验证");

                // 为用户接入创建一个验证线程
                new Thread(new VerifyConnection(userSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("验证端口服务异常 ：" + e.getMessage());
        }
    }
}