package frame.MainInterface;

import javax.swing.*;
import java.awt.*;

// listPanel
public class listPanel extends JPanel {
    listPanel() {
        setLocation(0, 165);
        setPreferredSize(new Dimension(250, 397)); // jscrollpanel有3像素未知区域。。。
        setBackground(new Color(238, 255, 250));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    }
}
