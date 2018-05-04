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

    public static String symbol="Q";
    private Player assignedPlayer;
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
    private boolean noQuestion=true;
    private CardDeck deck=new CardDeck();
	
    boolean rollCommand,endCommand;
	public GUIButton helpButton,buyButton, rollButton, endButton, mortgageButton,
            leaveButton, redeemButton,buildButton, demolishButton,quitButton;

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
    
    private Panopoly panopoly;
    private PersonOfInterest personOfInterest=new PersonOfInterest();
    static BufferedImage[] images = new BufferedImage[6];
    private JLabel locationWindow=new JLabel(" ",SwingConstants.CENTER);
    private JLabel questionWindow=new JLabel(" ",SwingConstants.CENTER);
    private JLabel latestAction=new JLabel("",SwingConstants.CENTER);
    private JLabel secondAction=new JLabel("",SwingConstants.CENTER);
    private JLabel thirdAction=new JLabel("",SwingConstants.CENTER);
    private GUI gui=this;

    GUI(int BoardSize,Panopoly panopoly,ArrayList<Player> players,Player player)
    {
        //FRAME_SIZE=new Dimension((int)(50+(FRAME_SIZE.width/2)),(int)(50+FRAME_SIZE.height/2));//temp code
        this.setPanopoly(panopoly);
        this.setPlayers(players);
        assignedPlayer=player;
        BOARD_SIZE=BoardSize;
        mainFrame = new JFrame("Interdimensional Panopoly: "+assignedPlayer.getIdentifier());
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
        ImageIcon scaleImage=new ImageIcon(GUI.class.getResource("media/Logo.png"));
        BufferedImage bi = new BufferedImage(scaleImage.getIconWidth(), scaleImage.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        scaleImage.paintIcon(null, g, 0,0);
        g.dispose();
        int scale=(int)(Offset*(SquaresOnSide-4));
        ImageIcon newIcon =new ImageIcon(bi.getScaledInstance(scale,scale,1));
        image=new JLabel(newIcon);
        image.setBounds(10+2*Offset,10+Offset,scale,scale);
        image.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        MainPane.add(image);


        locationWindow.setBounds(10+3*Offset,10+Offset,Offset*(SquaresOnSide-6),Offset*(SquaresOnSide-5));
        locationWindow.setBackground(Color.WHITE);
        locationWindow.setForeground(Color.BLACK);
        locationWindow.setBorder(BorderFactory.createLineBorder(Color.black,4));
        locationWindow.setVerticalAlignment(JLabel.TOP);
        locationWindow.setVisible(false);
        MainPane.add(locationWindow);

        thirdAction.setBounds(10+Offset,locationWindow.getY()+locationWindow.getHeight()+Offset,Offset*(SquaresOnSide-2),20);
        thirdAction.setVisible(true);
        thirdAction.setFont(new Font("times new roman",Font.BOLD,16));
        thirdAction.setForeground(Color.white);
        thirdAction.setText("");
        MainPane.add(thirdAction);

        secondAction.setBounds(10+Offset,thirdAction.getY()+20,Offset*(SquaresOnSide-2),20);
        secondAction.setVisible(true);
        secondAction.setFont(new Font("times new roman",Font.BOLD,16));
        secondAction.setForeground(Color.white);
        secondAction.setText("Enjoy!");
        MainPane.add(secondAction);

        latestAction.setBounds(10+Offset,secondAction.getY()+20,Offset*(SquaresOnSide-2),20);
        latestAction.setVisible(true);
        latestAction.setFont(new Font("Times New Roman",Font.BOLD,16));
        latestAction.setForeground(Color.white);
        latestAction.setText("Welcome To Interdimensional Panopoly");
        MainPane.add(latestAction);



        questionWindow.setBounds(10+Offset+10,10+Offset+10,-20+(SquaresOnSide-2)*Offset,80);
        questionWindow.setBackground(Color.WHITE);
        questionWindow.setForeground(Color.BLACK);
        questionWindow.setBorder(BorderFactory.createLineBorder(Color.black,4));
        questionWindow.setVerticalAlignment(JLabel.TOP);
        questionWindow.setVisible(false);
        MainPane.add(questionWindow);
        if(assignedPlayer instanceof GameBot)
            mainFrame.setVisible(false);
        else
            mainFrame.setVisible(true);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setupbuttons();

    }

    void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    void setPanopoly(Panopoly panopoly) {
        this.panopoly = panopoly;
    }

    public void rollFunction(){
        panopoly.roll();
        if(getSelectedLocation()!=getLocationLabel(panopoly.getCurrentPlayer().getPosition())
                &&!(getLocationLabel(panopoly.getCurrentPlayer().getPosition()).getLocation() instanceof CommunityChest
                ||getLocationLabel(panopoly.getCurrentPlayer().getPosition()).getLocation() instanceof Chance)) {
            setSelectedLabel(getLocationLabel(panopoly.getCurrentPlayer().getPosition()));
        }
    }
    public void endTurnFunction(){
        panopoly.startPlayerTurn(panopoly.getNextPlayer());
    }

    @Override
    public void getHelpFunction() {

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
    }

    @Override
    public void answerIncorrectlyFunction() {
        updateAction("Wrong answer.");
        panopoly.startPlayerTurn(panopoly.getNextPlayer());
        noQuestion=true;
        gui.hideAnswers();
    }

    private void setupbuttons()
    {
        helpButton=new GUIButton("?", 10+Offset*SquaresOnSide, 10, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SetupGUI.getHelp();
            }
        },this);
        helpButton.setSize(30,30);
        helpButton.setVisible(true);
        rollButton=new GUIButton("Roll",(int)(10+(Offset*((SquaresOnSide-1)/2.0))),-20+(SquaresOnSide-1)*Offset,
        new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rollFunction();
            }
        },this);
        endButton =new GUIButton("End",(int)(10+(Offset*((SquaresOnSide-1)/2.0))),-20+(SquaresOnSide-1)*Offset,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        endTurnFunction();
                    }
                },this);

        buyButton=new GUIButton("Buy",10+Offset+(SquaresOnSide-4)*Offset,2*Offset+10,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        buyPropertyFunction();
                    }
                },this);

        mortgageButton=new GUIButton("Mortgage",10+Offset+(SquaresOnSide-4)*Offset,2*Offset+10+30,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mortgagePropertyFunction();
                    }
                },this);
        redeemButton =new GUIButton("Redeem",10+Offset+(SquaresOnSide-4)*Offset,2*Offset+10+30,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        redeemPropertyFunction();
                    }
                },this);

        buildButton =new GUIButton("Build",10+Offset+(SquaresOnSide-4)*Offset,2*Offset+10+60,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        buildHouseFunction();
                    }
                },this);

        demolishButton =new GUIButton("Demolish",10+Offset+(SquaresOnSide-4)*Offset,2*Offset+10+90,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        demolishHouseFunction();
                    }
                },this);
        leaveButton =new GUIButton("Leave",(int)(10+Offset),-20+(SquaresOnSide-1)*Offset,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        leaveGameFunction();
                    }
                },this);
        leaveButton.setVisible(true);
        quitButton =new GUIButton("Quit",(int)(10+(Offset*((SquaresOnSide-2)))),-20+(SquaresOnSide-1)*Offset,
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
            answers[i]=new GUIButton("Answer",(int)(questionWindow.getX())+x,questionWindow.getY()+questionWindow.getHeight(),
                   null,this);
            answers[i].setSize(questionWindow.getWidth()/4,30);
            x+=questionWindow.getWidth()/4;
        }

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

    void displayCard(String msg)
    {
        setSelectedLabel(null);
        image.setVisible(false);
        JLabel card=new JLabel("<html><center>"+msg+"<br>Click to Close.</center></html>");
        card.setBounds(10+Offset+10,locationWindow.getY()+locationWindow.getHeight(),-20+(SquaresOnSide-2)*Offset,Offset);
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
    private void hideAnswers() {
        for(GUIButton answer:answers)
        {
            answer.setVisible(false);
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
            this.SelectedLabel=location;
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
            this.SelectedLabel=location;
            this.SelectedLabel.getLabel().setBorder(BorderFactory.createLineBorder(Color.green.darker(),2));
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
        String[] lines=null;
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
        GUIButton again=new GUIButton("New Game?",10+Offset*(SquaresOnSide-2)/2,(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mainFrame.dispose();
                        Main.createPanopoly();
                    }
                },this);
        again.setVisible(true);
        GUIButton exit=new GUIButton("Done?",10+Offset+(Offset*(SquaresOnSide-2)/2),(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mainFrame.dispose();
                    }
                },this);
        exit.setVisible(true);
      }
    
}