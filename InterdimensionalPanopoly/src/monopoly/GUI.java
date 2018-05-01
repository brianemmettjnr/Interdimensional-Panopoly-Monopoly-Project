package monopoly;
import interfaces.Groupable;
import interfaces.Locatable;
import interfaces.Rentable;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
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
	private GUIButton helpButton,buyButton, rollButton, endButton, mortgageButton,
            leaveButton, redeemButton,buildButton, demolishButton,quitButton;

	private GUIButton[] answers =new GUIButton[4];
	private MouseAdapter correct=new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            panopoly.getCurrentPlayer().releaseFromJail();
            gui.hideAnswers();
            updateAction("Correct answer.");
        }
    };


    private MouseAdapter incorrect=new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            panopoly.startPlayerTurn(panopoly.getNextPlayer());
            gui.hideAnswers();
            updateAction("Wrong answer.");
        }
    };
    
    private Panopoly panopoly;
    static BufferedImage[] images = new BufferedImage[6];
    private JLabel locationWindow=new JLabel(" ",SwingConstants.CENTER);
    private JLabel questionWindow=new JLabel(" ",SwingConstants.CENTER);
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
        latestAction.setFont(new Font("Times New Roman",Font.BOLD,20));
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

        questionWindow.setBounds((int)(10+(Offset*((SquaresOnSide)/2.0)))-200,(((int)(FRAME_SIZE.getHeight()*.9))/2)-140,400,80);
        questionWindow.setBackground(Color.WHITE);
        questionWindow.setForeground(Color.BLACK);
        questionWindow.setBorder(BorderFactory.createLineBorder(Color.black,4));
        questionWindow.setVerticalAlignment(JLabel.TOP);
        questionWindow.setOpaque(false);
        questionWindow.setVisible(false);
        MainPane.add(questionWindow);
        mainFrame.setVisible(true);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        helpButton=new GUIButton("?", (int)FRAME_SIZE.getWidth() - 40, 10, new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SetupGUI.getHelp();
            }
        },this);
        helpButton.setSize(30,30);
        helpButton.setVisible(true);
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
        rollButton=new GUIButton("Roll",(int)(10+(Offset*((SquaresOnSide-1)/2.0))),-20+(SquaresOnSide-1)*Offset,
        new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gui.updateAction(panopoly.roll());
                if(getSelectedLocation()!=getLocationLabel(panopoly.getCurrentPlayer().getPosition())) {
                    setSelectedLabel(getLocationLabel(panopoly.getCurrentPlayer().getPosition()));
                }
            }
        },this);

        buyButton=new GUIButton("Buy",(int)(10+(Offset*((SquaresOnSide-1)/2.0))),Offset+10,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Rentable buyProperty= (Rentable)panopoly.getBoard().getLocation(panopoly.getCurrentPlayer().getPosition());
                        gui.updateAction(panopoly.buyProperty(buyProperty));
                        gui.updateGUI();
                    }
                },this);

        endButton =new GUIButton("End",(int)(10+(Offset*((SquaresOnSide-1)/2.0))),-20+(SquaresOnSide-1)*Offset,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gui.updateAction(panopoly.startPlayerTurn(panopoly.getNextPlayer()));
                    }
                },this);

        mortgageButton=new GUIButton("Mortgage",10+Offset,10+Offset,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        RentalProperty mortgageProperty = (RentalProperty)getSelectedLocation().getLocation();
                        gui.updateAction(panopoly.mortgage(mortgageProperty));
                    }
                },this);
        redeemButton =new GUIButton("Redeem",10+Offset,10+Offset,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        RentalProperty redeem=(RentalProperty)getSelectedLocation().getLocation();
                        gui.updateAction(panopoly.redeem(redeem));
                    }
                },this);

        buildButton =new GUIButton("Build",(int)(10+(Offset*((SquaresOnSide-2)))),10+Offset,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        InvestmentProperty builder=(InvestmentProperty) getSelectedLocation().getLocation();
                        gui.updateAction(panopoly.buildUnit(builder));
                    }
                },this);

        demolishButton =new GUIButton("Demolish",(int)(10+(Offset*((SquaresOnSide-2)))),10+2*Offset,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        InvestmentProperty breaker=(InvestmentProperty) getSelectedLocation().getLocation();
                        gui.updateAction(panopoly.demolishUnit(breaker));
                    }
                },this);
        leaveButton =new GUIButton("Leave",(int)(10+Offset),-20+(SquaresOnSide-1)*Offset,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        panopoly.leaveGame();
                    }
                },this);
        leaveButton.setVisible(true);
        quitButton =new GUIButton("Quit",(int)(10+(Offset*((SquaresOnSide-2)))),-20+(SquaresOnSide-1)*Offset,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        panopoly.endGame();
                    }
                },this);
        quitButton.setVisible(true);
        int x=-Offset;
        for(int i=0;i<4;i++)
        {
            answers[i]=new GUIButton("Answer",(int)(10+(Offset*((SquaresOnSide-2)/2.0)))+x,-20+(SquaresOnSide-1)*Offset,
                   null,this);
            x+=Offset;
        }

    }
    
    private void setVisibleButtons()
    {
    	if(panopoly.getCurrentPlayer().isInJail())
		{
			gui.rollCommand = false;
			gui.endCommand = false;
		}
		
		else
		{
			gui.rollCommand = panopoly.getCurrentPlayer().canRoll;
			//unowned property and player has rolled at least once
			gui.endCommand = (!gui.rollCommand && panopoly.getCurrentPlayer().getBalance() >= 0);
		}
    	
    	rollButton.setVisible(rollCommand);
    	endButton.setVisible(endCommand);
    	if (panopoly.getCurrentPlayer().isInJail()) {
            setSelectedLabel(null);
            setSelectedLabel(getLocationLabel(panopoly.getCurrentPlayer().getPosition()));

            for(GUIButton button:answers)
            {
                button.setVisible(true);
                button.setMouseEvent(incorrect);
            }
            answers[0].setMouseEvent(correct);
            //todo get cian stuff here

        }
        //leaveButton.setVisible(!rollCommand&&!endCommand);
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
        LocationLabel label=getSelectedLocation();
        setSelectedLabel(label);
        setSelectedLabel(label);
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