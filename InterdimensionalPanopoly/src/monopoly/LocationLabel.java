package monopoly;
import interfaces.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.BLACK;
import static java.awt.Color.MAGENTA;
import static java.awt.Color.green;

class LocationLabel
{
    private GUI gui;
    private Border BorderColour=BorderFactory.createLineBorder(BLACK, 2);
    private JLabel label=new JLabel();
    private Locatable location;
    private LocationLabel thisLocation =this;

    LocationLabel(int x, int y, int NumOnBoard, GUI guiObj, Locatable location) {
        this.location = location;
        gui = guiObj;
        String html=this.getHTML();
        String name="<html>"+location.getIdentifier().replace(" ","<br>"+"</html>");
        label = new JLabel(name, SwingConstants.CENTER);
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
        label.setMaximumSize(label.getSize());
        label.setBorder(BorderColour);
        gui.getMainPane().add(label,7);
        label.setOpaque(true);
        label.setBackground(Color.white);
        label.setFont(new Font("Serif", Font.BOLD, 18 - (gui.getBOARD_SIZE()/8)));
        if (location instanceof Chance)
           label.setForeground(Color.red);
        else if(location instanceof CommunityChest)
            label.setForeground(Color.BLUE);
        else if(location instanceof Station)
            label.setForeground(Color.GRAY);
        else if(location instanceof Utility)
            label.setForeground(Color.cyan.darker());
    }
    int getX()
    {
        return label.getX();
    }
    int getY()
    {
        return label.getY();
    }

    Locatable getLocation() {
        return location;
    }

    private JLabel getLabel() {
        return label;
    }

    String getHTML() {
        String name=location.getIdentifier().replace(" ","<br>");
        String HTML="<html>"+"<body style='width: 100%'><center>";
        HTML+=name + "<br>";
        if(location instanceof RentalProperty)
        {
            Rentable rentable=((Rentable) location);
            Player owner= (Player) rentable.getOwner();
            if(owner==null) {
                HTML +="Unowned" + "<br>";
                HTML +="For sale for: $" + rentable.getPrice() + "<br>";
            }
            else
                HTML+="Owned by: "+owner.getIdentifier()+"<br>";
            if(location instanceof InvestmentProperty)
            {
                Improvable improver=(Improvable) location;
                HTML+="Houses: "+improver.getNumHouses()+"<br>";
                HTML+="Hotels: "+improver.getNumHotels()+"<br>";
                HTML+="Building cost: $"+improver.getBuildPrice()+"<br>";
                HTML+="Rent for staying: $"+((InvestmentProperty) location).getRentalAmount()+"<br>";
            }
        }
        else if(location instanceof TaxableProperty)
        {
            HTML+="Tax: $"+((TaxableProperty) location).getFlatAmount()+"<br>";
        }
        return HTML+"</center></body></html>";
    }
}
