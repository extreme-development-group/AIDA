package frame.MainInterface;

import javax.swing.*;
import java.awt.*;

// 关闭按钮
public class UI_Close extends JButton {

    UI_Close() {
        setMargin(new Insets(0, 0, 0, 0)); // 消除按钮外边距
        setContentAreaFilled(false); // 不绘制内容区域（透明背景）
        setBorderPainted(false); // 去除边框
        setFocusPainted(false); // 不绘制焦点状态
        setToolTipText("关闭");
        Image normal = new ImageIcon("./res/Login/closebutton_normal.png").getImage();
        setIcon(new ImageIcon(normal.getScaledInstance(30 , 30, Image.SCALE_DEFAULT)));
        Image hover = new ImageIcon("./res/Login/closebutton_hover.png").getImage();
        setRolloverIcon(new ImageIcon(hover.getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        Image down = new ImageIcon("./res/Login/closebutton_down.png").getImage();
        setPressedIcon(new ImageIcon(down.getScaledInstance(30, 30 , Image.SCALE_DEFAULT)));
    }
}
