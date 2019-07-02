package frame.MainInterface;

import javax.swing.*;
import java.awt.*;

// 弹出菜单样式
public class MyPopupMenu extends JPopupMenu {
    MyPopupMenu() {
        setBorder(BorderFactory.createLineBorder(Color.white, 2));
        setBackground(new Color(245, 255, 252));
    }
}
