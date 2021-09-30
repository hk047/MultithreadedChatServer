import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


public class ChatServer{

    private final ServerSocket serverSocket;
    ArrayList<ClientManager> clientManagers = new ArrayList<ClientManager>();
    ArrayList<Thread> currentThreads = new ArrayList<Thread>();

    public ChatServer(ServerSocket socket) {
        this.serverSocket = socket;
    }

    public void go() throws IOException {

        System.out.println("Server waiting for connection.");

        try {

            // creating new thread to handle a request to exit the server
            ExitHandler exitHandler = new ExitHandler(serverSocket);
            Thread t1 = new Thread(exitHandler);
            t1.start();
            System.out.println("New exitHandler thread started.");

            while (true) {

                //accepts a connection from a client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted a new connection on: " + serverSocket.getLocalPort()
                        + " : " + clientSocket.getPort());

                //creating a new thread to handle inputs and outputs to the client
                ClientManager clientManager = new ClientManager(clientSocket, clientManagers);
                clientManagers.add(clientManager);
                Thread t2 = new Thread(clientManager);
                t2.start();
                System.out.println("New clientManager thread started");

                //adds the newly created thread to an ArrayList of current threads
                currentThreads.add(t2);

            }
        } catch (IOException e) {

            System.out.println("Server is closed.");

        } finally {

            // closes the socket connection
            try {
                serverSocket.close();
                System.out.println("Closing server.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {

        //checks command line args to see is alternative port is specified and creates new connection based on args

        try {

            // if valid arguments are given, establish connection based on arguments
            if ((args[0].equals("-csp")) && (Integer.parseInt(args[1]) >= 0) && (Integer.parseInt(args[1]) <= 65535)) {
                ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[1]));
                ChatServer myServer = new ChatServer(serverSocket);
                myServer.go();
            }

            //if addition arguments are given, but are invalid --> use default
            else {
                System.out.println("Invalid arguments. Defaulting");
            }
        } catch (IndexOutOfBoundsException | IOException e) {
            // no addition arguments given --> use default
            System.out.println("Invalid arguments. Defaulting...");
            ServerSocket serverSocket = new ServerSocket(14001);
            ChatServer myServer = new ChatServer(serverSocket);
            myServer.go();
        }
    }
}


