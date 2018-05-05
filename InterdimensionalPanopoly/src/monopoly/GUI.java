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
    private JLabel auctionTimer=new JLabel("",SwingConstants.CENTER);
    private JLabel doomsdayTimer=new JLabel("",SwingConstants.CENTER);
    private JTextField bidBox=new JTextField();

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
    GUIButton demolishButton;
    GUIButton quitButton;
    private GUIButton[] answers =new GUIButton[4];
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
        //FRAME_SIZE=new Dimension((int)(50+(FRAME_SIZE.width/2)),(int)(50+FRAME_SIZE.height/2));//temp cod
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

        thirdAction.setBounds(10+ OFFSET,locationWindow.getY()+locationWindow.getHeight()+ OFFSET, OFFSET *(squaresOnSide -2),20);
        thirdAction.setVisible(true);
        thirdAction.setFont(new Font("times new roman",Font.BOLD,16));
        thirdAction.setForeground(Color.white);
        thirdAction.setText("");
        mainPane.add(thirdAction);

        secondAction.setBounds(10+ OFFSET,thirdAction.getY()+20, OFFSET *(squaresOnSide -2),20);
        secondAction.setVisible(true);
        secondAction.setFont(new Font("times new roman",Font.BOLD,16));
        secondAction.setForeground(Color.white);
        secondAction.setText("Enjoy!");
        mainPane.add(secondAction);

        latestAction.setBounds(10+ OFFSET,secondAction.getY()+20, OFFSET *(squaresOnSide -2),20);
        latestAction.setVisible(true);
        latestAction.setFont(new Font("Times New Roman",Font.BOLD,16));
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

        bidBox.setBounds((int)(10+(OFFSET *((squaresOnSide -1)/2.0))),-20+(squaresOnSide -1)* OFFSET, OFFSET,30);
        bidBox.setForeground(Color.WHITE);
        bidBox.setBackground(Color.BLACK);
        bidBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int bid;
                try {// if is number
                    bid=Integer.parseInt(bidBox.getText());
                } catch (NumberFormatException b) {
                    updateAction(bidBox.getText()+" is not a valid bid");
                    bidBox.setText("");
                    return;
                }
                if(bid<=panopoly.getCurrentBid())
                {updateAction(bidBox.getText()+" is not a valid bid");
                    bidBox.setText(""); }
                else
                    panopoly.updateBid(bid,assignedPlayer);
            }
        });
        bidBox.setBorder(BorderFactory.createLineBorder(Color.red.brighter(),1));
        bidBox.setVisible(false);
        mainPane.add(bidBox);

        auctionTimer.setBounds((int)(10+(OFFSET *((squaresOnSide -1)/2.0)))+OFFSET,-20+(squaresOnSide -1)* OFFSET, OFFSET,30);
        auctionTimer.setFont(new Font("Digital",Font.BOLD,20));
        auctionTimer.setBackground(Color.BLACK);
        auctionTimer.setForeground(Color.WHITE);
        auctionTimer.setVisible(false);
        auctionTimer.setBorder(BorderFactory.createLineBorder(Color.red.brighter(),1));

        doomsdayTimer.setBounds((int)(10+(OFFSET *((squaresOnSide -3))))+OFFSET,-20+(squaresOnSide -1)* OFFSET, OFFSET,30);
        doomsdayTimer.setFont(new Font("Digital",Font.BOLD,20));
        doomsdayTimer.setBackground(Color.BLACK);
        doomsdayTimer.setForeground(Color.WHITE);
        doomsdayTimer.setVisible(false);
        doomsdayTimer.setBorder(BorderFactory.createLineBorder(Color.red.brighter(),1));
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
        int x=0;
        for(int i=0;i<4;i++)
        {
            answers[i]=new GUIButton("Answer",(questionWindow.getX())+x,questionWindow.getY()+questionWindow.getHeight(),
                    null,this);
            answers[i].setSize(questionWindow.getWidth()/4,30);
            x+=questionWindow.getWidth()/4;
        }

    }

    public void rollFunction(){
        panopoly.roll();
        if(getSelectedLocation()!=getLocationLabel(panopoly.getCurrentPlayer().getPosition())
                &&!(getLocationLabel(panopoly.getCurrentPlayer().getPosition()).getLocation() instanceof CommunityChest
                ||getLocationLabel(panopoly.getCurrentPlayer().getPosition()).getLocation() instanceof Chance)) {
            setSelectedLabel(getLocationLabel(panopoly.getCurrentPlayer().getPosition()));
        }
    }

    public void endTurnFunction() {
        if (buyButton.isVisible()) {
            endButton.setVisible(false);
            buyButton.setVisible(false);
            panopoly.callAuction();
        }
        panopoly.startPlayerTurn(panopoly.getNextPlayer());
    }

    @Override
    public void getHelpFunction() {

    }
    private void setVisibleButtons()
    {
    	if(panopoly.getCurrentPlayer().isInJail()||assignedPlayer!=panopoly.getCurrentPlayer())
		{
			gui.rollCommand = false;
			gui.endCommand = false;
		}

		else if(panopoly.getCurrentPlayer().getBalance() < 0)
		    gui.rollCommand = false;
		
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
                            answers[i].setText("|"+question[1+wrongcount]);
                            wrongcount++;
                        }
                    }
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
        image.setVisible(false);
        JLabel card=new JLabel("<html><center>"+msg+"<br>Click to Close.</center></html>");
        card.setBounds(10+ OFFSET +10,locationWindow.getY()+locationWindow.getHeight(),-20+(squaresOnSide -2)* OFFSET, OFFSET);
        card.setVisible(true);
        card.setOpaque(true);
        getMainPane().add(card);
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                card.setVisible(false);
                getMainPane().remove(card);
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

    private void hideAnswers() {
        for(GUIButton answer:answers)
        {
            answer.setVisible(false);
        }
    }

    void startAuction()
    {
        setSelectedLabel(null);
        setSelectedLabel(getLocationLabel(panopoly.getCurrentPlayer().getPosition()));
        bidBox.setVisible(true);
        auctionTimer.setVisible(true);
    }

    void updateAuctionClock(String time)
    {
        auctionTimer.setText("<html><center>"+time+"</center></html>");
    }
    void updateDoomsdayClock(String time)
    {

    }

    void endAuction()
    {
        bidBox.setVisible(false);
        auctionTimer.setVisible(false);
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
        getMainPane().validate();
        getMainPane().repaint();
        GUIButton again=new GUIButton("New Game?",10+ OFFSET *(squaresOnSide -2)/2,(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mainFrame.dispose();
                        Main.createPanopoly();
                    }
                },this);
        again.setVisible(true);
        GUIButton exit=new GUIButton("Done?",10+ OFFSET +(OFFSET *(squaresOnSide -2)/2),(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mainFrame.dispose();
                        System.exit(0);
                    }
                },this);
        exit.setVisible(true);
      }





    @Override
    public void buyPropertyFunction() {
        Rentable buyProperty= (Rentable)panopoly.getBoard().getLocation(panopoly.getCurrentPlayer().getPosition());
        gui.updateAction(panopoly.buyProperty(buyProperty));
        gui.updateGUI();
        if(assignedPlayer instanceof GameBot)
            ((GameBot)assignedPlayer).makeGameDecision();
    }

    @Override
    public void mortgagePropertyFunction() {
        RentalProperty mortgageProperty = (RentalProperty)getSelectedLocation().getLocation();
        gui.updateAction(panopoly.mortgage(mortgageProperty));
    }

    @Override
    public void redeemPropertyFunction() {
        RentalProperty redeem=(RentalProperty)getSelectedLocation().getLocation();
        gui.updateAction(panopoly.redeem(redeem));
    }

    @Override
    public void buildHouseFunction() {
        InvestmentProperty builder=(InvestmentProperty) getSelectedLocation().getLocation();
        gui.updateAction(panopoly.buildUnit(builder));
    }

    @Override
    public void demolishHouseFunction() {
        InvestmentProperty breaker=(InvestmentProperty) getSelectedLocation().getLocation();
        gui.updateAction(panopoly.demolishUnit(breaker));
    }

    @Override
    public void quitTheGameFunction() {
        panopoly.endGame();
    }

    @Override
    public void leaveGameFunction() {
        panopoly.leaveGame(assignedPlayer);
        mainFrame.dispose();
    }

    @Override
    public void answerCorrectlyFunction() {
        updateAction("Correct answer.");
        panopoly.getCurrentPlayer().releaseFromJail();
        noQuestion=true;
        gui.hideAnswers();
        setSelectedLabel(null);
    }

    @Override
    public void answerIncorrectlyFunction() {
        updateAction("Wrong answer.");
        panopoly.startPlayerTurn(panopoly.getNextPlayer());
        noQuestion=true;
        gui.hideAnswers();
        setSelectedLabel(null);
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
        else if(panopoly.getCurrentPlayer().isInJail()&&location==getLocationLabel(panopoly.getCurrentPlayer().getPosition()))
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
                buyButton.setEnabled(true);
                RentalProperty locationCheck = (RentalProperty) location.getLocation();
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
                        if(panopoly.getCurrentPlayer().getBalance()<((RentalProperty) location.getLocation()).getPrice())
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
                        Boolean buildable=true;//((Player)investment.getOwner()).ownsGroup(locationCheck.getGroup());
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