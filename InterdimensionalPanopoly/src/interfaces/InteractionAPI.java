package interfaces;

public interface InteractionAPI {

    //helpButton,buyButton, rollButton, endButton, mortgageButton,leaveButton, redeemButton,buildButton, demolishButton,quitButton

    //Functions that a bot can use to play the game without buttons
    void rollFunction();
    void endTurnFunction();
    void getHelpFunction();
    void buyPropertyFunction();
    void mortgagePropertyFunction();
    void redeemPropertyFunction();
    void buildHouseFunction();
    void demolishHouseFunction();
    void quitTheGameFunction();
    void leaveGameFunction();
    void answerCorrectlyFunction();
    void answerIncorrectlyFunction();
}
