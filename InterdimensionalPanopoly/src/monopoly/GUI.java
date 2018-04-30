package monopoly;
import interfaces.Groupable;
import interfaces.Locatable;
import interfaces.Rentable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

class GUI {

    private final int SquaresOnSide;
    private final JFrame mainFrame;
    private int BOARD_SIZE;
    private Dimension FRAME_SIZE=Toolkit.getDefaultToolkit().getScreenSize();
    private LocationLabel[] LocationLabels;
    private PlayerLabel[] PlayerLabels;
    private JLayeredPane MainPane;
    private int Offset;
    private LocationLabel SelectedLabel=null;
    static String[] characters={"boat","car","dog","hat","iron","thimble"};
    private JLabel image;
    private ArrayList<Player> players=new ArrayList<>();
	
    boolean rollCommand,endCommand;
	private GUIButton helpButton,buyButton, rollButton, endturn, mortgageButton,
            leaveButton, redeemButton,buildButton,demoButton,quitButton;
    
    private Panopoly panopoly;
    static BufferedImage[] images = new BufferedImage[6];
    private JLabel locationWindow=new JLabel(" ",SwingConstants.CENTER);
    private JLabel latestAction=new JLabel("",SwingConstants.CENTER);
    private JLabel secondAction=new JLabel("",SwingConstants.CENTER);
    private JLabel thirdAction=new JLabel("",SwingConstants.CENTER);
    private GUI gui=this;

    GUI(int BoardSize,Panopoly panopoly,ArrayList<Player> players)
    {
        this.setPanopoly(panopoly);
        this.setPlayers(players);
        BOARD_SIZE=BoardSize;
        mainFrame = new JFrame("Interdimensional Panopoly");
        mainFrame.setSize(FRAME_SIZE);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        LocationLabels=new LocationLabel[BOARD_SIZE];
        MainPane = new JLayeredPane();
        MainPane.setLayout(null);
        MainPane.setOpaque(true);
        MainPane.setBackground(Color.red.darker().darker());
        mainFrame.add(MainPane);
        PlayerLabels=new PlayerLabel[players.size()];
        SquaresOnSide=(((BOARD_SIZE-4)/4)+2);
        int frameSize=(int)(FRAME_SIZE.getHeight()*.9);
        Offset=(frameSize)/SquaresOnSide;
        PlaceBoard();
        PlacePlayers();
        setupbuttons();

        image=new JLabel(new ImageIcon(GUI.class.getResource("Logo.png")));
        image.setBounds((((int)(FRAME_SIZE.getHeight()*.9))/2)-190,(((int)(FRAME_SIZE.getHeight()*.9))/2)-240,400,400);
        image.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        MainPane.add(image);

        latestAction.setBounds(10+Offset,(((int)(FRAME_SIZE.getHeight()*.9))/2)+210,Offset*(SquaresOnSide-2),30);
        latestAction.setVisible(true);
        latestAction.setFont(new Font("times new roman",Font.BOLD,20));
        latestAction.setForeground(Color.white);
        latestAction.setText("Welcome To Interdimensional Panopoly");
        MainPane.add(latestAction);

        secondAction.setBounds(10+Offset,(((int)(FRAME_SIZE.getHeight()*.9))/2)+180,Offset*(SquaresOnSide-2),30);
        secondAction.setVisible(true);
        secondAction.setFont(new Font("times new roman",Font.BOLD,20));
        secondAction.setForeground(Color.white);
        secondAction.setText("Enjoy!");
        MainPane.add(secondAction);

        thirdAction.setBounds(10+Offset,(((int)(FRAME_SIZE.getHeight()*.9))/2)+150,Offset*(SquaresOnSide-2),30);
        thirdAction.setVisible(true);
        thirdAction.setFont(new Font("times new roman",Font.BOLD,20));
        thirdAction.setForeground(Color.white);
        thirdAction.setText("");
        MainPane.add(thirdAction);

        locationWindow.setBounds((((int)(FRAME_SIZE.getHeight()*.9))/2)-90,(((int)(FRAME_SIZE.getHeight()*.9))/2)-240,200,380);
        locationWindow.setVisible(true);
        locationWindow.setBackground(Color.WHITE);
        locationWindow.setForeground(Color.BLACK);
        locationWindow.setBorder(BorderFactory.createLineBorder(Color.black,4));
        locationWindow.setVerticalAlignment(JLabel.TOP);
        MainPane.add(locationWindow);
        mainFrame.setVisible(true);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    void setPanopoly(Panopoly panopoly) {
        this.panopoly = panopoly;
    }

    private void setupbuttons()
    {
        helpButton=new GUIButton("Help", (int)FRAME_SIZE.getWidth() - 40, 10, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SetupGUI.getHelp();
            }
        },this);
        rollButton=new GUIButton("Roll",(int)(10+(Offset*((SquaresOnSide-1)/2.0))),(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,
        new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gui.updateAction(panopoly.roll());
                if(getSelectedLocation()!=getLocationLabel(panopoly.getCurrentPlayer().getPosition())) {
                    setSelectedLabel(getLocationLabel(panopoly.getCurrentPlayer().getPosition()));
                }
            }
        },this);

        buyButton=new GUIButton("Buy",(int)(10+(Offset*((SquaresOnSide-1)/2.0))),Offset+20,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Rentable buyProperty= (Rentable)panopoly.getBoard().getLocation(panopoly.getCurrentPlayer().getPosition());
                        gui.updateAction(panopoly.buyProperty(buyProperty));
                        gui.updateGUI();
                    }
                },this);

        endturn=new GUIButton("End",(int)(10+(Offset*((SquaresOnSide-1)/2.0))),(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gui.updateAction(panopoly.nextPlayer());
                    }
                },this);

        mortgageButton=new GUIButton("Mortgage",((int)(FRAME_SIZE.getHeight()*.9)/2)-190,(((int)(FRAME_SIZE.getHeight()*.9))/2)-100,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        RentalProperty mortgageProperty = (RentalProperty)getSelectedLocation().getLocation();
                        gui.updateAction(panopoly.mortgage(mortgageProperty));
                    }
                },this);
        redeemButton =new GUIButton("Redeem",((int)(FRAME_SIZE.getHeight()*.9)/2)-190,(((int)(FRAME_SIZE.getHeight()*.9))/2)+-100,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        RentalProperty redeem=(RentalProperty)getSelectedLocation().getLocation();
                        gui.updateAction(panopoly.redeem(redeem));
                    }
                },this);

        buildButton =new GUIButton("Build",((int)(FRAME_SIZE.getHeight()*.9)/2)+190,(((int)(FRAME_SIZE.getHeight()*.9))/2)-85,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        InvestmentProperty builder=(InvestmentProperty) getSelectedLocation().getLocation();
                        gui.updateAction(panopoly.buildUnit(builder));
                    }
                },this);

        demoButton =new GUIButton("Demolish",((int)(FRAME_SIZE.getHeight()*.9)/2)+190,(((int)(FRAME_SIZE.getHeight()*.9))/2)-115,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        InvestmentProperty breaker=(InvestmentProperty) getSelectedLocation().getLocation();
                        gui.updateAction(panopoly.demolishUnit(breaker));
                    }
                },this);
        leaveButton =new GUIButton("Leave",(int)(10+(Offset*((SquaresOnSide-1)/2.0)))+Offset,(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        panopoly.leaveGame();
                    }
                },this);
        quitButton =new GUIButton("Quit",(int)(10+(Offset*((SquaresOnSide-1)/2.0)))+Offset*2,(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        panopoly.endGame(panopoly.decideWinner());
                    }
                },this);
        quitButton.setVisible(true);

    }
    
    private void setVisibleButtons()
    {
    	panopoly.setPossibleCommands();
    	rollButton.setVisible(rollCommand);
    	endturn.setVisible(endCommand);
    	if (panopoly.getCurrentPlayer().isInJail())
        {
            setSelectedLabel(getLocationLabel(panopoly.getCurrentPlayer().getPosition()));
        }
        leaveButton.setVisible(!rollCommand&&!endCommand);
    }


    void resetCommands()
    {
    	rollCommand = false;
    	endCommand = false;
    }
    
    public void PlacePlayers()
    {
        int i=0;
        for(Player player:players)
        {
            PlayerLabels[i]=new PlayerLabel(player,i,new ImageIcon(images[player.getImageIndex()]),this);
            i++;
        }
    }
    public void deletePlayers()
    {
        for(PlayerLabel label:PlayerLabels)
        {
            label.removePlayer();
            label=null;
        }
    }
    public void leaveGame(Player player)
    {
        players.remove(player);
        deletePlayers();
        PlacePlayers();
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
    private LocationLabel getSelectedLocation()
    {
        return SelectedLabel;
    }

    void setSelectedLabel(LocationLabel location)
    {
        for(LocationLabel label:gui.getLocationLabels())
        {
            label.resetBorder();
        }
        if(this.SelectedLabel!=null)
        {
            this.SelectedLabel.getLabel().setBorder(BorderFactory.createLineBorder(Color.BLACK,2));

        }

        if(location==null||this.SelectedLabel==location)
        {
            this.SelectedLabel=null;
            image.setVisible(true);
            locationWindow.setText(" ");
            locationWindow.setOpaque(false);
            buyButton.setVisible(false);
            mortgageButton.setVisible(false);
            redeemButton.setVisible(false);
            buildButton.setVisible(false);
            demoButton.setVisible(false);
        }
        else
        {
            this.SelectedLabel=location;
            this.SelectedLabel.getLabel().setBorder(BorderFactory.createLineBorder(Color.green.darker(),2));
            locationWindow.setOpaque(true);
            image.setVisible(false);
            if (location.getLocation() instanceof RentalProperty)
            {
                RentalProperty locationCheck = (RentalProperty) location.getLocation();
                if (locationCheck.getOwner() == panopoly.getCurrentPlayer())
                {
                    Boolean mortgageable=true;

                    if(locationCheck instanceof InvestmentProperty)
                    {
                        mortgageable = !((InvestmentProperty) locationCheck).hasBuildings();
                    }

                    redeemButton.setVisible(locationCheck.isMortgaged()
                            &&locationCheck.getRedeemAmount()<=panopoly.getCurrentPlayer().getBalance());
                    mortgageButton.setVisible(!locationCheck.isMortgaged() && mortgageable);
                }
                else
                {
                    buyButton.setVisible(locationCheck.getOwner()==null&&locationCheck.getPrice()<=panopoly.getCurrentPlayer().getBalance()
                            &&panopoly.getCurrentPlayer().getPosition()==location.getIndex());
                    mortgageButton.setVisible(false);
                    redeemButton.setVisible(false);
                }
                if(location.getLocation() instanceof InvestmentProperty)
                {
                    InvestmentProperty investment=(InvestmentProperty)location.getLocation();
                    if(locationCheck.getOwner()==panopoly.getCurrentPlayer())
                    {
                        Boolean buildable=true;//((Player)investment.getOwner()).ownsGroup(locationCheck.getGroup());
                        for(Groupable groupLocation:locationCheck.getGroup().getMembers())
                        {
                            if(((InvestmentProperty)groupLocation).isMortgaged()||((InvestmentProperty) groupLocation).getNumBuildings()<investment.getNumBuildings())
                            {
                                buildable=false;
                                break;
                            }
                        }
                        demoButton.setVisible((investment.hasBuildings()));
                        buildButton.setVisible(buildable&&investment.hotels==0&&investment.getOwner().getBalance()>=investment.buildPrice);
                    }
                    else
                    {
                        demoButton.setVisible(false);
                        buildButton.setVisible(false);
                    }
                }
                else
                {
                    demoButton.setVisible(false);
                    buildButton.setVisible(false);
                }

            }

            else
            {
                demoButton.setVisible(false);
                buildButton.setVisible(false);
                mortgageButton.setVisible(false);
                redeemButton.setVisible(false);
            }
            locationWindow.setText(location.getHTML());
        }



    }

    void updateGUI()
    {
        for(PlayerLabel player:PlayerLabels)
        {
            player.updateLabel();
            if(player.getPlayer()==panopoly.getCurrentPlayer())
            {
                player.setCurrentPlayer();
            }
            if(player.getPlayer().isInJail())
            {
                //add more here
                player.setBorder(BorderFactory.createBevelBorder(1));
            }
            else
                player.setBorder(null);
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
        String[] lines;
        if(action.contains("\n"))
        {
            lines = action.split("\n");
            thirdAction.setText(latestAction.getText());
            secondAction.setText(lines[0]);
            latestAction.setText(lines[1]);
        }
        else{
            thirdAction.setText(secondAction.getText());
            secondAction.setText(latestAction.getText());
            latestAction.setText(action);
        }
        LocationLabel label=getSelectedLocation();
        setSelectedLabel(label);
        setSelectedLabel(label);
        panopoly.setPossibleCommands();
        updateGUI();
    }

    int getBOARD_SIZE()
    {
        return BOARD_SIZE;
    }
    int getOffset()
    {
        return  Offset;
    }

     private LocationLabel[] getLocationLabels() {
        return LocationLabels;
    }
    ArrayList<Player> getPlayers() {
        return players;
    }
    public void endGame()
    {
        JFrame playerFrame= new JFrame("Interdimensional Panopoly");
        JPanel playerPanel=new JPanel();
        playerFrame.setBounds(300,300,636,270);
        playerFrame.add(playerPanel);
        playerPanel.setOpaque(true);
        playerPanel.setBackground(Color.DARK_GRAY);
        playerFrame.setVisible(true);
        JButton newGame =new JButton("New Game?");
        newGame.setSize(100,50);
        playerPanel.add(newGame);

        newGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.dispose();
                playerFrame.dispose();
                Main.createPanopoly();
            }
        });
        JButton endGame =new JButton("End Game?");
        endGame.setSize(100,50);
        playerPanel.add(endGame);

        endGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.dispose();
                playerFrame.dispose();
            }
        });

    }
    
}