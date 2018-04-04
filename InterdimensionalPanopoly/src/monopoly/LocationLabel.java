package monopoly;
import interfaces.Locatable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.BLACK;
import static java.awt.Color.MAGENTA;
import static java.awt.Color.green;

public class LocationLabel
{
    private GUI gui;
    private Border BorderColour=BorderFactory.createLineBorder(BLACK, 2);
    private JLabel label=new JLabel();
    private Locatable location;
    private LocationLabel thisLocation =this;

    public LocationLabel(int x, int y, int NumOnBoard, GUI guiObj, Locatable location) {
        this.location = location;
        gui = guiObj;
        label = new JLabel("<html><p>" + location.getIdentifier().replace(" ", "<br>") + "</p></html>", SwingConstants.CENTER);
//        label.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                if (finalLabel.getBorder() != BorderFactory.createLineBorder(MAGENTA, 2))
//                    finalLabel.setBorder(BorderFactory.createLineBorder(black, 3));
//                gui.updatePlayers();
//            }
//        });
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                gui.updatePlayers();
            }
        });
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(gui.getSelectedLocation()==null)
                {
                    gui.setSelectedLabel(thisLocation);
                    thisLocation.getLabel().setBorder(BorderFactory.createLineBorder(green, 2));
                    gui.updatePlayers();
                }
                else if (thisLocation != gui.getSelectedLocation()) {
                    gui.getSelectedLocation().getLabel().setBorder(BorderColour);
                    gui.setSelectedLabel(thisLocation);
                    thisLocation.getLabel().setBorder(BorderFactory.createLineBorder(Color.green, 2));
                    gui.updatePlayers();
                } else
                {
                    gui.getSelectedLocation().getLabel().setBorder(BorderColour);
                    gui.setSelectedLabel(null);//return an empty jlabel, not sure how to approach
                }
                gui.updatePlayers();

            }
        });
            label.setBounds(x, y, gui.getOffset(), gui.getOffset());
        label.setBorder(BorderColour);
        gui.getMainPane().add(label,7);
        label.setOpaque(true);
        label.setBackground(Color.white);
        label.setFont(new Font("Serif", Font.BOLD, 18 - (gui.getBOARD_SIZE() / 5)));
        Class Comparitor = location.getClass();
        //I WOULD MAKE THIS A SWITCH STATEMENT BUT APPARENTLY THEY CANT ACCEPT CLASSES AS ARGUMENTS??????
        if (Comparitor == Chance.class)
           label.setForeground(Color.red);
        else if(Comparitor == CommunityChest.class)
            label.setForeground(Color.BLUE);
        else if(Comparitor == Station.class)
            label.setForeground(Color.GRAY);
    }
    public int getX()
    {
        return label.getX();
    }
    public int getY()
    {
        return label.getY();
    }

    public Locatable getLocation() {
        return location;
    }

    public JLabel getLabel() {
        return label;
    }
}
