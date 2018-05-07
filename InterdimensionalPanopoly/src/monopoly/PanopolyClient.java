//package monopoly;
//
//import javax.swing.*;
//import java.io.*;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class PanopolyClient implements Runnable {
//
//    static DataInputStream in;
//    static DataOutputStream out;
//    static Socket socket;
//    int playerId;
//    JFrame clientFrame;
//
//    public void makeClient(String serverAddress,int port) throws Exception {
//
//        try {
//            System.out.println("Connecting..");
//            socket = new Socket("localhost", 7777);
//            System.out.println("connected");
//            in = new DataInputStream(socket.getInputStream());
//            playerId = in.readInt();
//            out = new DataOutputStream(socket.getOutputStream());
//            ClientHandler input = new ClientHandler(in,out,this);
//            Thread thread = new Thread(input);
//            thread.start();
//            Thread thread1 = new Thread(this);
//            thread1.start();
//
//        }catch (Exception e){
//            System.out.println("Unable to start client");
//        }
//            in.readChar();
//                System.out.println("YUPPP");
//                if(in.readChar()=='f'){
//                    System.out.println("yoyo");
//                    int x = in.readInt();
//                    PanopolyServer.panopoly.setNetworkedPlayerGUI(x);
//                }
//
//            //System.out.println("board");
//    }
//
//    public static void main(String[] args) throws Exception {
//
//        Scanner keyboard = new Scanner(System.in);
//        System.out.print("Please enter the address of the Nim server (press enter if it is a localhost): ");
////        String serverAddress= keyboard.nextLine();
//        String serverAddress = "localhost";
//        if(serverAddress.equals("")) {
//            serverAddress = "localhost";
//        }
//        int port=7777;
//        PanopolyClient client = new PanopolyClient();
//        client.makeClient(serverAddress,port);
//    }
//    public void whatUpDog(char status){
//        System.out.println(status);
//        if(status=='f'){
//            System.out.println("hey");
//            PanopolyServer.panopoly.setNetworkedPlayerGUI(1);
//        }
//
//    }
//    @Override
//    public void run() {
//        while(true){
//            try {
//                System.out.println("x");
//                System.out.println(in.readChar());
//                if(in.readChar()=='f'){
//                    System.out.println("Received");
//                }
////                out.writeInt(playerId);
////                System.out.println();
//            } catch (IOException e) {
//                System.out.println("pid error");
//            }
//            try {
//                Thread.sleep(400);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
package monopoly;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class PanopolyClient {

    static DataInputStream in;
    static DataOutputStream out;
    static Socket socket;
    static int playerId;
    JFrame clientFrame;

    public void makeClient(String serverAddress,int port) throws Exception {

        try {
            System.out.println("Connecting..");
            socket = new Socket(serverAddress, port);
            System.out.println("connected");
            in = new DataInputStream(socket.getInputStream());
            //playerId = in.readInt();
            out = new DataOutputStream(socket.getOutputStream());
            clientFrame = new JFrame("OK MATE");
            clientFrame.setVisible(true);
            while(true){

                char x = in.readChar();
//                int pid = in.readInt();
                if(x=='f'){
                    System.out.println("RECEIVED");
//                    int pid = in.readInt();
//                    PanopolyServer.panopoly.setNetworkedPlayerGUI(pid);
                }
            }


            //System.out.println("board");

        }catch(Exception e){
            System.out.println("Unable to start client");
        }
    }

    public static void main(String[] args) throws Exception {

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Please enter the address of the Nim server (press enter if it is a localhost): ");
//        String serverAddress= keyboard.nextLine();
        String serverAddress = "localhost";
        if(serverAddress.equals("")) {
            serverAddress = "localhost";
        }
        int port=7777;
        PanopolyClient client = new PanopolyClient();
        client.makeClient(serverAddress,port);
    }
}