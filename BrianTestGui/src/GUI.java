import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.*;

public class GUI {

    private int BOARD_SIZE,FRAME_SIZE;
    private LocationLabel[] LocationLabels;
    private JLabel[] PropertyLabels;
    private NamedLocation[] Locations;
    private JPanel MainPanel;
    private JFrame MainFrame;
    private int LabelWidth,LabelHeight;
    private JLabel SelectedLabel=new JLabel();

    public GUI(int BoardSize,int FrameSquareSize)
    {
        BOARD_SIZE=BoardSize;
        FRAME_SIZE=FrameSquareSize;
        MainFrame = new JFrame("PANOPOLY");
        MainFrame.setSize(FRAME_SIZE, FRAME_SIZE);
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PropertyLabels =new JLabel[BOARD_SIZE];
        LocationLabels=new LocationLabel[BOARD_SIZE];
        MainPanel = new JPanel();
        MainPanel.setLayout(null);
        MainPanel.setOpaque(true);
        MainPanel.setBackground(WHITE);
        MainFrame.add(MainPanel);
        placeComponents();
        // Setting the frame visibility to true
        MainFrame.setVisible(true);
    }
    public JLabel getPropertyLabel(int index) throws ArrayIndexOutOfBoundsException{
        return PropertyLabels[index];
    }

    private void placeComponents()
    {
        int SquaresOnSide=(((BOARD_SIZE-4)/4)+2);
        int frameSize=(int)(FRAME_SIZE*.9);
        int Offset=(frameSize)/SquaresOnSide;
        int x=10,y=10;
        LabelHeight=Offset-1;
        LabelWidth=Offset-1;
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
        JLabel image=new JLabel(new ImageIcon("BrianTestGui\\src\\ReasonsWhyBrianIsntAGraphicDesigner.png"));
        image.setBounds(((frameSize)/2)-100,((frameSize)/2)-100,200,200);//this isnt relative yet okay jeez
        MainPanel.add(image);

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

    public int getLabelHeight() {
        return LabelHeight;
    }
}