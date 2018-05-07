//package monopoly;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//
//public class ClientHandler implements Runnable{
//
//    DataInputStream in;
//    PanopolyClient client;
//    DataOutputStream out;
//
//    ClientHandler(DataInputStream in, DataOutputStream out, PanopolyClient c){
//        this.in = in;
//        this.client = c;
//        this.out = out;
//    }
//    @Override
//    public void run() {
//
//            try {
//
//                    System.out.println("hi");
//                    char status = in.readChar();
////                System.out.println("received f");
////                int playerId = in.readInt();
//                    client.whatUpDog(status);
//            } catch (IOException e) {
//                System.out.println("handler");
//            }
//
//        }
//    }
