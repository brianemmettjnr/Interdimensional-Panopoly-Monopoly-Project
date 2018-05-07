package interfaces;

public interface InteractionAPI {

    //helpButton,buyButton, rollButton, endButton, mortgageButton,leaveButton, redeemButton,buildButton, demolishButton,quitButton

    //Functions that a bot can use to play the game without buttons
    void rollFunction();
    void wonGameStopFunction();
    void wonGameReplayFunction();
    void endTurnFunction();
    void getHelpFunction();
    void buyPropertyFunction();
    void mortgagePropertyFunction();
    void redeemPropertyFunction();
    void wonGameQuitFunction();
    void wonGameReplayFunction();
    void buildHouseFunction();
    void demolishHouseFunction();
    void quitTheGameFunction();
    void leaveGameFunction();
    void answerCorrectlyFunction();
    void answerIncorrectlyFunction();
}
