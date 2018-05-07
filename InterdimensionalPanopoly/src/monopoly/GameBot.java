package monopoly;

import interfaces.Playable;
import interfaces.Rentable;

import java.util.ArrayList;

public class GameBot extends Player{

    private int bidsMade = 0;
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
        }
        //bidding cant work as bid is free for all and the bot player is never active to bid
        /*else if(gui.bidButton.isVisible()){
            if(bidsMade<2){
                gui.bidFunction();
                bidsMade++;
            }else{
                bidsMade=0;
            }gui.getLocationLabel(this.getPosition()).getLocation().
        }*/
        else if(gui.rollButton.isVisible()) {
            gui.rollFunction();
        } else if(gui.exitGame.isVisible()){
            gui.wonGameQuitFunction();
//        } else if(gui.buyButton.isVisible()){
//            if (balance > 300) {
//                gui.buyPropertyFunction();
//            }
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
