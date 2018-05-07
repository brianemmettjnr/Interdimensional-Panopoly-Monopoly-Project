package monopoly;

import interfaces.Playable;
import interfaces.Rentable;

import java.util.ArrayList;

public class GameBot extends Player{


    private GUI gui;

    public GameBot(String name, int imageIndex, int playerIndex, Panopoly panopoly) {
        super(name, imageIndex, playerIndex, panopoly);
    }

    @Override
    public String getIdentifier() {
        return name+"(BOT)";
    }

    public void makeGameDecision(){
        if(isInJail()){
            gui.answerCorrectlyFunction();
        } else if(gui.rollButton.isVisible()) {
            gui.rollFunction();
        } else if(gui.buyButton.isVisible()){
            gui.buyPropertyFunction();
        } else if(gui.endButton.isVisible()){
            gui.endTurnFunction();
        } else if(balance<0){
            gui.leaveGameFunction();
        } else {
            gui.leaveGameFunction();
        }

    }
    public void setGUI(GUI gui)
    {
        this.gui=gui;
    }
}
