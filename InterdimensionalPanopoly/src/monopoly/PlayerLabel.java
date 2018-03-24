package monopoly;

import javax.swing.*;
import java.awt.*;

public class PlayerLabel
{
    private Player player;
    private int index;
    private JLabel name=new JLabel();
    private JLabel balance=new JLabel();
    private JLabel icon;
    private GUI gui;
    public PlayerLabel(Player player, int i, ImageIcon icon,GUI gui)
    {
        this.gui=gui;
        this.player=player;
        this.index=i;
        this.icon=new JLabel(icon);
        name.setText(player.getIdentifier());
        balance.setText("$"+player.getBalance());
        balance.setFont(new Font("Times New Roman",Font.ITALIC,32));
        this.icon.setBounds(this.gui.getFRAME_SIZE().height,i*100,100,100);
        this.icon.setVisible(true);
        gui.getMainPanel().add(this.icon);
        name.setFont(new Font("Arial",Font.BOLD,32));
        name.setBounds(this.gui.getFRAME_SIZE().height+120,i*100,200,100);
        name.setVisible(true);
        gui.getMainPanel().add(name);
        balance.setBounds(this.gui.getFRAME_SIZE().height+320,i*100,100,100);
        balance.setVisible(true);
        gui.getMainPanel().add(balance);

    }
    public void updateLabel()
    {
        balance.setText("$"+player.getBalance());
    }
}
