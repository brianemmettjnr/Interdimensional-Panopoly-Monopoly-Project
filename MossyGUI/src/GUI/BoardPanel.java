package GUI;

import javax.swing.*;

import static javafx.scene.input.KeyCode.J;


public class BoardPanel extends JPanel{

    BoardPanel(int BOARD_SIZE,int FRAME_SIZE){

        int SquaresOnSide=(((BOARD_SIZE-4)/4)+2);
        double frameSize=FRAME_SIZE*.9;
        int Offset=(int)(frameSize)/SquaresOnSide;
        int x=Offset,y=0;
        int height=Offset-1,width=Offset-1;
        int NumOnBoard=0;
        //setLayout(null);

        JLabel[] userLabels = new JLabel[BOARD_SIZE];

        this.add(new JLabel("Hello"));
//        userLabels[NumOnBoard]=new JLabel(""+NumOnBoard);
//        userLabels[NumOnBoard].setBounds(x,y,width,height);
//        this.add(userLabels[NumOnBoard]);

//        while (x<Offset*SquaresOnSide)
//        {
//            userLabels[NumOnBoard]=new JLabel(""+NumOnBoard);
//            userLabels[NumOnBoard].setBounds(x,y,width,height);
//            add(userLabels[NumOnBoard]);
//            x+=Offset;
//            NumOnBoard++;
//
//        }
//        while(y<(Offset*(SquaresOnSide-1)))
//        {
//            userLabels[NumOnBoard]=new JLabel(""+NumOnBoard);
//            userLabels[NumOnBoard].setBounds(x,y,height,width);
//            this.add(userLabels[NumOnBoard]);
//            y+=Offset;
//            NumOnBoard++;
//
//        }
//        while (x>Offset)
//        {
//            userLabels[NumOnBoard]=new JLabel(""+NumOnBoard);
//            userLabels[NumOnBoard].setBounds(x,y,height,width);
//            this.add(userLabels[NumOnBoard]);
//            x-=Offset;
//            NumOnBoard++;
//        }
//        while (y>=Offset)
//        {
//            userLabels[NumOnBoard]=new JLabel(""+NumOnBoard);
//            userLabels[NumOnBoard].setBounds(x,y,height,width);
//            this.add(userLabels[NumOnBoard]);
//            y-=Offset;
//            NumOnBoard++;
//        }
    }
}
