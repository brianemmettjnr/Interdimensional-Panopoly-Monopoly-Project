package monopoly;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerLabel
{
    private final int scale;
    private Player player;
    private int index;
    private JLabel name=new JLabel("",SwingConstants.CENTER);
    private JLabel balance=new JLabel("",SwingConstants.CENTER);
    private JLabel icon;
    private GUI gui;
    private JLabel positionIcon;
    public PlayerLabel(Player player, int i, ImageIcon icon,GUI gui)
    {
        this.gui=gui;
        this.player=player;
        this.index=i;
        this.icon=new JLabel(icon);

        this.icon.setBounds(this.gui.getFRAME_SIZE().height,i*100,100,100);
        this.icon.setVisible(true);
        this.icon.setBackground(Color.DARK_GRAY);
        this.icon.setOpaque(true);
        gui.getMainPanel().add(this.icon);
        gui.getMainPanel().add(name);

        name.setText(player.getIdentifier());
        name.setFont(new Font("Arial",Font.BOLD,32));
        name.setBounds(this.gui.getFRAME_SIZE().height+100,i*100,200,100);
        name.setVisible(true);
        name.setBackground(Color.DARK_GRAY);
        name.setForeground(Color.white);
        name.setOpaque(true);

        balance.setText("$"+player.getBalance());
        balance.setFont(new Font("Times New Roman",Font.ITALIC,32));
        balance.setBounds(this.gui.getFRAME_SIZE().height+300,i*100,100,100);
        balance.setVisible(true);
        balance.setOpaque(true);
        balance.setBackground(Color.DARK_GRAY);
        balance.setForeground(Color.WHITE);
        gui.getMainPanel().add(balance);

        BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        icon.paintIcon(null, g, 0,0);
        g.dispose();
        scale=(int)(gui.getLabelWidth()*.5);
        ImageIcon newIcon =new ImageIcon(bi.getScaledInstance(scale,scale,1));
        positionIcon=new JLabel(newIcon);
        positionIcon.setBounds(gui.getPropertyLabel(player.getPosition()).getX()+(index*20),gui.getPropertyLabel(player.getPosition()).getY(),scale,scale);
        gui.getMainPanel().add(positionIcon);
    }
    public void updateLabel()
    {
        balance.setText("$"+player.getBalance());
        positionIcon.setBounds(gui.getPropertyLabel(player.getPosition()).getX()+(index*20),gui.getPropertyLabel(player.getPosition()).getY(),scale,scale);
    }
}
