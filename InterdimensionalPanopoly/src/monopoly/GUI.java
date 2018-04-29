package monopoly;
import interfaces.Groupable;
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
    private LocationLabel SelectedLabel=null;
    static String[] characters={"boat","car","dog","hat","iron","thimble"};
    private JLabel image;
    private static ArrayList<Player> players=new ArrayList<>();
	
    boolean rollCommand,endCommand;
	private GUIButton buyButton, rollButton, endturn, mortgageButton, redeemButton,buildButton,demoButton;
    
    private static Panopoly panopoly;
    static BufferedImage[] images = new BufferedImage[6];
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

    public static void setPlayers(ArrayList<Player> players) {
        GUI.players = players;
    }

    public static void setPanopoly(Panopoly panopoly) {
        GUI.panopoly = panopoly;
    }

    private void setupbuttons()
    {
        rollButton=new GUIButton("Roll",(int)((FRAME_SIZE.getHeight()*.9)/2)-10,(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,
        new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gui.updateAction(panopoly.roll());
                if(getSelectedLocation()!=getLocationLabel(panopoly.getCurrentPlayer().getPosition())) {
                    setSelectedLabel(getLocationLabel(panopoly.getCurrentPlayer().getPosition()));
                }
            }
        },this);

        buyButton=new GUIButton("Buy",(int)(FRAME_SIZE.getHeight()*.9)/2-getOffset()-10,(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Rentable buyProperty= (Rentable)panopoly.getBoard().getLocation(panopoly.getCurrentPlayer().getPosition());
                        gui.updateAction(panopoly.buyProperty(buyProperty));
                        gui.updateGUI();
                    }
                },this);

        endturn=new GUIButton("End",(int)((FRAME_SIZE.getHeight()*.9)/2)-10,(((int)(FRAME_SIZE.getHeight()*.9))/2)+240,
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

    }
    
    private void setVisibleButtons()
    {
    	panopoly.setPossibleCommands();
    	rollButton.setVisible(rollCommand);
    	endturn.setVisible(endCommand);
    }
    
    void resetCommands()
    {
    	rollCommand = false;
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
    static ArrayList<Player> getPlayersArray()
    {
        return players;
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
                }
            }

            else
            {
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
        secondAction.setText(latestAction.getText());
        latestAction.setText(action);
        LocationLabel label=getSelectedLocation();
        setSelectedLabel(label);
        setSelectedLabel(label);
        panopoly.setPossibleCommands();
        updateGUI();
    }


    int getOffset()
    {
        return  Offset;
    }

     private LocationLabel[] getLocationLabels() {
        return LocationLabels;
    }
     static ArrayList<Player> getPlayers() {
        return players;
    }
}