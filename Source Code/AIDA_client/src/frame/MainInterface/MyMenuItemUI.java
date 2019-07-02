package frame.MainInterface;

import javax.swing.plaf.basic.BasicMenuItemUI;
import java.awt.*;

// MenuItem鼠标滑过样式
public class MyMenuItemUI extends BasicMenuItemUI {
    MyMenuItemUI() {
        super.selectionBackground = new Color(2, 167, 240);
        super.selectionForeground = Color.white;
    }
}
