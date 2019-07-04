package server;

import javax.swing.*;

public class Start {
    public static void main(String[] args) {
        (new Thread(new WaitForConnection())).start();
        System.out.println("服务器开始运行...");
    }
}
