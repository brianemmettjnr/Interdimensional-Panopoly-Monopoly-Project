package monopoly;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class NetworkedPlayer extends Player{

    private Socket socket;
    public static DataInputStream in;
    public static DataOutputStream out;

    public NetworkedPlayer(String name, int imageIndex, int playerIndex, Panopoly panopoly, DataOutputStream out, DataInputStream in) {
        super(name, imageIndex, playerIndex, panopoly);
        this.out = out;
        this.in = in;
    }

}
