import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.BLACK;
import static java.awt.Color.MAGENTA;

public class LocationLabel
{
    private GUI gui;
    private Border BorderColour=BorderFactory.createLineBorder(BLACK, 1);

    public LocationLabel(JLabel label, int x, int y, int NumOnBoard,GUI guiObj)
    {
        gui=guiObj;
        label=new JLabel("       "+NumOnBoard);
        JLabel finalLabel = label;
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                finalLabel.setText("hello");
            }
        });
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                finalLabel.setText("bye");
            }
        });
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gui.getSelectedLocation().setBorder(BorderColour);
                if(finalLabel!=gui.getSelectedLocation())
                {
                    gui.setSelectedLabel(finalLabel);
                    finalLabel.setBorder(BorderFactory.createLineBorder(MAGENTA, 2));
                }
                else
                    gui.setSelectedLabel(new JLabel());//return an empty jlabel, not sure how to approach
            }
        });
        label.setBounds(x,y,gui.getLabelHeight(),gui.getLabelWidth());
        label.setBorder(BorderColour);
        gui.getMainPanel().add(label);
    }
}
