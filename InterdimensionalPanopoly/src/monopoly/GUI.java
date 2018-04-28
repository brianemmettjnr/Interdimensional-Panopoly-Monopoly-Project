package monopoly;
import interfaces.Locatable;
import interfaces.Rentable;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

class GUI {

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
    private JLabel image;
    private static int playerCount=0;
    private static ArrayList<Player> players=new ArrayList<>();
	
    boolean buyCommand,rollCommand,endCommand;
	private JLabel buyButton, rollButton, endturn,mortgageButton, redeemButton,buildButton,demoButton;
    
    private static Panopoly panopoly;
    private static BufferedImage[] images = new BufferedImage[6];
    private JLabel locationWindow=new JLabel(" ",SwingConstants.CENTER);
    private JLabel latestAction=new JLabel("",SwingConstants.CENTER);
    private JLabel secondAction=new JLabel("",SwingConstants.CENTER);
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
        MainPane.setBackground(Color.red.darker().darker());
        mainFrame.add(MainPane);
        PlayerLabels=new PlayerLabel[players.size()];
        int SquaresOnSide=(((BOARD_SIZE-4)/4)+2);
        int frameSize=(int)(FRAME_SIZE.getHeight()*.9);
        Offset=(frameSize)/SquaresOnSide;
        PlacePlayers();
        PlaceBoard();
        setupbuttons();

        image=new JLabel(new ImageIcon(GUI.class.getResource("Logo.png")));
        image.setBounds((((int)(FRAME_SIZE.getHeight()*.9))/2)-190,(((int)(FRAME_SIZE.getHeight()*.9))/2)-220,400,400);
        image.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        MainPane.add(image);

        latestAction.setBounds((((int)(FRAME_SIZE.getHeight()*.9))/2)-190,(((int)(FRAME_SIZE.getHeight()*.9))/2)+210,400,30);
        latestAction.setVisible(true);
        latestAction.setFont(new Font("times new roman",Font.BOLD,20));
        latestAction.setForeground(Color.white);
        latestAction.setText("Welcome To Interdimensional Panopoly");
        MainPane.add(latestAction);

        secondAction.setBounds((((int)(FRAME_SIZE.getHeight()*.9))/2)-190,(((int)(FRAME_SIZE.getHeight()*.9))/2)+180,400,30);
        secondAction.setVisible(true);
        secondAction.setFont(new Font("times new roman",Font.BOLD,20));
        secondAction.setForeground(Color.white);
        secondAction.setText("Enjoy!");
        MainPane.add(secondAction);


        locationWindow.setBounds((((int)(FRAME_SIZE.getHeight()*.9))/2)-90,(((int)(FRAME_SIZE.getHeight()*.9))/2)-210,200,380);
        locationWindow.setVisible(true);
        locationWindow.setBackground(Color.WHITE);
        locationWindow.setForeground(Color.BLACK);
        locationWindow.setBorder(BorderFactory.createLineBorder(Color.black,4));
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
        rollButton.setBounds((int)((FRAME_SIZE.getHeight()*.9)/2)-10,(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,Offset,30);
        rollButton.setOpaque(true);
        rollButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gui.updateAction(panopoly.roll());
                setSelectedLabel(getLocationLabel(panopoly.getCurrentPlayer().getPosition()));
            }
        });
        buyButton=new JLabel("Buy");
        MainPane.add(buyButton);
        buyButton.setVisible(buyCommand);
        buyButton.setBounds((int)(FRAME_SIZE.getHeight()*.9)/2-getOffset()-10,(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,Offset,30);
        buyButton.setOpaque(true);
        buyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Rentable buyProperty= (Rentable)panopoly.getBoard().getLocation(panopoly.getCurrentPlayer().getPosition());
                gui.updateAction(panopoly.buyProperty(buyProperty));
                gui.buyCommand = false;
                gui.updatePlayers();
            }
        });
        endturn=new JLabel("End Turn");
        MainPane.add(endturn);
        endturn.setVisible(false);
        endturn.setBounds((int)((FRAME_SIZE.getHeight()*.9)/2)-10,(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,Offset,30);
        endturn.setOpaque(true);
        endturn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gui.updateAction(panopoly.nextPlayer());
            }
        });
        mortgageButton=new JLabel("Mortgage");
        MainPane.add(mortgageButton);
        mortgageButton.setVisible(false);
        mortgageButton.setBounds(((int)(FRAME_SIZE.getHeight()*.9)/2)-190,(((int)(FRAME_SIZE.getHeight()*.9))/2)-100,Offset,30);
        mortgageButton.setOpaque(true);
        mortgageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RentalProperty mortgageProperty = (RentalProperty)getSelectedLocation().getLocation();
                gui.updateAction(panopoly.mortgage(mortgageProperty));
            }
        });
        redeemButton =new JLabel("Redeem");
        MainPane.add(redeemButton);
        redeemButton.setVisible(false);
        redeemButton.setBounds(((int)(FRAME_SIZE.getHeight()*.9)/2)-190,(((int)(FRAME_SIZE.getHeight()*.9))/2)+-100,Offset,30);
        redeemButton.setOpaque(true);
        redeemButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RentalProperty redeemProperty = (RentalProperty)getSelectedLocation().getLocation();
                gui.updateAction(panopoly.redeem(redeemProperty));
            }
        });
        buildButton =new JLabel("Build");
        MainPane.add(buildButton);
        buildButton.setVisible(false);
        buildButton.setBounds(((int)(FRAME_SIZE.getHeight()*.9)/2)+190,(((int)(FRAME_SIZE.getHeight()*.9))/2)-85,Offset,30);
        buildButton.setOpaque(true);
        buildButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                InvestmentProperty builder=(InvestmentProperty) getSelectedLocation().getLocation();
                gui.updateAction(panopoly.buildUnit(builder));
            }
        });
        demoButton =new JLabel("Demolish");
        MainPane.add(demoButton);
        demoButton.setVisible(false);
        demoButton.setBounds(((int)(FRAME_SIZE.getHeight()*.9)/2)+190,(((int)(FRAME_SIZE.getHeight()*.9))/2)-115,Offset,30);
        demoButton.setOpaque(true);
        demoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                InvestmentProperty breaker=(InvestmentProperty) getSelectedLocation().getLocation();
                gui.updateAction(panopoly.demolishUnit(breaker));
            }
        });


    }
    
    private void setVisibleButtons()
    {
    	panopoly.setPossibleCommands();
    	rollButton.setVisible(rollCommand);
    	buyButton.setVisible(buyCommand);
    	endturn.setVisible(!rollCommand);
    }
    
    void resetCommands()
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

    LocationLabel getLocationLabel(Locatable location) throws ArrayIndexOutOfBoundsException
    {
        for(LocationLabel local:LocationLabels)
        {
            if(local.getLocation()==location)
                return local;
        }
        return null;
    }
    LocationLabel getLocationLabel(int index)throws ArrayIndexOutOfBoundsException
    {
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
        */
        GUI.PlayerNameGUI();
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
        */
        players.add(new Player("Brian",0,0,panopoly));
        players.add(new Player("Chloe",1,1,panopoly));
        players.add(new Player("Cian",2,2,panopoly));
        players.add(new Player("Mossy",3,3,panopoly));
        players.add(new Player("Tony",4,4,panopoly));
        players.add(new Player("Christ",5,5,panopoly));
        panopoly.createGUI();
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
        if(!(this.SelectedLabel==null))
            this.SelectedLabel.getLabel().setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        this.SelectedLabel=location;
        this.SelectedLabel.getLabel().setBorder(BorderFactory.createLineBorder(Color.green.darker(),2));
        locationWindow.setOpaque(true);
        if(location==null)
        {
            image.setVisible(true);
            locationWindow.setText(" ");
            locationWindow.setOpaque(false);
            mortgageButton.setVisible(false);
            redeemButton.setVisible(false);
            buildButton.setVisible(false);
            demoButton.setVisible(false);
        }
        else
        {
            image.setVisible(false);
            if (location.getLocation() instanceof RentalProperty)
            {
                RentalProperty mortgageCheck = (RentalProperty) location.getLocation();
                if (mortgageCheck.getOwner() == panopoly.getCurrentPlayer())
                {
                    redeemButton.setVisible(mortgageCheck.isMortgaged());
                    mortgageButton.setVisible(!mortgageCheck.isMortgaged());
                }
                else
                {
                    mortgageButton.setVisible(false);
                    redeemButton.setVisible(false);
                }
                if(location.getLocation() instanceof InvestmentProperty)
                {
                	
                }
            }
            locationWindow.setText(location.getHTML());
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

   void updateAction(String action)
    {
	   updatePlayers();
	   secondAction.setText(latestAction.getText());
	   latestAction.setText(action);
       setSelectedLabel(getSelectedLocation());
    }

    int getBOARD_SIZE() {
        return BOARD_SIZE;
    }

    int getOffset()
    {
        return  Offset;
    }
}