package client;

import frame.ChatFrame.ChatFrame;
import frame.ChatFrame.ChatWithFriends;
import frame.ChatFrame.ProcessFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.*;

public class ClientFile extends Thread {

    private  String ip;
    private  int port;
    private String filepath;
    private long size;
    public ClientFile(String ip, int port, String filepath) {
        this.ip = ip;
        this.port = port;
        this.filepath = filepath;

    }
    public void run() {
        try {
            Socket socket = new Socket(ip, port);
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();

            File file = new File(filepath);
            System.out.println(filepath);
            // 第一次传输文件名字and文件的大小
            String str1 = file.getName() + "\t" + file.length();
            output.write(str1.getBytes());
            output.flush();
            byte[] b = new byte[100];
            int len = input.read(b);
            String s = new String(b, 0, len);
            // 如果服务器传输过来的是ok那么就开始传输字节
            if (s.equalsIgnoreCase("ok")) {
                long size = 0;
                ProcessFrame frame = new ProcessFrame();
                JProgressBar jprogressbar=frame.progressBar;
                jprogressbar.setMaximum((int) (file.length() / 10000));// 设置进度条最大值
                FileInputStream fin = new FileInputStream(file);
                byte[] b1 = new byte[1024 * 1024 * 2];
                while (fin.available() != 0) {
                    len = fin.read(b1);
                    output.write(b1, 0, len);
                    output.flush();
                    size += len;
                    jprogressbar.setValue((int) (size / 10000));
                }
                frame.dispose();
                if (fin.available() == 0) {
                    output.close();
                    fin.close();
                    socket.close();
                    javax.swing.JOptionPane.showMessageDialog(null, "传输完毕！");
                }

            } else {
                // 传输的不是ok那么就弹出个信息框对方拒绝
                javax.swing.JOptionPane.showMessageDialog(null, "对方拒绝接收此数据！");
            }
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "传输错误");
        }
    }
    //调试
//    public static void main(String[] args){
//        JFileChooser chooser = new JFileChooser();
//        int returnVal=chooser.showOpenDialog(null);
//        String filepath;
//        filepath = chooser.getSelectedFile().getPath();
//        System.out.println(filepath);
//        ClientFile clientFile = new ClientFile("127.0.0.1",8080,filepath);
//        clientFile.start();
//    }

}
