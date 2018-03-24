package monopoly;
import interfaces.Locatable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.BLACK;
import static java.awt.Color.MAGENTA;
import static java.awt.Color.black;

public class LocationLabel
{
    private GUI gui;
    private Border BorderColour=BorderFactory.createLineBorder(BLACK, 2);
    private JLabel label=null;
    private Locatable location;
    public LocationLabel(JLabel label, int x, int y, int NumOnBoard, GUI guiObj, Locatable location)
    {
        this.label=label;
        this.location=location;
        gui=guiObj;
        label=new JLabel("       "+NumOnBoard);
        JLabel finalLabel = label;
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (finalLabel.getBorder()!=BorderFactory.createLineBorder(MAGENTA, 2))
                    finalLabel.setBorder(BorderFactory.createLineBorder(black,3));
            }
        });
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                    finalLabel.setBorder(BorderFactory.createLineBorder(black,2));
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
    public int getX()
    {
        return label.getX();
    }
    public int getY()
    {
        return label.getY();
    }
}
