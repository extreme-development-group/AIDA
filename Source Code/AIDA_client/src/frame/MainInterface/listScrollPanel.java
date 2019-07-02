package frame.MainInterface;

import javax.swing.*;

// listScrollPanel
public class listScrollPanel extends JScrollPane {
    listScrollPanel(JPanel jp) {
        super(jp);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        getVerticalScrollBar().setUI(new ScrollBarUI());
        getVerticalScrollBar().setUnitIncrement(15);
        setBounds(0, 165, 250, 400);
        setBorder(null);
    }
}
