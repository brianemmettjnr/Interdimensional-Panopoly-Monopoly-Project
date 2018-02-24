import jdk.internal.util.xml.impl.Input;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static java.awt.Color.BLACK;
import static java.awt.Color.white;

public class GUI {

    public int BOARD_SIZE,FRAME_SIZE;
    private JLabel[] jlabels;
    public GUI(int BoardSize,int FrameSquareSize)
    {
        BOARD_SIZE=BoardSize;
        FRAME_SIZE=FrameSquareSize;
        JFrame frame = new JFrame("PANOPOLY");
        frame.setSize(FRAME_SIZE, FRAME_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jlabels=new JLabel[BOARD_SIZE];
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(true);
        panel.setBackground(white);
        frame.add(panel);
        placeComponents(panel);
        // Setting the frame visibility to true
        frame.setVisible(true);
    }
    public JLabel getPropertyLabel(int index) throws ArrayIndexOutOfBoundsException{
        return jlabels[index];
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
            updateLabels(panel,jlabels[NumOnBoard],x,y,height,width,NumOnBoard,border);
            x+=Offset;
            NumOnBoard++;

        }
        while(y<(Offset*(SquaresOnSide-1)))
        {
            updateLabels(panel,jlabels[NumOnBoard],x,y,height,width,NumOnBoard,border);
            y+=Offset;
            NumOnBoard++;

        }
        while (x>=Offset)
        {
            updateLabels(panel,jlabels[NumOnBoard],x,y,height,width,NumOnBoard,border);
            x-=Offset;
            NumOnBoard++;
        }
        while (y>=Offset)
        {

            updateLabels(panel,jlabels[NumOnBoard],x,y,height,width,NumOnBoard,border);
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
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }
        });
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        label.setBounds(x,y,height,width);
        label.setBorder(border);
        panel.add(label);
    }
}