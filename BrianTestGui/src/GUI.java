import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static java.awt.Color.BLACK;

public class GUI {

    public int BOARD_SIZE,FRAME_SIZE;
    public GUI(int BoardSize,int FrameSquareSize)
    {
        BOARD_SIZE=BoardSize;
        FRAME_SIZE=FrameSquareSize;
        JFrame frame = new JFrame("PANOPOLY");
        frame.setSize(FRAME_SIZE, FRAME_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
           panel.setLayout(null);
            frame.add(panel);
        placeComponents(panel);
        // Setting the frame visibility to true
        frame.setVisible(true);
    }
    private void placeComponents(JPanel panel)
    {
        int SquaresOnSide=(((BOARD_SIZE-4)/4)+2);
        double frameSize=FRAME_SIZE*.9;
        int Offset=(int)(frameSize)/SquaresOnSide;
        int x=Offset,y=0;
        int height=Offset-1,width=Offset-1;
        int NumOnBoard=0;
        Border border =BorderFactory.createLineBorder(BLACK, 1);
        JLabel[] userLabels = new JLabel[BOARD_SIZE];
        while (x<Offset*SquaresOnSide)
        {
            userLabels[NumOnBoard]=new JLabel(""+NumOnBoard);
            userLabels[NumOnBoard].setBounds(x,y,width,height);
            panel.add(userLabels[NumOnBoard]);
            userLabels[NumOnBoard].setBorder(border);
            x+=Offset;
            NumOnBoard++;

        }
        while(y<(Offset*(SquaresOnSide-1)))
        {
            userLabels[NumOnBoard]=new JLabel(""+NumOnBoard);
            userLabels[NumOnBoard].setBounds(x,y,height,width);
            panel.add(userLabels[NumOnBoard]);
            userLabels[NumOnBoard].setBorder(border);
            y+=Offset;
            NumOnBoard++;

        }
        while (x>Offset)
        {
            userLabels[NumOnBoard]=new JLabel(""+NumOnBoard);
            userLabels[NumOnBoard].setBounds(x,y,height,width);
            panel.add(userLabels[NumOnBoard]);
            userLabels[NumOnBoard].setBorder(border);
            x-=Offset;
            NumOnBoard++;
        }
        while (y>=Offset)
        {
            userLabels[NumOnBoard]=new JLabel(""+NumOnBoard);
            userLabels[NumOnBoard].setBounds(x,y,height,width);
            panel.add(userLabels[NumOnBoard]);
            userLabels[NumOnBoard].setBorder(border);
            y-=Offset;
            NumOnBoard++;
        }
    }

}/////////jesus christ i fucking hate guis OOPS CAPS LOCK WAS ONE awhdIA\£wdh||b oi@{unblk