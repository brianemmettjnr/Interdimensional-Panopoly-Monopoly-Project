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
    private final int width;
    private final int height;
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
        this.width=gui.getOffset()*(1+((int)(index/(gui.getSquaresOnSide()-1)))%2);
        this.height=gui.getOffset()*(1+(1+((int)(index/(gui.getSquaresOnSide()-1)))%2)%2);
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
        {
            label.setBounds(x, y + (gui.getOffset() / 5)-1, width, (int) (height * .9)+2);
            colourLabel.setBounds(x, y ,width, (int) (height * .2));
            colourLabel.setBorder(BorderColour);
            colourLabel.setVisible(true);
            colourLabel.setBackground(((InvestmentProperty) location).getGroup().getColor());
            colourLabel.setOpaque(true);
            gui.getMainPane().add(colourLabel);}

        else {
            if(index%(gui.getSquaresOnSide()-1)==0)//checks if corner
                label.setBounds(x, y,2*gui.getOffset(), 2*gui.getOffset());
            else
                label.setBounds(x, y,width, height);
        }
        label.setMaximumSize(label.getSize());
        label.setBorder(BorderColour);
        gui.getMainPane().add(label,7);
        label.setOpaque(true);
        label.setBackground(Color.white);

        int size = 20;

        while(label.getFontMetrics(new Font("Courier", Font.BOLD,  size)).stringWidth("investments") > gui.getOffset())
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
