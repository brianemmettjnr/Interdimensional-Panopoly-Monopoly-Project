package monopoly;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIButton {
    private JLabel button;

    public GUIButton(String name, int x, int y,MouseAdapter mouseAdapter,GUI gui)
    {
        button=new JLabel(name);
        button.setVisible(false);
        button.setBounds(x,y,gui.getOffset(),30);
        button.setOpaque(true);
        button.addMouseListener(mouseAdapter);
        gui.getMainPane().add(button);
    }
    public void setVisible(boolean visibility)
    {
        button.setVisible(visibility);
    }
}
