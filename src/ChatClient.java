import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLOutput;

public class ChatClient {
    private Socket serverSocket;

    public ChatClient(String address, int port) {

        try {
            serverSocket = new Socket(address, port);
        } catch (IOException e) {
            System.out.println("Invalid arguments.");
        }
    }

    public void go (){

        try {

            //creating new sending and receiving threads to and from the server
            ServerReader serverReader = new ServerReader(serverSocket);
            ServerSender serverSender = new ServerSender(serverSocket);

            Thread t1 = new Thread(serverReader);
            t1.start();
            Thread t2 = new Thread(serverSender);
            t2.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        try {

            //if the user is attempting to pass an IP address and port
            if ((args[0].equals("-cca")) && (args[2].equals("-ccp")) && (Integer.parseInt(args[3]) >= 0) && (Integer.parseInt(args[3]) <= 65535)) {
                ChatClient myClient = new ChatClient(args[1], Integer.parseInt(args[3]));
                myClient.go();
                System.out.println("Successful connection to server.");
            }
            else System.out.println(args[3]);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e1) {
            //catches an exception caused if the user only wants to change IP address OR port
            try{
                //checks if they want to change port, and if port given is valid
                if ((args[0].equals("-ccp")) && (Integer.parseInt(args[1]) >= 0) && (Integer.parseInt(args[1]) <= 65535)) {
                    ChatClient myClient = new ChatClient("localhost", Integer.parseInt(args[1]));
                    myClient.go();
                    System.out.println("Successful connection to server.");
                }
                //checks if they want to change the IP address
                else if (args[0].equals("-cca")){
                    ChatClient myClient = new ChatClient(args[1], 14001);
                    myClient.go();
                    System.out.println("Successful connection to server.");
                }
                //incorrect port, or invalid IP address
                else{
                    System.out.println("Invalid arguments.");
                    ChatClient myClient = new ChatClient("localhost", 14001);
                    myClient.go();
                    System.out.println("Default connection established.");
                }
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e2){
                //catches an exception caused if no additional arguments are given
                ChatClient myClient = new ChatClient("localhost", 14001);
                myClient.go();
                System.out.println("Default connection established.");
            }
        }
    }
}