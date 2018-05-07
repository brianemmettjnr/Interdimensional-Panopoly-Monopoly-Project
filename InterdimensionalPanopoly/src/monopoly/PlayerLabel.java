package monopoly;

import interfaces.Locatable;
import interfaces.Rentable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static java.awt.Color.getColor;
import static java.awt.Color.green;

public class PlayerLabel
{
    private int scale;
    private JLabel name;
    private Player player;
    private int index;
    private JLabel balance=new JLabel("",SwingConstants.CENTER);
    private JLabel icon;
    private GUI gui;
    private JLabel positionIcon;
    private Border border;

     PlayerLabel(Player player, int i, ImageIcon image,GUI gui)
    {
        this.gui=gui;
        this.player=player;
        this.index=i;
        int width=(int)((this.gui.getFRAME_SIZE().width-this.gui.getFRAME_SIZE().height)/4)-10;
        if(width*gui.getPlayers().size()>gui.getFRAME_SIZE().height-20)
        {
            width=(int)(gui.getFRAME_SIZE().height*.9-20)/gui.getPlayers().size();
        }
        BufferedImage bi = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        image.paintIcon(null, g, 0,0);
        g.dispose();
        scale=(int)(width);
        ImageIcon newIcon =new ImageIcon(bi.getScaledInstance(scale,scale,1));
        this.icon=new JLabel(newIcon);
        //noinspection SuspiciousNameCombination
        icon.setBounds(this.gui.getFRAME_SIZE().height,10+i*width,width,width);
        icon.setVisible(true);
        icon.setBackground(Color.DARK_GRAY);
        icon.setOpaque(true);
        icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gui.setSelectedLabel(null);
                gui.setSelectedPlayer(player);
                for(Rentable property:player.getProperties())
                {
                    if(((RentalProperty) property).isMortgaged())
                        gui.getLocationLabel(((Locatable)property)).setTempBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.red,4),BorderFactory.createLineBorder(Color.red.darker(),2)));
                    else
                        gui.getLocationLabel(((Locatable)property)).setTempBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.green,4),BorderFactory.createLineBorder(Color.red.brighter(),2)));
                }
            }
            @Override
            public void mouseEntered(MouseEvent e)
            {
                border=icon.getBorder();
                icon.setBorder(BorderFactory.createLineBorder(Color.white,2));
            }
            @Override
            public void mouseExited(MouseEvent e)
            {
                icon.setBorder(border);
            }
        });
        gui.getMainPane().add(this.icon);
        name = new JLabel("", SwingConstants.CENTER);
        gui.getMainPane().add(name);

        name.setText(player.getIdentifier());
        name.setBounds(this.gui.getFRAME_SIZE().height+width,10+i*width,2*width,width);
        int size = 32;
        name.setFont(new Font("Chiller",Font.BOLD,32));

        while(name.getFontMetrics(new Font("Chiller",Font.BOLD,size)).stringWidth(name.getText()) >2*width)
        {
            size--;
            name.setFont(new Font("Chiller",Font.BOLD,size));
        }
        name.setVisible(true);
        name.setBackground(Color.DARK_GRAY);
        name.setForeground(Color.white);
        name.setOpaque(true);

        balance.setText("$"+player.getBalance());
        balance.setBounds(this.gui.getFRAME_SIZE().height+3*width,10+i*width,width,width);
        size = 32;
        balance.setFont(new Font("Times New Roman",Font.ITALIC,32));

        while(balance.getFontMetrics(new Font("Times New Roman",Font.ITALIC,size)).stringWidth(balance.getText()) > width)
        {
            size--;
            balance.setFont(new Font("Times New Roman",Font.ITALIC,size));
        }
        balance.setVisible(true);
        balance.setOpaque(true);
        balance.setBackground(Color.DARK_GRAY);
        balance.setForeground(Color.WHITE);
        gui.getMainPane().add(balance);

        BufferedImage bi2 = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = bi2.createGraphics();
        image.paintIcon(null, g, 0,0);
        g2.dispose();
        scale=(int)((gui.getOFFSET())*.5);
        newIcon =new ImageIcon(bi.getScaledInstance(scale,scale,1));
        positionIcon= new JLabel(newIcon);
        positionIcon.setBounds(gui.getLocationLabel(player.getPosition()).getX()+(index%3*(gui.getOFFSET()/4)),
                gui.getLocationLabel(player.getPosition()).getY()+1+(index%2*(gui.getOFFSET()/2)),scale,scale);
        gui.getMainPane().add(positionIcon,index);
    }
     void updateLabel()
    {
        balance.setText(GUI.symbol+player.getBalance());
        int size = 32;
        balance.setFont(new Font("Times New Roman",Font.ITALIC,32));

        while(balance.getFontMetrics(new Font("Times New Roman",Font.ITALIC,size)).stringWidth(balance.getText()) > gui.getOFFSET())
        {
            size--;
            balance.setFont(new Font("Times New Roman",Font.ITALIC,size));
        }
        positionIcon.updateUI();
        positionIcon.setBounds(gui.getLocationLabel(player.getPosition()).getX()+(index%3*(gui.getOFFSET()/4)),
                gui.getLocationLabel(player.getPosition()).getY()+1+(index%2*(gui.getOFFSET()/2)),scale,scale);
        icon.setBorder(border);
    }

    public Player getPlayer() {
        return player;
    }

    public void removePlayer()
    {
        gui.getMainPane().remove(positionIcon);
        gui.getMainPane().remove(balance);
        gui.getMainPane().remove(icon);
        gui.getMainPane().remove(name);
        gui.getMainPane().validate();
        gui.getMainPane().repaint();

    }

    public void setCurrentPlayer() {
        icon.setBorder(BorderFactory.createLineBorder(Color.red.brighter(),3));
    }

    public void setBorder(Border border) {
        this.border = border;
    }
}
