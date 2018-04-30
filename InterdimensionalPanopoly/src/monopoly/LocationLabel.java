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
    private JLabel colourLabel=new JLabel();
    private Locatable location;
    private LocationLabel thisLocation =this;
    private int index;
    private int x=0,y=0;

    LocationLabel(int x, int y, int NumOnBoard, GUI guiObj, Locatable location)
    {
        this.location = location;
        index=NumOnBoard;
        gui = guiObj;
        this.x=x;
        this.y=y;
        String name=location.getIdentifier().replace(" ","\n");
        String text="<html><center><p1><body style='width: "+gui.getOffset()+"px'>"+ name + "</center></body></p1></html>";
        label = new JLabel(text, SwingConstants.CENTER);
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
        if(location instanceof InvestmentProperty)
        {label.setBounds(x, y + (gui.getOffset() / 5)-1, gui.getOffset(), (int) (gui.getOffset() * .8)+2);
            colourLabel.setBounds(x, y , gui.getOffset(), (int) (gui.getOffset() * .2));
            colourLabel.setBorder(BorderColour);
            colourLabel.setVisible(true);
            colourLabel.setBackground(((InvestmentProperty) location).getGroup().getColor());
            colourLabel.setOpaque(true);
            gui.getMainPane().add(colourLabel);}

        else {
        label.setBounds(x, y,gui.getOffset(), (int)(gui.getOffset()));
        }
        label.setMaximumSize(label.getSize());
        label.setBorder(BorderColour);
        gui.getMainPane().add(label,7);
        label.setOpaque(true);
        label.setBackground(Color.white);

        int size = 20;

        while(label.getFontMetrics(new Font("Courier", Font.BOLD,  size)).stringWidth("investment") > gui.getOffset())
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
        return x;
    }
    int getY()
    {
        return y;
    }

    Locatable getLocation() {
        return location;
    }

    JLabel getLabel() {
        return label;
    }

    String getHTML() {
        String name=location.getIdentifier().replace(" ","<br>");
        String color="";
        if (location instanceof Chance)
            color="red";
        else if(location instanceof CommunityChest)
            color="blue";
        else if(location instanceof Station)
            color="gray";
        else if(location instanceof Utility)
            color="#008b8b";
        String HTML="<html>"+"<body style='width: 100%'><center><font size='6' color='"+color+"'>";
        HTML+=name + "</font><br>";
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
                    HTML += "Site is currently Mortgaged<br>Cost to Redeem: $"+((RentalProperty) location).getRedeemAmount()+"<br>";

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
        boolean playerOnTile=false;
        for(Player player:GUI.getPlayers())
        {
            if (player.getPosition()==this.index)
            {
                if(!playerOnTile)
                {
                    HTML+="Players on Tile:<br>";
                    playerOnTile=true;
                }
                HTML+=player.getIdentifier()+"<br>";
            }
        }
        return HTML+"</center></body></html>";
    }
    public void setTempBorder(Border newBorder)
    {
        label.setBorder(newBorder);
    }
    public Border getBorder()
    {
        return label.getBorder();
    }
    public void resetBorder()
    {
        label.setBorder(BorderColour);
    }

    public int getIndex() {
        return index;
    }
}
