package frame.ChatFrame;

import javax.swing.*;
import java.awt.*;

public class ProcessFrame extends JFrame {
    public JProgressBar progressBar;
    public JPanel panel;
    public ProcessFrame(){
        progressBar = new JProgressBar();
        panel = new JPanel();
        progressBar.setPreferredSize(new Dimension(300,30));
        progressBar.setLocation(new Point(50,100));
        progressBar.setStringPainted(true);
        panel.setBounds(50,85,300,30);
        panel.add(progressBar);
        this.setLayout(new BorderLayout());
        this.add(panel,BorderLayout.CENTER);
        this.setSize(400,100);
        setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public static void main(String[] args){
        new ProcessFrame();
    }
}
