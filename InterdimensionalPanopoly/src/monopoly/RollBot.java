package monopoly;

public class RollBot extends GameBot {

    private GUI gui;
    public RollBot(String name, int imageIndex, int playerIndex, Panopoly panopoly) {
        super(name, imageIndex, playerIndex, panopoly);
    }
    @Override
    public void makeGameDecision() {
        if(isInJail()){
            gui.answerCorrectlyFunction();
        } else if(gui.auctionTimer.isVisible()){

        } else if(gui.rollButton.isVisible()) {
            gui.rollFunction();
        } else if(gui.exitGame.isVisible()){
            gui.wonGameQuitFunction();
        } else if(gui.endButton.isVisible()){
            gui.endTurnFunction();
        } else if(balance<0){
            gui.leaveGameFunction();
        } else {
            gui.leaveGameFunction();
        }
    }
    @Override
    public void setGUI(GUI gui)
    {
        this.gui=gui;
    }
}
