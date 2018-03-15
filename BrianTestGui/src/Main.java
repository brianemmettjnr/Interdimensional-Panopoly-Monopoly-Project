import java.awt.*;
import java.util.Random;

public class Main
{
    public static void main(String args[])
    {
        Random rand=new Random();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        GUI gui = new GUI(4*(rand.nextInt((12-4))+4),screenSize);
        GUI.PlayerCountGui();
    }
}