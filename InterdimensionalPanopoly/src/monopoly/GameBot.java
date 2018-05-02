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
        gui.rollFunction();
        gui.endTurnFunction();
    }
}
