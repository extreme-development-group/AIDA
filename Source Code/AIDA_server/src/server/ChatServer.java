package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;

public final class ChatServer {

    /**
     * @Fields serverSocket : 服务器Socket对象
     */
    private ServerSocket serverSocket;

    /**
     * @Fields localHostAddress : 本地信息
     */
    private InetAddress localHostAddress;

    /**
     * @Fields serverPort : 监听端口
     */
    private int serverPort;

    /**
     * @Fields clientUser : 用户ID与其连接对象数据流之间的哈希映射
     */
    private static HashMap<String, DataStream> clientUser;

    // 静态初始化块
    static {
        clientUser = new HashMap<String, DataStream>();
    }

    /**
     * @Title: ChatServer
     * @Description: 监听计算机某个端口(创建ServerSocket对象)
     * @param port
     *            监听哪个端口
     * @throws: IOException
     *              会在端口被占用或无法成功创建的时候抛出
     */
    public ChatServer(int port) {
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

    /**
     * @Title: getServerPort
     * @Description: 获取监听的端口号
     * @return int
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * @Title: getLocalHostName
     * @Description: 获取本机名称
     * @return String
     */
    public String getLocalHostName() {
        return localHostAddress.getHostName();
    }

    /**
     * @Title: getLocalHostAddress
     * @Description: 获取本机IP(当前计算机所在局域网的IP信息)
     * @return String
     */
    public String getLocalHostAddress() {
        return localHostAddress.getHostAddress();
    }

    /**
     * @Title: getClientNum
     * @Description: 获取已经成功连接到该ChatServer对象的人数
     * @return int
     */
    public int getClientNum() {
        return clientUser.size();
    }

    /**
     * @Title: getServerSocket
     * @Description: 获取该端口的监听者ServerSocket对象
     * @return ServerSocket
     */
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * @Title: getClientUser
     * @Description: 获取用户ID与其连接对象数据流之间的哈希映射
     * @return HashMap<String,DataStream>
     */
    public static HashMap<String, DataStream> getClientUser() {
        return clientUser;
    }
}