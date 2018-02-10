import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
public class GUI {

    public int BOARD_SIZE;
    public GUI(int BoardSize)
    {
        BOARD_SIZE=BoardSize;
        JFrame frame = new JFrame("PANOPOLY");
        frame.setSize(1000,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel[] panels = new JPanel[BoardSize];
        for(int i=0;i<BOARD_SIZE;i++)
        {
            panels[i]=new JPanel();
            panels[i].setLayout(null);
            frame.add(panels[i]);
        }
        placeComponents(panels);
        // Setting the frame visibility to true
        frame.setVisible(true);
    }
    private void placeComponents(JPanel[] panel)
    {
        int SquaresOnSide=(((BOARD_SIZE-4)/4)+2);
        int Offset=1000/SquaresOnSide;
        int x=Offset*(SquaresOnSide-1),y=Offset;
        int height=Offset-1,width=Offset-1;
        int NumOnBoard=0;
        JLabel[] userLabels = new JLabel[BOARD_SIZE];
        while (x>Offset)
        {
            userLabels[NumOnBoard]=new JLabel("PLACEHOLDER");
            userLabels[NumOnBoard].setBounds(x,y,100,30);
            panel[NumOnBoard].add(userLabels[NumOnBoard]);
            x-=Offset;
            NumOnBoard++;
        }
        while(y<=(Offset*SquaresOnSide-1))
        {
            userLabels[NumOnBoard]=new JLabel("PLACEHOLDER");
            userLabels[NumOnBoard].setBounds(x,y,height,width);
            panel[NumOnBoard].add(userLabels[NumOnBoard]);
            y+=Offset;
            NumOnBoard++;
        }
        while (x<(Offset*SquaresOnSide-1))
        {
            userLabels[NumOnBoard]=new JLabel("PLACEHOLDER");
            userLabels[NumOnBoard].setBounds(x,y,height,width);
            panel[NumOnBoard].add(userLabels[NumOnBoard]);
            x+=Offset;
            NumOnBoard++;
        }
        while (y>Offset)
        {
            userLabels[NumOnBoard]=new JLabel("PLACEHOLDER");
            userLabels[NumOnBoard].setBounds(x,y,height,width);
            panel[NumOnBoard].add(userLabels[NumOnBoard]);
            y-=Offset;
            NumOnBoard++;
            System.out.println(y);
        }
    }

}/////////jesus christ i fucking hate guis OOPS CAPS LOCK WAS ONE awhdIA\£wdh||b oi@{unblk