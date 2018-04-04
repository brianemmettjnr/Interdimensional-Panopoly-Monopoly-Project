package monopoly;
import interfaces.Locatable;
import interfaces.Rentable;

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

import static java.awt.Color.*;

public class GUI {

    private int BOARD_SIZE;
    private Dimension FRAME_SIZE=Toolkit.getDefaultToolkit().getScreenSize();
    private LocationLabel[] LocationLabels;
    private PlayerLabel[] PlayerLabels;
    private JLayeredPane MainPane;
    private int Offset;
    private static JTextField nameSpace=new JTextField();
    private LocationLabel SelectedLabel=null;
    private static String[] characters={"boat","car","dog","hat","iron","thimble"};
    private static int selectedpictureIndex=-1;
    private static JLabel selectedImage =null;
    private static int playerCount=0;
    private static ArrayList<Player> players=new ArrayList<Player>();
	
    boolean buyCommand,rollCommand,sellCommand,redeemCommand,mortgageCommand,endCommand;
	private JLabel buyButton, rollButton, endturn;
    
    private static Panopoly panopoly;
    private static BufferedImage[] images = new BufferedImage[6];
    private JLabel locationWindow=new JLabel(" ",SwingConstants.CENTER);
    private JLabel latestAction=new JLabel("",SwingConstants.CENTER);
    private GUI gui=this;

    GUI(int BoardSize)
    {
        BOARD_SIZE=BoardSize;
        JFrame mainFrame = new JFrame("Interdimensional Panopoly");
        mainFrame.setSize(FRAME_SIZE);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        LocationLabels=new LocationLabel[BOARD_SIZE];
        MainPane = new JLayeredPane();
        MainPane.setLayout(null);
        MainPane.setOpaque(true);
        MainPane.setBackground(Color.red.darker());
        mainFrame.add(MainPane);
        PlayerLabels=new PlayerLabel[players.size()];
        int SquaresOnSide=(((BOARD_SIZE-4)/4)+2);
        int frameSize=(int)(FRAME_SIZE.getHeight()*.9);
        Offset=(frameSize)/SquaresOnSide;
        PlacePlayers();
        PlaceBoard();
        setupbuttons();

        JLabel image=new JLabel(new ImageIcon(GUI.class.getResource("ReasonsWhyBrianIsntAGraphicDesigner.png")));
        image.setBounds((((int)(FRAME_SIZE.getHeight()*.9))/2)-190,(((int)(FRAME_SIZE.getHeight()*.9))/2)-190,400,400);
        image.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        MainPane.add(image);

        latestAction.setBounds((((int)(FRAME_SIZE.getHeight()*.9))/2)-190,(((int)(FRAME_SIZE.getHeight()*.9))/2)+210,400,30);
        latestAction.setVisible(true);
        latestAction.setFont(new Font("times new roman",Font.BOLD,12));
        latestAction.setForeground(Color.white);
        latestAction.setText("Welcome To Interdimensional Panopoly");
        MainPane.add(latestAction);

        locationWindow.setBounds((((int)(FRAME_SIZE.getHeight()*.9))/2)-90,(((int)(FRAME_SIZE.getHeight()*.9))/2)-180,200,380);
        locationWindow.setVisible(true);
        locationWindow.setBackground(Color.WHITE);
        locationWindow.setForeground(Color.BLACK);
        locationWindow.setBorder(BorderFactory.createLineBorder(Color.black,4,true));
        locationWindow.setVerticalAlignment(JLabel.TOP);
        MainPane.add(locationWindow);
        mainFrame.setVisible(true);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void setupbuttons()
    {
        rollButton=new JLabel("Roll");
        MainPane.add(rollButton);
        rollButton.setVisible(true);
        rollButton.setBounds(10+
                Offset,(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,Offset,30);
        rollButton.setOpaque(true);
        rollButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panopoly.roll();
            }
        });
        buyButton=new JLabel("Buy");
        MainPane.add(buyButton);
        buyButton.setVisible(buyCommand);
        buyButton.setBounds(10+Offset*2,(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,Offset,30);
        buyButton.setOpaque(true);
        buyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panopoly.buyProperty((Rentable)panopoly.getBoard().getLocation(panopoly.getCurrentPlayer().getPosition()));
            }
        });
        endturn=new JLabel("End Turn");
        MainPane.add(endturn);
        endturn.setVisible(true);
        endturn.setBounds(10+Offset*3,(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,Offset,30);
        endturn.setOpaque(true);
        endturn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panopoly.nextPlayer();
            }
        });
    }
    
    private void setVisibleButtons()
    {
    	panopoly.setPossibleCommands();
    	rollButton.setVisible(rollCommand);
    	buyButton.setVisible(buyCommand);
    	endturn.setVisible(endCommand);
    }
    
    public void resetCommands()
    {
    	rollCommand = false;
    	buyCommand = false;
    	endCommand = false;
    }
    
    private void PlacePlayers()
    {
        int i=0;
        for(Player player:players)
        {
            PlayerLabels[i]=new PlayerLabel(player,i,new ImageIcon(images[player.getImageIndex()]),this);
            i++;
        }
    }

    LocationLabel getLocationLabel(int index) throws ArrayIndexOutOfBoundsException{
        return LocationLabels[index];
    }

    private void PlaceBoard()
    {
        ArrayList<Locatable> Locations=panopoly.getBoard().getLocations();
        int x=10,y=10;
        int SquaresOnSide=(((BOARD_SIZE-4)/4)+2);
        int NumOnBoard=0;
        while (x<Offset*(SquaresOnSide-1))
        {
            LocationLabels[NumOnBoard]= new LocationLabel(x,y,NumOnBoard,this,Locations.get(NumOnBoard));
            x+=Offset;
            NumOnBoard++;

        }
        while(y<(Offset*(SquaresOnSide-1)))
        {
            LocationLabels[NumOnBoard]= new LocationLabel(x,y,NumOnBoard,this,Locations.get(NumOnBoard));
            y+=Offset;
            NumOnBoard++;

        }
        while (x>=Offset)
        {
            LocationLabels[NumOnBoard]= new LocationLabel(x,y,NumOnBoard,this,Locations.get(NumOnBoard));
            x-=Offset;
            NumOnBoard++;
        }
        while (y>=Offset)
        {
            LocationLabels[NumOnBoard]= new LocationLabel(x,y,NumOnBoard,this,Locations.get(NumOnBoard));
            y-=Offset;
            NumOnBoard++;
        }
    }

    static void PlayerCountGui(Panopoly panopoly1)
    {
    	panopoly = panopoly1;
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
        Border border=BorderFactory.createLineBorder(MAGENTA,2,true);
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
                  playerCount= finalI+2;
                  GUI.PlayerNameGUI();
                }
            });
            playerPanel.add(button[i]);
        }
        //player select
        playerFrame.setVisible(true);
        playerFrame.setResizable(false);
        image.setBounds(-10,0,400,100);//this isnt relative yet okay jeez
        playerPanel.add(image);
    }

    private static void PlayerNameGUI() {
        for(int i=0;i<6;i++)
        {

            try
            {
                images[i] = ImageIO.read(GUI.class.getResource(characters[i]+".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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
            JLabel image=new JLabel(new ImageIcon(images[i]));
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
                    panopoly.createGUI();
                }

            }
        });
        playerPanel.add(sendinputButton);
        playerPanel.add(nameSpace);
    }

    static ArrayList<Player> getPlayersArray()
    {
        return players;
    }

    LocationLabel getSelectedLocation()
    {
        return SelectedLabel;
    }

    void setSelectedLabel(LocationLabel location)
    {
        this.SelectedLabel=location;
        locationWindow.setOpaque(true);
        if(location==null)
        {
            locationWindow.setText(" ");
            locationWindow.setOpaque(false);
        }
        else
        {
            Class Comparitor = location.getLocation().getClass();
            String colour="";
            //I WOULD MAKE THIS A SWITCH STATEMENT BUT APPARENTLY THEY CANT ACCEPT CLASSES AS ARGUMENTS??????
            if (Comparitor == Chance.class)
                colour="red";
            else if(Comparitor == CommunityChest.class)
                colour="blue";
            else if(Comparitor == Station.class)
                colour="gray";
            locationWindow.setText("<html><font color=\""+colour+"\">"+SelectedLabel.getLocation().getIdentifier()+"</font><br>"+"</html>");
        }



    }

    void updatePlayers()
    {
        for(PlayerLabel player:PlayerLabels)
        {
            player.updateLabel();
            if(player.getPlayer()==panopoly.getCurrentPlayer())
            {
                player.setCurrentPlayer();
            }
        }
        
        setVisibleButtons();
    }

    JLayeredPane getMainPane()
    {
        return MainPane;
    }

    Dimension getFRAME_SIZE() {
        return FRAME_SIZE;
    }

    public void updateAction(String action)
    {
        latestAction.setText(action);
    }

    int getBOARD_SIZE() {
        return BOARD_SIZE;
    }

    int getOffset()
    {
        return  Offset;
    }
}