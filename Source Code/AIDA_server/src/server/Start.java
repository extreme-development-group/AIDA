package server;

public class Start {
    public static void main(String[] args) {
        new Thread(new VerifyThread()).start();
        System.out.println("服务端已成功创建,当前在线人数：0");
    }
}
