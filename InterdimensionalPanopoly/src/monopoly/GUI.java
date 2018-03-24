package monopoly;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.awt.Color.*;

public class GUI {

    private int BOARD_SIZE;
    private Dimension FRAME_SIZE;
    private LocationLabel[] LocationLabels;
    private JLabel[] PropertyLabels;
    private PlayerLabel[] PlayerLabels;
    private NamedLocation[] Locations;
    private JPanel MainPanel;
    private JFrame MainFrame;
    private int LabelWidth,LabelHeight;
    private static JTextField nameSpace=new JTextField();
    private JLabel SelectedLabel=new JLabel();
    private static String[] characters={"boat","car","dog","hat","iron","thimble"};
    private static int selectedpictureIndex=-1;
    private static JLabel selectedImage =null;
    private static int playerCount=0;
    private static ArrayList<Player> players=new ArrayList<Player>();
	public static boolean rentCommand;
	private static Panopoly panopoly;
    private static BufferedImage[] images = new BufferedImage[6];

    public GUI(int BoardSize, Dimension FrameDimension)
    {
        BOARD_SIZE=BoardSize;
        FRAME_SIZE=FrameDimension;
        MainFrame = new JFrame("PANOPOLY");
        MainFrame.setSize(FRAME_SIZE);
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PropertyLabels =new JLabel[BOARD_SIZE];
        LocationLabels=new LocationLabel[BOARD_SIZE];
        MainPanel = new JPanel();
        MainPanel.setLayout(null);
        MainPanel.setOpaque(true);
        MainPanel.setBackground(WHITE);
        MainFrame.add(MainPanel);
        PlaceBoard();
        PlayerLabels=new PlayerLabel[players.size()];
        PlacePlayers();
//        for(Player player:players)
//        {
//            JLabel icon=player.getIcon();
//            icon.setVisible(true);
//            icon.
//        }
//        // Setting the frame visibility to true
        MainFrame.setVisible(true);
        MainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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

    public JLabel getPropertyLabel(int index) throws ArrayIndexOutOfBoundsException{
        return PropertyLabels[index];
    }

    private void PlaceBoard()
    {
        int SquaresOnSide=(((BOARD_SIZE-4)/4)+2);
        int frameSize=(int)(FRAME_SIZE.getHeight()*.9);
        int Offset=(frameSize)/SquaresOnSide;
        int x=10,y=10;
        LabelHeight=Offset;
        LabelWidth=Offset;
        int NumOnBoard=0;
        while (x<Offset*(SquaresOnSide-1))
        {
            PropertyLabels[NumOnBoard]=new JLabel();
            LocationLabels[NumOnBoard]= new LocationLabel(PropertyLabels[NumOnBoard],x,y,NumOnBoard,this);
            x+=Offset;
            NumOnBoard++;

        }
        while(y<(Offset*(SquaresOnSide-1)))
        {
            PropertyLabels[NumOnBoard]=new JLabel();
            LocationLabels[NumOnBoard]= new LocationLabel(PropertyLabels[NumOnBoard],x,y,NumOnBoard,this);
            y+=Offset;
            NumOnBoard++;

        }
        while (x>=Offset)
        {
            PropertyLabels[NumOnBoard]=new JLabel();
            LocationLabels[NumOnBoard]= new LocationLabel(PropertyLabels[NumOnBoard],x,y,NumOnBoard,this);
            x-=Offset;
            NumOnBoard++;
        }
        while (y>=Offset)
        {
            PropertyLabels[NumOnBoard]=new JLabel();
            LocationLabels[NumOnBoard]= new LocationLabel(PropertyLabels[NumOnBoard],x,y,NumOnBoard,this);
            y-=Offset;
            NumOnBoard++;
        }
        JLabel image=new JLabel(new ImageIcon("InterdimensionalPanopoly\\src\\ReasonsWhyBrianIsntAGraphicDesigner.png"));
        image.setBounds(((frameSize)/2)-200,((frameSize)/2)-200,400,400);//this isnt relative yet okay jeez
        MainPanel.add(image);
    }
    public static void PlayerCountGui(Panopoly panopoly1)
    {
    	panopoly = panopoly1;
        //how many players
        JFrame playerFrame= new JFrame("INTERDIMENSIONAL PANOPOLY");
        JPanel playerPanel=new JPanel();

        playerFrame.setSize(400,210);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        playerFrame.setLocation(dim.width/2-playerFrame.getSize().width/2, dim.height/2-playerFrame.getSize().height/2);
        playerFrame.add(playerPanel);
        //panel changes after here
        playerPanel.setBackground(Color.WHITE);
        playerPanel.setLayout(null);
        JLabel image=new JLabel(new ImageIcon("src\\MiniLogo.png"));
        JLabel[] button=new JLabel[5];
        Border border=BorderFactory.createLineBorder(MAGENTA,2,true);
        for(int i=0;i<5;i++)
        {
            button[i]=new JLabel();
            button[i].setBounds(((i)*75)+7,80,70,80);
            button[i].setBorder(border);
            button[i].setText("  "+(i+2)+" Players");
            int finalI = i;
            button[i].addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button[finalI].setText(finalI +2+" Players?");
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
                images[i] = ImageIO.read(new File("InterdimensionalPanopoly\\src\\"+characters[i]+".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        JFrame playerFrame= new JFrame("INTERDIMENSIONAL PANOPOLY");
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
        nameSpace.setText("");
        final int[] playerIncrement = {0};
        sendinputButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!(nameSpace.getText().isEmpty()||selectedImage==null||nameSpace.getText()==null))
                {
                    upperline.setForeground(Color.white);
                    players.add(new Player(nameSpace.getText(),selectedpictureIndex,playerIncrement[0]));
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
    public void updatePlayerBalances()
    {
        for(PlayerLabel label:PlayerLabels)
        {
            label.updateLabel();
        }
    }
    public static ArrayList<Player> getPlayersArray()
    {
        return players;
    }
    public int getPlayerCount()
    {
        return  playerCount;
    }
    public JLabel getSelectedLocation()
    {
        return SelectedLabel;
    }
    public void setSelectedLabel(JLabel location)
    {
        this.SelectedLabel=location;
    }
    public JPanel getMainPanel()
    {
        return MainPanel;
    }
    public int getLabelWidth()
    {
        return LabelWidth;
    }
    public Dimension getFRAME_SIZE() {
        return FRAME_SIZE;
    }

    public int getLabelHeight() {
        return LabelHeight;
    }
}