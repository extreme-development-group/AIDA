package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// 实现可拖动的事件监听
public class DragWindowListener extends MouseAdapter {
    private int offsetX, offsetY;
    private boolean isCanMove;

    public DragWindowListener() {
        isCanMove = true;
    }

    public void setCanMove(boolean isCanMove) {
        this.isCanMove = isCanMove;
    }

    public void mouseDragged(MouseEvent e) {
        // requires JDK 1.6 or above
        if (isCanMove) {
            SwingUtilities.getRoot((Component) e.getSource()).setLocation(e.getXOnScreen() - offsetX,
                    e.getYOnScreen() - offsetY);
        }
    }

    public void mousePressed(MouseEvent e) {
        offsetX = e.getX();
        offsetY = e.getY();
    }

    public void mouseClicked(MouseEvent e) {
        SwingUtilities.getRoot((Component) e.getSource()).requestFocus();
        super.mouseClicked(e);
    }
}
