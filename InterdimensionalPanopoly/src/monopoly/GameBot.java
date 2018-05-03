package monopoly;

import interfaces.Playable;
import interfaces.Rentable;

import java.util.ArrayList;

public class GameBot extends Player{


    public GameBot(String name, int imageIndex, int playerIndex, Panopoly panopoly) {
        super(name, imageIndex, playerIndex, panopoly);
    }

    @Override
    public String getIdentifier() {
        return name+"(BOT)";
    }

    public void makeGameDecision(GUI gui){

        if(gui.rollButton.isVisible()) {
            gui.rollFunction();
        } //to buy if you have the money
        else if(gui.buyButton.isVisible()){
            gui.buyPropertyFunction();
        }
        else if(gui.endButton.isVisible()){
            gui.endTurnFunction();
        }//for the pussies who cant answer a question
        else if(!gui.rollButton.isVisible() && !gui.endButton.isVisible()){
            gui.leaveGameFunction();

        }

    }
}
