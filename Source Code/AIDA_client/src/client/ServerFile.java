package client;

import frame.ChatFrame.ChatFrame;
import frame.ChatFrame.ProcessFrame;

import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class ServerFile extends Thread {

    private Socket socket;
    private JProgressBar jprogressbars;
    private ProcessFrame frame;
    private ChatFrame chatFrame;

    public ServerFile(Socket socket) {
        this.socket = socket;
        this.chatFrame=chatFrame;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            byte[] b = new byte[100];
            int len = input.read(b);
            String ss = new String(b, 0, len);
            System.out.println(ss);
            String[] str1 = ss.split("\t");// 把接收到的信息按制表符拆分
            String filename = str1[0];// 起始位文件名称
            String filesize = str1[1];// 下标1位文件的大小
            long size = Long.parseLong(filesize);

            InetAddress ip = socket.getInetAddress();// 得到发送端的IP
            int port = socket.getPort();// 得到发送端的端口
            System.out.println("From: "+ip+" "+port);

            long s = size / 1024 / 1024;
            String name = " M";
            if (s < 1) {
                s = (size / 1024);
                name = " K";
            } else if (s > 1024) {
                float s1 = size / 1024 / 1024;
                s = (size / 1024 / 1024 / 1024);
                name = " G多";
            }
            System.out.println("弹出确认款");
            // 弹出确认款，显示对方的ip端口以及文件的名称和大小是否需要接收
            int i = JOptionPane.showConfirmDialog(null,
                    "来自: " + ip + ":" + port + "\n文件名称: " + filename + "\n文件大小: " + s + name);
            // 如果点击确认
            if (i == JOptionPane.OK_OPTION) {
                // 那么传输ok给发送端示意可以接收
                output.write("ok".getBytes());
                output.flush();
                JFileChooser jf = new JFileChooser();
                // 存储到本地路径的夹子
                jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jf.showOpenDialog(null);
                frame = new ProcessFrame();
                jprogressbars=frame.progressBar;
                jprogressbars.setMaximum((int) (size / 10000));
                FileOutputStream fout = new FileOutputStream(new File(jf.getSelectedFile(), filename));
                b = new byte[1024 * 1024 * 2];
                long size1 = 0;
                while ((len = input.read(b)) != -1) {
                    fout.write(b, 0, len);
                    size1 += len;
                    jprogressbars.setValue((int) (size1 / 10000));
                }
                fout.close();
                frame.dispose();
                input.close();
                socket.close();
            } else {
                // 否不接收此文件
                output.write("no".getBytes());
                output.flush();
            }
        } catch (IOException e) {
            System.out.println();
            javax.swing.JOptionPane.showMessageDialog(null, "传输错误");
        }finally {
            try {
                openServer(8080);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static ServerSocket server = null;
    // 启动服务器
    public static void openServer(int port) throws Exception {
        new Thread() {
            public void run() {
                try {
                    if (server != null && !server.isClosed()) {
                        server.close();
                    }
                    System.out.println("新建文件传输服务器");
                    server = new ServerSocket(port);
                    new ServerFile(server.accept()).start();
                } catch (IOException e) {
//                    JOptionPane.showMessageDialog(null, "传输错误");
                }
            }
        }.start();
    }

    // 关闭服务器
    public static void closeServer() {
        try {
            server.close();
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "IOException");
        }
    }
    public static void main(String[] args){
        try {
            ServerFile.openServer(48248);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
