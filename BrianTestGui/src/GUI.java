import com.sun.org.apache.xml.internal.security.utils.JDKXPathAPI;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.awt.Color.BLACK;
import static java.awt.Color.white;

public class GUI {

    private int BOARD_SIZE,FRAME_SIZE;
    private JLabel[] PropertyLabels;
    private JPanel MainPanel;
    private JFrame MainFrame;

    public GUI(int BoardSize,int FrameSquareSize)
    {
        BOARD_SIZE=BoardSize;
        FRAME_SIZE=FrameSquareSize;
        MainFrame = new JFrame("PANOPOLY");
        MainFrame.setSize(FRAME_SIZE, FRAME_SIZE);
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PropertyLabels =new JLabel[BOARD_SIZE];
        MainPanel = new JPanel();
        MainPanel.setLayout(null);
        MainPanel.setOpaque(true);
        MainPanel.setBackground(white);
        MainFrame.add(MainPanel);
        placeComponents(MainPanel);
        // Setting the frame visibility to true
        MainFrame.setVisible(true);
    }
    public JLabel getPropertyLabel(int index) throws ArrayIndexOutOfBoundsException{
        return PropertyLabels[index];
    }

    private void placeComponents(JPanel panel)
    {
        int SquaresOnSide=(((BOARD_SIZE-4)/4)+2);
        double frameSize=FRAME_SIZE*.9;
        int Offset=(int)(frameSize)/SquaresOnSide;
        int x=10,y=10;
        int height=Offset-1,width=Offset-1;
        int NumOnBoard=0;
        Border border =BorderFactory.createLineBorder(BLACK, 1);
        while (x<Offset*(SquaresOnSide-1))
        {
            updateLabels(panel, PropertyLabels[NumOnBoard],x,y,height,width,NumOnBoard,border);
            x+=Offset;
            NumOnBoard++;

        }
        while(y<(Offset*(SquaresOnSide-1)))
        {
            updateLabels(panel, PropertyLabels[NumOnBoard],x,y,height,width,NumOnBoard,border);
            y+=Offset;
            NumOnBoard++;

        }
        while (x>=Offset)
        {
            updateLabels(panel, PropertyLabels[NumOnBoard],x,y,height,width,NumOnBoard,border);
            x-=Offset;
            NumOnBoard++;
        }
        while (y>=Offset)
        {

            updateLabels(panel, PropertyLabels[NumOnBoard],x,y,height,width,NumOnBoard,border);
            y-=Offset;
            NumOnBoard++;
        }
        JLabel image=new JLabel(new ImageIcon("BrianTestGui\\src\\ReasonsWhyBrianIsntAGraphicDesigner.png"));
        image.setBounds((FRAME_SIZE-200)/2,(FRAME_SIZE-200)/2,200,200);//this isnt relative yet okay jeez
        panel.add(image);

    }
    private void updateLabels(JPanel panel,JLabel label,int x,int y,int height, int width,int NumOnBoard,Border border)
    {
        label=new JLabel("       "+NumOnBoard);
        JLabel finalLabel = label;
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                finalLabel.setText("hello");
            }
        });
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                finalLabel.setText("bye");
            }
        });
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                finalLabel.setText("hi");
            }
        });
        label.setBounds(x,y,height,width);
        label.setBorder(border);
        panel.add(label);
    }
}