package monopoly;
import interfaces.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.BLACK;

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
        String name=location.getIdentifier().replace(" ","\n");
        String text="<html><center><p1><body style='width: "+gui.getOffset()+"px'>"+ name + "</center></body></p1></html>";
        label = new JLabel(text, SwingConstants.CENTER);
//        label.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                if (finalLabel.getBorder() != BorderFactory.createLineBorder(MAGENTA, 2))
//                    finalLabel.setBorder(BorderFactory.createLineBorder(black, 3));
//                gui.updateGUI();
//            }
//        });
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                gui.updateGUI();
            }
        });
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gui.setSelectedLabel(thisLocation);
                gui.updateGUI();

            }
        });
            label.setBounds(x, y, gui.getOffset(), gui.getOffset());
        label.setMaximumSize(label.getSize());
        label.setBorder(BorderColour);
        gui.getMainPane().add(label,7);
        label.setOpaque(true);
        label.setBackground(Color.white);
        label.setFont(new Font("Courier", Font.BOLD,  20));

        int size = 20;

        while(label.getFontMetrics(new Font("Courier", Font.BOLD,  size)).stringWidth("Investment") > gui.getOffset())
        {
            size--;
            label.setFont(new Font("Courier", Font.BOLD,  size));
        }

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

    JLabel getLabel() {
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
            else {
                HTML += "Owned by: " + owner.getIdentifier() + "<br>";
                HTML += "Rent: $" + rentable.getRentalAmount() + "<br>";
                if(((RentalProperty) location).isMortgaged())
                    HTML += "Site is currently Mortgaged<br>";
            }
            if(location instanceof InvestmentProperty)
            {
                Improvable improver=(Improvable) location;
                HTML+="Houses: "+improver.getNumHouses()+"<br>";
                HTML+="Hotels: "+improver.getNumHotels()+"<br>";
                HTML+="Building cost: $"+improver.getBuildPrice()+"<br>";
            }
        }
        else if(location instanceof TaxableProperty)
        {
            HTML+="Tax: $"+((TaxableProperty) location).getFlatAmount()+"<br>";
        }
        return HTML+"</center></body></html>";
    }
}
