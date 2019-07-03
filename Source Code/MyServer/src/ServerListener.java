import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;

public class ServerListener {
    // 记录在线用户id
    private static HashMap<String, DataStream> clientUser;
    // 静态初始化块
    static {
        clientUser = new HashMap<String, DataStream>();
    }

    private ServerSocket serverSocket;
    private InetAddress localHostAddress;
    private int serverPort;

    public ServerListener(int port) {
        try {
            // 监听端口
            serverPort = port;

            // 创建监听端口的ServerSocket对象
            serverSocket = new ServerSocket(serverPort);

            // 获取本机地址
            localHostAddress = serverSocket.getInetAddress();

        } catch (IOException e) {
            System.out.println("错误信息 ：" + e.getMessage());
        }
    }


    public int getServerPort() {
        return serverPort;
    }

    public String getLocalHostName() {
        return localHostAddress.getHostName();
    }

    public String getLocalHostAddress() {
        return localHostAddress.getHostAddress();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    // 获取在线用户表
    public static HashMap<String, DataStream> getClientUser() {
        return clientUser;
    }
}
