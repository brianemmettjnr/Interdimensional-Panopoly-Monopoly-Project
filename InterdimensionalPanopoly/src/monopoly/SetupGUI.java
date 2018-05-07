package monopoly;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


public class SetupGUI
{
    private static Panopoly panopoly;
    private static int playerCount;
    private static JLabel selectedImage;
    private static int selectedpictureIndex=-1;
    private static JTextField nameSpace=new JTextField();
    private static ArrayList<Player> players=new ArrayList<>();

    static void PlayerCountGui(Panopoly panopoly1)
    {
        panopoly = panopoly1;
        /*

        //how many players
        JFrame playerFrame= new JFrame("Interdimensional Panopoly");
        JPanel playerPanel=new JPanel();

        playerFrame.setSize(400,170);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        playerFrame.setLocation(dim.width/2-playerFrame.getSize().width/2, dim.height/2-playerFrame.getSize().height/2);
        playerFrame.add(playerPanel);
        //panel changes after here
        playerPanel.setBackground(Color.WHITE);
        playerPanel.setLayout(null);
        JLabel image=new JLabel(new ImageIcon(GUI.class.getResource("MiniLogo.png")));
        JLabel[] button=new JLabel[5];
        Border border=BorderFactory.createLineBorder(Color.MAGENTA,2,true);
        for(int i=0;i<5;i++)
        {
            button[i]=new JLabel();
            button[i].setBounds(((i)*75)+7,80,70,40);
            button[i].setBorder(border);
            button[i].setText("  "+(i+2)+" Players");
            button[i].setFont(new Font("Times New Roman",Font.BOLD,12));
            int finalI = i;
            button[i].addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button[finalI].setText("  "+(finalI +2)+" Players?");
                    button[finalI].setBorder(BorderFactory.createLineBorder(Color.blue,3,true));
                }
            });
            button[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    button[finalI].setText("  "+(finalI +2)+" Players");
                    button[finalI].setBorder(border);
                }
            });
            button[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                  //set player count
                  playerFrame.dispatchEvent(new WindowEvent(playerFrame, WindowEvent.WINDOW_CLOSING));
                  playerCount = finalI + 2;
                  PlayerNameGUI();
                }
            });
            playerPanel.add(button[i]);
        }
        //player select
        playerFrame.setVisible(true);
        playerFrame.setResizable(false);
        image.setBounds(-10,0,400,100);//this isnt relative yet okay jeez
        playerPanel.add(image);*/
        //AFTER HERE IS TEST INSTANTIATION;
        PlayerNameGUI();
    }
    private static void createIcons()
    {
        for(int i=0;i<6;i++)
        {

            try
            {
                GUI.images[i] = ImageIO.read(GUI.class.getResource("media/"+GUI.characters[i]+".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public static void PlayerNameServerGUI(Panopoly panopoly, ArrayList<Player> playerArray) {
        createIcons();

        panopoly.createNetworkedGUI(playerArray);
    }
    private static void PlayerNameGUI() {
        createIcons();
        /*
        JFrame playerFrame= new JFrame("Interdimensional Panopoly");
        JPanel playerPanel=new JPanel();
        playerFrame.setBounds(300,300,636,270);
        playerFrame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        playerFrame.setLocation(dim.width/2-playerFrame.getSize().width/2, dim.height/2-playerFrame.getSize().height/2);
        playerFrame.add(playerPanel);
        playerPanel.setLayout(null);
        playerPanel.setOpaque(true);
        playerPanel.setBackground(Color.DARK_GRAY);
        playerFrame.setVisible(true);
        for(int i=0;i<6;i++)
        {
            JLabel image=new JLabel(new ImageIcon(GUI.images[i]));
            playerPanel.add(image);
            image.setBounds(10+(i*100),40,100,100);
            image.setText(String.valueOf(i));
            int finalI = i;
            image.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(selectedImage ==image) {
                        image.setBorder(BorderFactory.createLineBorder(Color.black,3,true));
                        selectedImage = null;
                    }
                    else {
                        if(selectedImage!=null)
                            selectedImage.setBorder(null);
                        image.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3,true));
                        selectedImage = image;
                        selectedpictureIndex = finalI;
                    }
                }
            });
            image.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if(selectedImage !=image) {
                        image.setBorder(BorderFactory.createLineBorder(Color.black, 3,true));
                    }
                }
            });
            image.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    if(selectedImage!=image)
                        image.setBorder(null);
                }
            });
        }
        JLabel upperline=new JLabel("Select your Icon, then enter your username.");
        upperline.setBounds(120,10,600,20);
        upperline.setFont(new Font("Times New Roman",Font.ITALIC,20));
        upperline.setForeground(Color.white);
        playerPanel.add(upperline);
        nameSpace.setBounds(218,150,200,20);
        JButton sendinputButton=new JButton();
        sendinputButton.setBounds(218,180,200,40);
        sendinputButton.setText("PRESS TO CONFIRM");
        sendinputButton.setFont(new Font("Times New Roman",Font.ITALIC,16));
        nameSpace.setText("");
        final int[] playerIncrement = {0};
        sendinputButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!(nameSpace.getText().isEmpty()||selectedImage==null||nameSpace.getText()==null))
                {
                    upperline.setForeground(Color.white);
                    players.add(new Player(nameSpace.getText(),selectedpictureIndex,playerIncrement[0],panopoly));
                    nameSpace.setText("");
                    selectedImage.setVisible(false);
                    selectedImage=null;
                    playerIncrement[0]++;
                }
                else
                {
                    upperline.setForeground(Color.red);
                }
                if(playerCount== playerIncrement[0])
                {
                    playerFrame.dispatchEvent(new WindowEvent(playerFrame, WindowEvent.WINDOW_CLOSING));
                    //create main game GUI

                    panopoly.createGUI(players);
                }

            }
        });
        playerPanel.add(sendinputButton);
        playerPanel.add(nameSpace);
        *///AFTER HERE IS TEST INSTANTIATION
        players=new ArrayList<Player>();
        players.add(new Player("Brian",0,0,panopoly));
        players.add(new Player("Chloe",1,1,panopoly));
        players.add(new GameBot("Cian",2,2,panopoly));
        players.add(new GameBot("Mossy",3,3,panopoly));
        players.add(new GameBot("Tony",4,4,panopoly));
        players.add(new GameBot("Christ.........",5,5,panopoly));
        panopoly.createGUI(players);
    }

    public static void getHelp() {
        JFrame helpFrame=new JFrame();
        helpFrame.setVisible(true);
        helpFrame.setBounds(100,100,450,600);
        helpFrame.setResizable(false);
        JPanel panel=new JPanel();
        panel.setBounds(0,0,400,600);
        helpFrame.add(panel);
        JTextArea text=new JTextArea();
        text.setBounds(0,0,400,600);
        text.setLineWrap(true);
        text.setEditable(false);
        panel.add(text);
        text.setText("Go away I havent written actual rules yet shut up chloe");
    }


}
