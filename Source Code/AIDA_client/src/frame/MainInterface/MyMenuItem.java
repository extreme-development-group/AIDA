package frame.MainInterface;

import javax.swing.*;
import java.awt.*;

// 菜单选项样式
public class MyMenuItem extends JMenuItem {
    MyMenuItem(String text) {
        super(text);
        setBorderPainted(false);
        setOpaque(false);
        setFont(new Font("微软雅黑", Font.PLAIN, 14));
        setUI(new MyMenuItemUI());
    }
}
