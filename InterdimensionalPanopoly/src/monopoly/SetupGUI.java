package monopoly;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
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

    void PlayerCountGui(Panopoly panopoly1)
    {
        panopoly = panopoly1;


        //how many players
        JFrame playerFrame= new JFrame("Interdimensional Panopoly");
        JPanel playerPanel=new JPanel();
        playerFrame.setSize(400,400);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        playerFrame.setLocation(dim.width/2-playerFrame.getSize().width/2, dim.height/2-playerFrame.getSize().height/2);
        playerFrame.add(playerPanel);
        //panel changes after here
        playerPanel.setBackground(Color.WHITE);
        playerPanel.setLayout(null);
        JLabel image=new JLabel(new ImageIcon(this.getClass().getResource("media/Logo.PNG")));
        JLabel[] button=new JLabel[5];
        for(int i=0;i<5;i++)
        {
            button[i]=new JLabel("",SwingConstants.CENTER);
            button[i].setBounds(((i)*75)+7,5,70,40);
            button[i].setBorder(BorderFactory.createLineBorder(Color.white.darker().darker(),3,true));
            button[i].setText((i+2)+" Players");
            button[i].setFont(new Font("Chiller", Font.PLAIN,18));
            button[i].setForeground(Color.white);
            int finalI = i;
            button[i].addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button[finalI].setText((finalI +2)+" Players?");
                    button[finalI].setBorder(BorderFactory.createLineBorder(Color.red.darker().darker(),3,true));
                }
            });
            button[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    button[finalI].setText((finalI +2)+" Players");
                    button[finalI].setBorder(BorderFactory.createLineBorder(Color.white.darker().darker(),3,true));
                }
            });
            button[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                  //set player count
                  playerFrame.dispatchEvent(new WindowEvent(playerFrame, WindowEvent.WINDOW_CLOSING));
                  playerCount = finalI + 2;
                  AICountGui(playerCount);
                }
            });
            playerPanel.add(button[i]);
        }
        //player select
        playerFrame.setVisible(true);
        playerFrame.setResizable(false);
        image.setBounds(-10,0,400,400);//this isnt relative yet okay jeez
        playerPanel.add(image);
        //AFTER HERE IS TEST INSTANTIATION;
        //PlayerNameGUI();
    }

    void AICountGui(int PlayerCount)
    {
        //how many players
        JFrame playerFrame= new JFrame("Interdimensional Panopoly");
        JPanel playerPanel=new JPanel();
        playerFrame.setSize(400,400);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        playerFrame.setLocation(dim.width/2-playerFrame.getSize().width/2, dim.height/2-playerFrame.getSize().height/2);
        playerFrame.add(playerPanel);
        //panel changes after here
        playerPanel.setBackground(Color.WHITE);
        playerPanel.setLayout(null);
        JLabel image=new JLabel(new ImageIcon(this.getClass().getResource("media/Logo.PNG")));
        JLabel[] button=new JLabel[6];
        int width=375/PlayerCount;
        for(int i=0;i<PlayerCount;i++)
        {
            button[i]=new JLabel("",SwingConstants.CENTER);
            button[i].setBounds(((i)*width)+5,5,width,40);
            button[i].setBorder(BorderFactory.createLineBorder(Color.white.darker().darker(),3,true));
            button[i].setText((i)+" A.I.");
            button[i].setFont(new Font("Chiller", Font.PLAIN,18));
            button[i].setForeground(Color.white);
            int finalI = i;
            button[i].addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button[finalI].setText((finalI)+" A.I.?");
                    button[finalI].setBorder(BorderFactory.createLineBorder(Color.red.darker().darker(),3,true));
                }
            });
            button[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    button[finalI].setText((finalI)+" A.I.");
                    button[finalI].setBorder(BorderFactory.createLineBorder(Color.white.darker().darker(),3,true));
                }
            });
            button[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    //set player count
                    playerFrame.dispatchEvent(new WindowEvent(playerFrame, WindowEvent.WINDOW_CLOSING));
                    int AIcount = finalI;
                    PlayerNameGUI(AIcount);
                }
            });
            playerPanel.add(button[i]);
        }
        //player select
        playerFrame.setVisible(true);
        playerFrame.setResizable(false);
        image.setBounds(-10,0,400,400);//this isnt relative yet okay jeez
        playerPanel.add(image);
        //AFTER HERE IS TEST INSTANTIATION;
        //PlayerNameGUI();
    }

    private static void createIcons()
    {
        for(int i=0;i<6;i++)
        {

            try
            {
                GUI.images[i] = ImageIO.read(GUI.class.getResource("media/" + GUI.characters[i]+".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public static void PlayerNameServerGUI(Panopoly panopoly, ArrayList<Player> playerArray) {
        createIcons();

        panopoly.createNetworkedGUI(playerArray);
    }
    private static void PlayerNameGUI(int AIcount) {
        createIcons();
        playerCount=playerCount-AIcount;
        players=new ArrayList<>();
        JFrame playerFrame= new JFrame("Interdimensional Panopoly");
        JPanel playerPanel=new JPanel();
        playerFrame.setBounds(300,300,636,270);
        playerFrame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        playerFrame.setLocation(dim.width/2-playerFrame.getSize().width/2, dim.height/2-playerFrame.getSize().height/2);
        playerFrame.add(playerPanel);
        playerPanel.setLayout(null);
        playerPanel.setOpaque(true);
        playerPanel.setBackground(Color.red.darker().darker());
        playerFrame.setVisible(true);
        String[] AInames={"Bob","Rob","Dob","Sob","Jim"};
        int[] availableIcons={0,1,2,3,4,5};
        selectedpictureIndex=-1;

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
        upperline.setFont(new Font("Chiller",Font.ITALIC,28));
        upperline.setForeground(Color.white);
        playerPanel.add(upperline);
        nameSpace.setBounds(218,150,200,20);
        JButton sendinputButton=new JButton();
        sendinputButton.setBounds(218,180,200,40);
        sendinputButton.setText("PRESS TO CONFIRM");
        sendinputButton.setFocusPainted(false);
        sendinputButton.setFont(new Font("Chiller",Font.ITALIC,16));
        sendinputButton.setBorder(BorderFactory.createBevelBorder(1,Color.red,Color.red.darker()));
        sendinputButton.setBackground(Color.black);
        sendinputButton.setForeground(Color.lightGray);
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
                    availableIcons[selectedpictureIndex]=-1;
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
                    int count=0;
                    for(int i=0;i<AIcount;i++)
                    {
                        while(availableIcons[count]==-1)
                            count++;
                        players.add(new GameBot(AInames[i],availableIcons[count],playerIncrement[0],panopoly));
                        playerIncrement[0]++;
                        availableIcons[count]=-1;
                    }
                    panopoly.createGUI(players);
                }

            }
        });
        playerPanel.add(sendinputButton);
        playerPanel.add(nameSpace);
        //AFTER HERE IS TEST INSTANTIATION
        /*players=new ArrayList<Player>();
        players.add(new Player("Brian",0,0,panopoly));
        players.add(new GameBot("Chloe",1,1,panopoly));
       // players.add(new GameBot("Cian",2,2,panopoly));
//        players.add(new GameBot("Mossy",3,3,panopoly));
//        players.add(new GameBot("Tony",4,4,panopoly));
//        players.add(new GameBot("Christ.........",5,5,panopoly));
        panopoly.createGUI(players);*/
    }

    public static void getHelp() {
        JFrame helpFrame=new JFrame();
        helpFrame.setVisible(true);
        helpFrame.setBounds(100,100,1500,310);
        helpFrame.setResizable(false);
        JPanel panel=new JPanel();
        panel.setBounds(0,0,1475,300);
        helpFrame.add(panel);
        JTextArea text=new JTextArea();
        text.setBounds(0,0,1475,300);
        text.setLineWrap(true);
        text.setEditable(false);
        panel.add(text);
        text.setText(
                "1. Each player recieves Q1500 at the start of the game \n" +
                "2. A player balance and name are displayed on the right-hand side as well as indicating whose turn it is \n"+
                "3. Push the roll dice button to roll the dice, the playes icon will then move the alloted number of squares \n" +
                "4. If a player lands on an unowned property they have the option to buy it, if they have the required funds, this action is performed using the buy button.\n" +
                "4.b If the player choses not to buy said property the proerty goes to auction where a player may bid within the 12 second window using the bid button, the highest bidder wins the property.\n" +
                "5. If a player lands on a chance or community chest type square they are assigned a random card and the cards consequences are then imposed on the player \n" +
                "6. If a player lands on an owned property they must pay the rent alloted which is taken automatically from their account\n" +
                "7. If a player owns an entire colour group they can built on a relevent property by selcting it and clicking the build button, this will build a house on the proeprty increasing its rent while " +
                        " taking the cost of the house from the players balance. \n" +
                "8. Once a players passes GO square they will reciveve Q200 \n" +
                "9. If a player lands on go to jail ore receives a relevent card they roceed to jail to escape jail a player can pay the fine or answeer the supplied question correctly by clicking the \n" +
                        " appropriate button, the player is also given the option" +
                        " to use their get out of jail free card by clicking the appropriate button" +
                "10. If a player lands on free parking  they receive any money that has been collected from tax squares, etc. \n" +
                "11. If a player lands on a tax square the amount is deducted from their balance \n" +
                "12. A property once owned can be mortgaged,the player will receive the mortgage value, however hey will no longer receive rent if the properrty is landed upon this can be done by clicking " +
                        " the relevant property and clicking the mortgage button \n" +
                "13. If player is unable to escape bankruptcy they must leave the game by clicking the relevant leave button \n" +
                "14. Once the doomsday card is activated the game will end 5 minutes from that point with the remaining time indicated by the counter in the bott-right of the screen, the winner of" +
                "    the game will then be calculated based the sum total of a players assets value and balance.\n" +
                "15. A winner is declared");
    }


}
