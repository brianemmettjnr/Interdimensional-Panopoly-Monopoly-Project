package monopoly;
import interfaces.Locatable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.BLACK;
import static java.awt.Color.MAGENTA;
import static java.awt.Color.black;

public class LocationLabel
{
    private GUI gui;
    private Border BorderColour=BorderFactory.createLineBorder(BLACK, 2);
    private JLabel label=new JLabel();
    private Locatable location;

    public LocationLabel(int x, int y, int NumOnBoard, GUI guiObj, Locatable location) {
        this.location = location;
        gui = guiObj;
        label = new JLabel("<html><p>" + location.getIdentifier().replace(" ", "<br>") + "</p></html>", SwingConstants.CENTER);
        JLabel finalLabel = label;
//        label.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                gui.updatePlayers();
//                if (finalLabel.getBorder() != BorderFactory.createLineBorder(MAGENTA, 2))
//                    finalLabel.setBorder(BorderFactory.createLineBorder(black, 3));
//                gui.updatePlayers();
//            }
//        });
//        label.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseExited(MouseEvent e) {
//                gui.updatePlayers();
//                finalLabel.setBorder(BorderFactory.createLineBorder(black, 2));
//            }
//        });
//        label.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                gui.getSelectedLocation().setBorder(BorderColour);
//                if (finalLabel != gui.getSelectedLocation()) {
//                    gui.setSelectedLabel(finalLabel);
//                    finalLabel.setBorder(BorderFactory.createLineBorder(MAGENTA, 2));
//                    gui.updatePlayers();
//                } else
//                    gui.setSelectedLabel(new JLabel());//return an empty jlabel, not sure how to approach
//                    gui.updatePlayers();
//            }
//        });
//        if(NumOnBoard%(gui.getBOARD_SIZE()/4)==0)
//            label.setBounds(x, y, gui.getLabelHeight()*2, gui.getLabelWidth()*2);
//        else
            label.setBounds(x, y, gui.getLabelHeight(), gui.getLabelWidth());
        label.setBorder(BorderColour);
        gui.getMainPane().add(label,7);
        label.setOpaque(true);
        label.setBackground(Color.white);
        label.setFont(new Font("Serif", Font.BOLD, 18 - (gui.getBOARD_SIZE() / 6)));
        Class Comparitor = location.getClass();
        //I WOULD MAKE THIS A SWITCH STATEMENT BUT APPARENTLY THEY CANT ACCEPT CLASSES AS ARGUMENTS??????
        if (Comparitor == Chance.class)
           label.setForeground(Color.red);
        else if(Comparitor == CommunityChest.class)
            label.setForeground(Color.BLUE);
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
