package GUI;

import javax.swing.*;

public class UI {

    private int BOARD_SIZE,FRAME_SIZE;
    private InfoPanel infoPanel = new InfoPanel();
    private CommandPanel commandPanel = new CommandPanel();
    private BoardPanel boardPanel = new BoardPanel(BOARD_SIZE,FRAME_SIZE);


    UI(int BoardSize,int FrameSquareSize){

            BOARD_SIZE=BoardSize;
            FRAME_SIZE=FrameSquareSize;
            JFrame frame = new JFrame("PANOPOLY");
            frame.setSize(FRAME_SIZE, FRAME_SIZE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.add(infoPanel);
//            frame.add(commandPanel);
            frame.add(boardPanel);
            frame.pack();
            frame.setVisible(true);
    }
}
