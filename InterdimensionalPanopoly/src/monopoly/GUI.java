package monopoly;
import base.PersonOfInterest;
import interfaces.Groupable;
import interfaces.InteractionAPI;
import interfaces.Locatable;
import interfaces.Rentable;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class GUI implements InteractionAPI {

    //game setup
    static String[] characters={"boat","car","dog","hat","iron","thimble"};
    static String symbol="Q";
    static BufferedImage[] images = new BufferedImage[6];

    //Board Dimensions info
    private final int squaresOnSide;
    private int boardSize;
    private Dimension FRAME_SIZE=Toolkit.getDefaultToolkit().getScreenSize();
    private final int OFFSET;


    //Board frames, panel and labels
    private final JFrame mainFrame;
    private JLayeredPane mainPane;
    private JLabel image;
    private JLabel locationWindow=new JLabel(" ",SwingConstants.CENTER);
    private JLabel questionWindow=new JLabel(" ",SwingConstants.CENTER);
    private JLabel latestAction=new JLabel("",SwingConstants.CENTER);
    private JLabel secondAction=new JLabel("",SwingConstants.CENTER);
    private JLabel thirdAction=new JLabel("",SwingConstants.CENTER);
    private GUIButton auctionTimer;
    private JLabel doomsdayTimer=new JLabel("",SwingConstants.CENTER);
    private JPanel cardPanel = new JPanel();
    private JTextField tradeBox=new JTextField();

    //Arrays of objects
    private LocationLabel[] locationLabels;
    private PlayerLabel[] PlayerLabels;
    private ArrayList<Player> players=new ArrayList<>();

    //Buttons
    private boolean rollCommand,endCommand;
    GUIButton buyButton;
    GUIButton rollButton;
    GUIButton endButton;
    private GUIButton mortgageButton;
    private GUIButton leaveButton;
    private GUIButton redeemButton;
    private GUIButton buildButton;
    private GUIButton demolishButton;
    private GUIButton quitButton;
    private GUIButton[] answers =new GUIButton[4];
    private GUIButton bidButton;
    private GUIButton GOOJButton;
    private GUIButton tradeButton;
	private MouseAdapter correct=new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            answerCorrectlyFunction();
        }
    };
    private MouseAdapter incorrect=new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            answerIncorrectlyFunction();
        }
    };

    //Game instance objects
    private GUI gui=this;
    private Player assignedPlayer;
    private Panopoly panopoly;
    private PersonOfInterest personOfInterest=new PersonOfInterest();
    private LocationLabel selectedLabel =null;
    private boolean noQuestion=true;

    GUI(int boardSize,Panopoly panopoly,ArrayList<Player> players,Player player)
    {
        FRAME_SIZE=new Dimension((int)(50+(FRAME_SIZE.width/2)),(int)(50+FRAME_SIZE.height/2));//temp cod
        this.panopoly=panopoly;
        this.players=players;
        assignedPlayer=player;
        this.boardSize =boardSize;
        squaresOnSide =(((this.boardSize -4)/4)+2);
        OFFSET =((int)(FRAME_SIZE.getHeight()*.9))/ squaresOnSide;

        mainFrame = new JFrame("Interdimensional Panopoly: "+assignedPlayer.getIdentifier());
        mainFrame.setSize(FRAME_SIZE);
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                leaveGameFunction();
            }
        });

        mainPane = new JLayeredPane();
        mainPane.setLayout(null);
        mainPane.setOpaque(true);
        mainPane.setBackground(Color.red.darker().darker());
        mainFrame.add(mainPane);

        placeBoard();
        placePlayers();
        setupImage();
        setupWindows();
        setupButtons();

        if(assignedPlayer instanceof GameBot)
            mainFrame.setVisible(false);
        else
            mainFrame.setVisible(true);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void placeBoard()
    {
        locationLabels =new LocationLabel[boardSize];
        ArrayList<Locatable> Locations=panopoly.getBoard().getLocations();
        int x=10,y=10;
        int SquaresOnSide=(((boardSize -4)/4)+2);
        int NumOnBoard=0;
        while (x< OFFSET *(SquaresOnSide-1))
        {
            locationLabels[NumOnBoard]= new LocationLabel(x,y,NumOnBoard,this,Locations.get(NumOnBoard));
            x+= OFFSET;
            NumOnBoard++;

        }
        while(y<(OFFSET *(SquaresOnSide-1)))
        {
            locationLabels[NumOnBoard]= new LocationLabel(x,y,NumOnBoard,this,Locations.get(NumOnBoard));
            y+= OFFSET;
            NumOnBoard++;

        }
        while (x>= OFFSET)
        {
            locationLabels[NumOnBoard]= new LocationLabel(x,y,NumOnBoard,this,Locations.get(NumOnBoard));
            x-= OFFSET;
            NumOnBoard++;
        }
        while (y>= OFFSET)
        {
            locationLabels[NumOnBoard]= new LocationLabel(x,y,NumOnBoard,this,Locations.get(NumOnBoard));
            y-= OFFSET;
            NumOnBoard++;
        }
    }

    private void setupImage()
    {
        ImageIcon scaleImage=new ImageIcon(GUI.class.getResource("media/Logo.png"));
        BufferedImage bi = new BufferedImage(scaleImage.getIconWidth(), scaleImage.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        scaleImage.paintIcon(null, g, 0,0);
        g.dispose();
        int scale=(OFFSET *(squaresOnSide -4));
        ImageIcon newIcon =new ImageIcon(bi.getScaledInstance(scale,scale,1));
        image=new JLabel(newIcon);
        image.setBounds(10+2* OFFSET,10+ OFFSET,scale,scale);
        image.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        mainPane.add(image);
    }

    private void setupWindows()
    {
        locationWindow.setBounds(10+3* OFFSET,10+ OFFSET, OFFSET *(squaresOnSide -6), OFFSET *(squaresOnSide -5));
        locationWindow.setBackground(Color.WHITE);
        locationWindow.setForeground(Color.BLACK);
        locationWindow.setBorder(BorderFactory.createLineBorder(Color.black,4));
        locationWindow.setVerticalAlignment(JLabel.TOP);
        locationWindow.setVisible(false);
        mainPane.add(locationWindow);

        thirdAction.setBounds(10+ OFFSET,locationWindow.getY()+locationWindow.getHeight()+ OFFSET, OFFSET *(squaresOnSide -2),OFFSET/3);
        thirdAction.setVisible(true);
        int size = 32;
        thirdAction.setFont(new Font("Chiller",Font.BOLD,32));

        while(thirdAction.getFontMetrics(new Font("Chiller",Font.BOLD,size)).stringWidth(" ")*2.5 >thirdAction.getHeight())
        {
            size--;
            thirdAction.setFont(new Font("Chiller",Font.BOLD,size));
        }
        thirdAction.setForeground(Color.white);
        thirdAction.setText("");
        mainPane.add(thirdAction);

        secondAction.setBounds(10+ OFFSET,thirdAction.getY()+thirdAction.getHeight(), OFFSET *(squaresOnSide -2),thirdAction.getHeight());
        secondAction.setVisible(true);
        secondAction.setFont(thirdAction.getFont());
        secondAction.setForeground(Color.white);
        secondAction.setText("Enjoy!");
        mainPane.add(secondAction);

        latestAction.setBounds(10+ OFFSET,secondAction.getY()+secondAction.getHeight(), OFFSET *(squaresOnSide -2),secondAction.getHeight());
        latestAction.setVisible(true);
        latestAction.setFont(thirdAction.getFont());
        latestAction.setForeground(Color.white);
        latestAction.setText("Welcome To Interdimensional Panopoly");
        mainPane.add(latestAction);

        questionWindow.setBounds(10+ OFFSET +10,10+ OFFSET +10,-20+(squaresOnSide -2)* OFFSET,80);
        questionWindow.setBackground(Color.WHITE);
        questionWindow.setForeground(Color.BLACK);
        questionWindow.setBorder(BorderFactory.createLineBorder(Color.black,4));
        questionWindow.setVerticalAlignment(JLabel.TOP);
        questionWindow.setVisible(false);
        mainPane.add(questionWindow);
        
        cardPanel.setVisible(false);
        cardPanel.setBounds(10+ OFFSET +10,locationWindow.getY()+locationWindow.getHeight(),-20+(squaresOnSide -2)* OFFSET, OFFSET);
        cardPanel.setLayout(new OverlayLayout(cardPanel));
        cardPanel.setBackground(Color.red.darker().darker());
        mainPane.add(cardPanel);

        auctionTimer=new GUIButton("00:00",(int)(10+(OFFSET *((squaresOnSide -1)/2.0)))+OFFSET,-20+(squaresOnSide -1)*OFFSET,null,this);
        auctionTimer.setVisible(false);

        doomsdayTimer.setBounds((int)(10+(OFFSET *((squaresOnSide -3)))),-20+(squaresOnSide -1)* OFFSET, OFFSET,30);
        doomsdayTimer.setFont(new Font("Digital",Font.BOLD,20));
        doomsdayTimer.setBackground(Color.BLACK);
        doomsdayTimer.setForeground(Color.WHITE);
        doomsdayTimer.setVisible(false);
        doomsdayTimer.setOpaque(true);
        doomsdayTimer.setBorder(BorderFactory.createLineBorder(Color.red.brighter(),1));
        mainPane.add(doomsdayTimer);

        tradeBox.setVisible(false);
        tradeBox.setBounds((int)(10+(OFFSET *((squaresOnSide -1)/2.0))),-20+(squaresOnSide -1)* OFFSET,30,OFFSET);
        tradeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendTradefunction();
            }
        });


    }

    private void setupButtons()
    {
        GUIButton helpButton = new GUIButton("?", 10 + OFFSET * squaresOnSide, 10, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SetupGUI.getHelp();
            }
        }, this);
        helpButton.setSize(30,30);
        helpButton.setVisible(true);

        rollButton=new GUIButton("Roll",(int)(10+(OFFSET *((squaresOnSide -1)/2.0))),-20+(squaresOnSide -1)* OFFSET,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        rollFunction();
                    }
                },this);

        endButton =new GUIButton("End",(int)(10+(OFFSET *((squaresOnSide -1)/2.0))),-20+(squaresOnSide -1)* OFFSET,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        endTurnFunction();
                    }
                },this);

        buyButton=new GUIButton("Buy",10+ OFFSET +(squaresOnSide -4)* OFFSET,2* OFFSET +10,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        buyPropertyFunction();
                    }
                },this);

        mortgageButton=new GUIButton("Mortgage",10+ OFFSET +(squaresOnSide -4)* OFFSET,2* OFFSET +10+30,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mortgagePropertyFunction();
                    }
                },this);

        redeemButton =new GUIButton("Redeem",10+ OFFSET +(squaresOnSide -4)* OFFSET,2* OFFSET +10+30,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        redeemPropertyFunction();
                    }
                },this);

        buildButton =new GUIButton("Build",10+ OFFSET +(squaresOnSide -4)* OFFSET,2* OFFSET +10+60,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        buildHouseFunction();
                    }
                },this);

        demolishButton =new GUIButton("Demolish",10+ OFFSET +(squaresOnSide -4)* OFFSET,2* OFFSET +10+90,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        demolishHouseFunction();
                    }
                },this);

        leaveButton =new GUIButton("Leave",(10+ OFFSET),-20+(squaresOnSide -1)* OFFSET,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        leaveGameFunction();
                    }
                },this);
        leaveButton.setVisible(true);

        quitButton =new GUIButton("Quit",(10+(OFFSET *((squaresOnSide -2)))),-20+(squaresOnSide -1)* OFFSET,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        quitTheGameFunction();
                    }
                },this);
        quitButton.setVisible(true);

        tradeButton=new GUIButton("Trade",demolishButton.getButton().getX(),demolishButton.getButton().getY()+demolishButton.getButton().getHeight(),
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        tradeFunction();
                    }
                },this);




        int x=0;
        for(int i=0;i<4;i++)
        {
            answers[i]=new GUIButton("Answer",(questionWindow.getX())+x,questionWindow.getY()+questionWindow.getHeight(),
                    null,this);
            answers[i].setSize(questionWindow.getWidth()/4,30);
            x+=questionWindow.getWidth()/4;
        }

        bidButton=new GUIButton("Bid",(int)(10+(OFFSET *((squaresOnSide -3)/2.0))),-20+(squaresOnSide -1)* OFFSET,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(panopoly.getCurrentBid()+(int)(((RentalProperty)panopoly.getAuctionProperty()).getPrice()*.1)<=assignedPlayer.getBalance())
                            panopoly.updateBid(panopoly.getCurrentBid()+(int)(((RentalProperty)panopoly.getAuctionProperty()).getPrice()*.1),assignedPlayer);
                        else
                            updateAction("Insufficient funds to bid.");
                    }
                },this);
        bidButton.setSize(2*OFFSET,30);
        GOOJButton=new GUIButton("", (int) (10 + (OFFSET * ((squaresOnSide - 3) / 2.0))), -20 + (squaresOnSide - 1) * OFFSET,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        useGOOJ();
                    }
                }, this);
        GOOJButton.setSize(2*OFFSET,30);
        GOOJButton.setText("GET OUT OF JAIL FREE");
    }

    private void setVisibleButtons()
    {
        if(!(getLocationLabel(panopoly.getCurrentPlayer().getPosition()).getLocation() instanceof Chance
                ||getLocationLabel(panopoly.getCurrentPlayer().getPosition()).getLocation() instanceof CommunityChest )) {
            LocationLabel label = getSelectedLocation();
            setSelectedLabel(null);
            setSelectedLabel(label);
        }
    	if(panopoly.getCurrentPlayer().isInJail()||assignedPlayer!=panopoly.getCurrentPlayer())
		{
			gui.rollCommand = false;
			gui.endCommand = false;
		}

		else if(panopoly.getCurrentPlayer().getBalance() < 0)
        {gui.rollCommand = false;
            gui.endCommand = false;}
		
		else
		{
			gui.rollCommand = panopoly.getCurrentPlayer().canRoll;
			//unowned property and player has rolled at least once
			gui.endCommand = (!gui.rollCommand && panopoly.getCurrentPlayer().getBalance() >= 0);
		}
    	hideAnswers();
    	rollButton.setVisible(rollCommand);
    	endButton.setVisible(endCommand);
    	if (panopoly.getCurrentPlayer().isInJail()&&panopoly.getCurrentPlayer()==assignedPlayer) {
                setSelectedLabel(null);
                setSelectedLabel(getLocationLabel(panopoly.getCurrentPlayer().getPosition()));
                if(noQuestion) {
                    noQuestion=false;

                    String[] question =personOfInterest.Question();
                    int rand = ThreadLocalRandom.current().nextInt(0, 3 + 1);
                    int wrongcount = 1;
                    for (int i = 0; i < 4; i++) {
                        answers[i].setVisible(true);
                        if (i == rand) {
                            answers[i].setText(question[1]);
                            answers[i].setMouseEvent(correct);
                        } else {
                            answers[i].setMouseEvent(incorrect);
                            answers[i].setText(question[1+wrongcount]);
                            wrongcount++;
                        }
                    }
                    if(assignedPlayer.hasGOOJFree())
                        GOOJButton.setVisible(true);
                    questionWindow.setText("<html><center>" + question[0] + "</center></html>");

                }
                else
                {
                    for(GUIButton button:answers)
                        button.setVisible(true);
                }
        }

        //leaveButton.setVisible(!rollCommand&&!endCommand);
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
        if(!auctionTimer.isVisible())
            setVisibleButtons();
    }

    void updateAction(String action)
    {
        String[] lines;
        if(action.contains("\n"))
        {
            lines = action.split("\n");

            if(lines.length == 2)
            {
                thirdAction.setText(latestAction.getText());
                secondAction.setText(lines[0]);
                latestAction.setText(lines[1]);
            }
            else
            {
                thirdAction.setText(lines[0]);
                secondAction.setText(lines[1]);
                latestAction.setText(lines[2]);
            }
        }
        else{
            thirdAction.setText(secondAction.getText());
            secondAction.setText(latestAction.getText());
            latestAction.setText(action);
        }
        updateGUI();
    }

    void displayCard(String msg)
    {
        setSelectedLabel(null);
        setSelectedLabel(getLocationLabel(panopoly.getCurrentPlayer().getPosition()));
        image.setVisible(false);
        JLabel card=new JLabel("<html><center>"+msg+"<br>Click to Close.</center></html>",SwingConstants.CENTER);
        card.setBounds(10+ OFFSET +10,locationWindow.getY()+locationWindow.getHeight(),-20+(squaresOnSide -2)* OFFSET, OFFSET);
        card.setVisible(true);
        card.setOpaque(true);
        card.setBackground(Color.white);
        card.setForeground(Color.BLACK);
        card.setBorder(BorderFactory.createBevelBorder(1,Color.red,Color.red.darker()));
        cardPanel.add(card);
        cardPanel.setVisible(true);
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                card.setVisible(false);
                cardPanel.remove(card);
                if(cardPanel.getComponentCount() == 0)
                	cardPanel.setVisible(false);
                setSelectedLabel(null);
                setSelectedLabel(getLocationLabel(assignedPlayer.getPosition()));
            }
        });

    }

    void resetCommands()
    {
    	rollCommand = false;
    	endCommand = false;
    }
    
    private void placePlayers()
    {
        PlayerLabels=new PlayerLabel[players.size()];
        int i=0;
        for(Player player:players)
        {
            PlayerLabels[i]=new PlayerLabel(player,i,new ImageIcon(images[player.getImageIndex()]),this);
            i++;
        }
    }

    private void deletePlayers()
    {
        for(PlayerLabel label:PlayerLabels)
        {
            label.removePlayer();
        }
    }

    void leaveGame(Player player)
    {
        players.remove(player);
        deletePlayers();
        placePlayers();
    }

    private void hideAnswers()
    {
        for(GUIButton answer:answers)
        {
            answer.setVisible(false);
        }
    }

    void startAuction()
    {
        auctionTimer.setVisible(true);
        setSelectedLabel(null);
        setSelectedLabel(getLocationLabel(panopoly.getCurrentPlayer().getPosition()));
        bidButton.setText("Bid: "+GUI.symbol+(int)(((RentalProperty)panopoly.getAuctionProperty()).getPrice()*.1));
        if((int)(((RentalProperty)panopoly.getAuctionProperty()).getPrice()*.1)<=assignedPlayer.getBalance())
            bidButton.setVisible(true);
    }

    void updateAuctionClock(String time)
    {
        endButton.setVisible(false);
        rollButton.setVisible(false);
        auctionTimer.setText("<html><center>"+time+"</center></html>");
        if((panopoly.getCurrentBid()+(int)(((RentalProperty)(panopoly.getAuctionProperty())).getPrice()*.1))>assignedPlayer.getBalance())
            bidButton.setVisible(false);
        else
            bidButton.setText("Bid: "+GUI.symbol+(panopoly.getCurrentBid()+(int)(((RentalProperty)(panopoly.getAuctionProperty())).getPrice()*.1)));
    }

    void updateDoomsdayClock(String time)
    {
    	doomsdayTimer.setVisible(true);
        doomsdayTimer.setText("<html><center>"+time+"</center></html>");
        System.out.println(time);

    }

    void endAuction()
    {
        bidButton.setVisible(false);
        bidButton.setText("No Bid");
        auctionTimer.setVisible(false);
        setSelectedLabel(getLocationLabel(panopoly.getAuctionProperty()));
    }

    void endGame()
    {
        getMainPane().remove(buyButton.getButton());
        getMainPane().remove(endButton.getButton());
        getMainPane().remove(demolishButton.getButton());
        getMainPane().remove(mortgageButton.getButton());
        getMainPane().remove(buildButton.getButton());
        getMainPane().remove(redeemButton.getButton());
        getMainPane().remove(rollButton.getButton());
        getMainPane().remove(quitButton.getButton());
        getMainPane().remove(leaveButton.getButton());
        getMainPane().remove(auctionTimer.getButton());
        getMainPane().remove(bidButton.getButton());
        for(int i=0;i<4;i++)
            getMainPane().remove(answers[i].getButton());
        getMainPane().remove(questionWindow);
        getMainPane().validate();
        getMainPane().repaint();
        GUIButton again=new GUIButton("New Game?",10+ OFFSET *(squaresOnSide -2)/2,-20+(squaresOnSide -1)* OFFSET,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mainFrame.dispose();
                        Main.createPanopoly();
                    }
                },this);
        again.setVisible(true);
        GUIButton exit=new GUIButton("Done?",10+ OFFSET +(OFFSET *(squaresOnSide -2)/2),-20+(squaresOnSide -1)* OFFSET,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mainFrame.dispose();
                        System.exit(0);
                    }
                },this);
        exit.setVisible(true);
      }

    public void rollFunction()
    {
        if (panopoly.getBoard().getLocation(panopoly.getCurrentPlayer().getPosition()) instanceof RentalProperty
                && !((RentalProperty) panopoly.getBoard().getLocation(panopoly.getCurrentPlayer().getPosition())).isOwned())
        {
            rollButton.setVisible(false);
            tradeButton.setVisible(false);
            buyButton.setVisible(false);
            panopoly.callAuction(0);
        }
        else {
            panopoly.roll();
            if (getSelectedLocation() != getLocationLabel(panopoly.getCurrentPlayer().getPosition())
                    && !(getLocationLabel(panopoly.getCurrentPlayer().getPosition()).getLocation() instanceof CommunityChest
                    || getLocationLabel(panopoly.getCurrentPlayer().getPosition()).getLocation() instanceof Chance)) {
                setSelectedLabel(getLocationLabel(panopoly.getCurrentPlayer().getPosition()));
            }
            if(panopoly.getCurrentPlayer() instanceof GameBot)
                ((GameBot) panopoly.getCurrentPlayer()).makeGameDecision();
        }
    }

    public void endTurnFunction()
    {
        if ((panopoly.getBoard().getLocation(panopoly.getCurrentPlayer().getPosition()) instanceof RentalProperty
                && !((RentalProperty) panopoly.getBoard().getLocation(panopoly.getCurrentPlayer().getPosition())).isOwned())) {
            endButton.setVisible(false);
            buyButton.setVisible(false);
            tradeButton.setVisible(false);
            panopoly.callAuction(1);
        }
        else {
            Player player=panopoly.getNextPlayer();
            panopoly.startPlayerTurn(player);
            if(panopoly.getCurrentPlayer()==assignedPlayer&&assignedPlayer instanceof GameBot)
                ((GameBot)assignedPlayer).makeGameDecision();
        }
    }

    @Override
    public void buyPropertyFunction()
    {
        Rentable buyProperty= (Rentable)panopoly.getBoard().getLocation(panopoly.getCurrentPlayer().getPosition());
        gui.updateAction(panopoly.buyProperty(buyProperty));
        gui.updateGUI();
        if(assignedPlayer instanceof GameBot)
            ((GameBot)assignedPlayer).makeGameDecision();
    }

    @Override
    public void mortgagePropertyFunction()
    {
        RentalProperty mortgageProperty = (RentalProperty)getSelectedLocation().getLocation();
        gui.updateAction(panopoly.mortgage(mortgageProperty));
        if(panopoly.getCurrentPlayer() instanceof GameBot)
            ((GameBot) panopoly.getCurrentPlayer()).makeGameDecision();
    }

    @Override
    public void redeemPropertyFunction()
    {
        RentalProperty redeem=(RentalProperty)getSelectedLocation().getLocation();
        gui.updateAction(panopoly.redeem(redeem));
        if(panopoly.getCurrentPlayer() instanceof GameBot)
            ((GameBot) panopoly.getCurrentPlayer()).makeGameDecision();
    }

    @Override
    public void buildHouseFunction()
    {
        InvestmentProperty builder=(InvestmentProperty) getSelectedLocation().getLocation();
        gui.updateAction(panopoly.buildUnit(builder));
        if(panopoly.getCurrentPlayer() instanceof GameBot)
            ((GameBot) panopoly.getCurrentPlayer()).makeGameDecision();
    }

    @Override
    public void demolishHouseFunction()
    {
        InvestmentProperty breaker=(InvestmentProperty) getSelectedLocation().getLocation();
        gui.updateAction(panopoly.demolishUnit(breaker));
        if(panopoly.getCurrentPlayer() instanceof GameBot)
            ((GameBot) panopoly.getCurrentPlayer()).makeGameDecision();
    }

    @Override
    public void quitTheGameFunction()
    {
        panopoly.endGame();
    }

    @Override
    public void leaveGameFunction()
    {
        panopoly.leaveGame(assignedPlayer);
        mainFrame.dispose();
        if(panopoly.getCurrentPlayer() instanceof GameBot)
            ((GameBot) panopoly.getCurrentPlayer()).makeGameDecision();
    }

    @Override
    public void answerCorrectlyFunction()
    {
        updateAction("Correct answer.");
        panopoly.getCurrentPlayer().releaseFromJail();
        noQuestion=true;
        gui.hideAnswers();
        GOOJButton.setVisible(false);
        setSelectedLabel(null);
        if(panopoly.getCurrentPlayer() instanceof GameBot)
            ((GameBot) panopoly.getCurrentPlayer()).makeGameDecision();
    }

    @Override
    public void answerIncorrectlyFunction()
    {
        updateAction("Wrong answer.");
        panopoly.startPlayerTurn(panopoly.getNextPlayer());
        noQuestion=true;
        GOOJButton.setVisible(false);
        gui.hideAnswers();
        setSelectedLabel(null);
        if(panopoly.getCurrentPlayer() instanceof GameBot)
            ((GameBot) panopoly.getCurrentPlayer()).makeGameDecision();
    }

    @Override
    public void getHelpFunction()
    {

    }

    public void tradeFunction()
    {
        tradeButton.setText("Cancel");
        tradeButton.setMouseEvent(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    cancelTrade();
                }
        });
        if(  ((RentalProperty)(getSelectedLocation().getLocation())).getOwner()==assignedPlayer  )
        {
            updateAction("Choose the player you would like to trade with");
            updateAction("Enter the amount you want from them");
            tradeBox.setVisible(true);

        }
        else
        {
            updateAction("Choose the amount you will pay for this");
        }
    }

    public void sendTradefunction()
    {

    }

    public void cancelTrade()
    {
        tradeButton.setText("Trade");
        tradeButton.setMouseEvent(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tradeFunction();
            }
        });
    }

    public void completeTrade()
    {

    }

    public void useGOOJ()
    {
        updateAction(assignedPlayer.getIdentifier()+" used get out of Jail free.");
        assignedPlayer.useGOOJFree();
        GOOJButton.setVisible(false);
        if(panopoly.getCurrentPlayer() instanceof GameBot)
            ((GameBot) panopoly.getCurrentPlayer()).makeGameDecision();
    }

    JLayeredPane getMainPane()
    {
        return mainPane;
    }

    Dimension getFRAME_SIZE() {
        return FRAME_SIZE;
    }

    int getOFFSET()
    {
        return OFFSET;
    }

    private LocationLabel[] getLocationLabels() {
        return locationLabels;
    }

    ArrayList<Player> getPlayers() {
        return players;
    }

    private LocationLabel getSelectedLocation()
    {
        return selectedLabel;
    }

    LocationLabel getLocationLabel(Locatable location) throws ArrayIndexOutOfBoundsException
    {
        for(LocationLabel local: locationLabels)
        {
            if(local.getLocation()==location)
                return local;
        }
        return null;
    }

    LocationLabel getLocationLabel(int index)throws ArrayIndexOutOfBoundsException
    {
        return locationLabels[index];
    }

    void setPanopoly(Panopoly panopoly) {
        this.panopoly = panopoly;
    }

    void setSelectedLabel(LocationLabel location)
    {
        for(LocationLabel label:gui.getLocationLabels())
        {
            label.resetBorder();
        }
        if(this.selectedLabel !=null)
        {
            this.selectedLabel.getLabel().setBorder(BorderFactory.createLineBorder(Color.BLACK,2));

        }

        if(location==null||this.selectedLabel ==location)
        {
            this.selectedLabel =null;
            image.setVisible(true);
            locationWindow.setText(" ");
            locationWindow.setOpaque(false);
            locationWindow.setVisible(false);
            questionWindow.setVisible(false);
            questionWindow.setOpaque(false);
            buyButton.setVisible(false);
            mortgageButton.setVisible(false);
            redeemButton.setVisible(false);
            buildButton.setVisible(false);
            demolishButton.setVisible(false);
        }
        else if(panopoly.getCurrentPlayer().isInJail()&&location==getLocationLabel(panopoly.getCurrentPlayer().getPosition())&panopoly.getCurrentPlayer()==assignedPlayer)
        {
            this.selectedLabel =location;
            image.setVisible(false);
            questionWindow.setVisible(true);
            questionWindow.setOpaque(true);
            locationWindow.setText(" ");
            locationWindow.setOpaque(false);
            locationWindow.setVisible(false);
            buyButton.setVisible(false);
            mortgageButton.setVisible(false);
            redeemButton.setVisible(false);
            buildButton.setVisible(false);
            demolishButton.setVisible(false);
        }
        else
        {
            questionWindow.setVisible(false);
            questionWindow.setOpaque(false);
            this.selectedLabel =location;
            this.selectedLabel.getLabel().setBorder(BorderFactory.createLineBorder(Color.green.darker(),2));
            locationWindow.setOpaque(true);
            locationWindow.setVisible(true);
            image.setVisible(false);
            if (location.getLocation() instanceof RentalProperty)
            {
                RentalProperty locationCheck = (RentalProperty) location.getLocation();
                tradeButton.setVisible(locationCheck.isOwned());
                if (locationCheck.getOwner() == panopoly.getCurrentPlayer()&&panopoly.getCurrentPlayer()==assignedPlayer)
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
                    if(locationCheck.getOwner()==null &&panopoly.getCurrentPlayer().getPosition()==location.getIndex()&&panopoly.getCurrentPlayer()==assignedPlayer)
                    {
                        if(panopoly.getCurrentPlayer().getBalance()<((RentalProperty) location.getLocation()).getPrice()||auctionTimer.isVisible())
                        {
                            buyButton.setVisible(false);
                        }
                        else
                            buyButton.setVisible(true);
                    }
                    else
                        buyButton.setVisible(false);
                    mortgageButton.setVisible(false);
                    redeemButton.setVisible(false);
                }
                if(location.getLocation() instanceof InvestmentProperty)
                {
                    InvestmentProperty investment=(InvestmentProperty)location.getLocation();
                    if(locationCheck.getOwner()==panopoly.getCurrentPlayer()&&panopoly.getCurrentPlayer()==assignedPlayer)
                    {
                        Boolean buildable=((Player)investment.getOwner()).ownsGroup(locationCheck.getGroup());
                        for(Groupable groupLocation:locationCheck.getGroup().getMembers())
                        {
                            if(((InvestmentProperty)groupLocation).isMortgaged()||((InvestmentProperty) groupLocation).getNumBuildings()<investment.getNumBuildings())
                            {
                                buildable=false;
                                break;
                            }
                        }
                        demolishButton.setVisible((investment.hasBuildings()));
                        buildButton.setVisible(buildable&&investment.hotels==0&&investment.getOwner().getBalance()>=investment.buildPrice);
                    }
                    else
                    {
                        demolishButton.setVisible(false);
                        buildButton.setVisible(false);
                    }
                }
                else
                {
                    demolishButton.setVisible(false);
                    buildButton.setVisible(false);
                }

            }

            else
            {
                demolishButton.setVisible(false);
                buildButton.setVisible(false);
                mortgageButton.setVisible(false);
                redeemButton.setVisible(false);
            }
            locationWindow.setText(location.getHTML());
        }



    }

}