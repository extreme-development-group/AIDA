import config.ChatVerify;

import java.io.*;
import java.net.Socket;

public class Verification implements Runnable {
    private Socket userSocket;

    Verification(Socket userSocket) {
        this.userSocket = userSocket;
    }

    private Object process(Object obj) {
        // 获取对象类型
        String objName = obj.getClass().getSimpleName();
        System.out.println("当前处理对象类型为 " + objName);

        // 返回结果对象
        Object result = null;

        switch (objName) {
            case "ChatVerify":
                // 登陆验证
                ChatVerify loginVerify = (ChatVerify) obj;
                if(ServerListener.getClientUser().containsKey(loginVerify.getUserId())) {
                    // 重复登陆
                    result = "Repeat_login";
                } else {
                    result = DataCheck.isLoginSuccess(loginVerify.getUserId(), loginVerify.getUserPassword());

                    // 为登录成功的用户创建聊天线程
                    if (result != null && (Boolean) result == true) {
                        System.out.println("登录成功，为该用户创建一个聊天线程");
                        new Thread(new InteractWithClient(loginVerify.getUserId())).start();
                    }
                }
                break;
            case "String":
                // 获取字符串内容
                String field = obj.toString();
                // 如果为获取信息
                if (field.startsWith("getUserInfo")) {
                    field = field.replace("getUserInfo", "");
                    result = DataCheck.getUserInfo(field);
                } else if (field.startsWith("getChatRecord")) {

                    // 获取聊天记录
                    // TODO
                } else if (field.startsWith("getGroupMembers")) {
                    field = field.replace("getGroupMembers", "");
                    result = DataCheck.getGroupMember(field);
                } else if (field.startsWith("setMyTrades")) {
                    // TODO
                }
                break;
        }

        return result;
    }

    // 接收用户套接字传过来的对象，处理，回传结果
    @Override
    public void run() {
        try {
            // 对象输入输出流
            ObjectInputStream in = new ObjectInputStream(userSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(userSocket.getOutputStream());

            // 接收一个对象流
            Object obj = in.readObject();

            // 获取处理结果
            Object result = process(obj);

            // 返回给客户端这个处理结果
            out.writeObject(result);

            // 关闭所有流
            in.close();
            out.close();
            userSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
