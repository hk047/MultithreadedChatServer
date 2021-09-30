import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

class ClientManager implements Runnable {

    private final Socket clientSocket;
    private final ArrayList<ClientManager> clientManagers;
    boolean endThread = false;

    public ClientManager(Socket socket, ArrayList<ClientManager> clientManagers) {
        this.clientSocket = socket;
        this.clientManagers = clientManagers;
    }


    @Override
    public void run() {

        //while the thread is still alive
        while (!Thread.currentThread().isInterrupted()) {

            //setup the ability to read data from the client
            InputStreamReader clientCharStream = null;
            try {
                clientCharStream = new InputStreamReader(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // read data from the client
            assert clientCharStream != null;
            BufferedReader clientIn = new BufferedReader(clientCharStream);
            String userInput = null;
            try {
                userInput = clientIn.readLine();
            } catch (SocketException e) {
                System.out.println("Client disconnected");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }

            PrintWriter clientOut = null;

            for (ClientManager cm : clientManagers) {
                if (userInput != null) {

                    //if the current clientManager port is different to the one it is comparing to
                    //prevents messages echoing
                    if (this.clientSocket.getPort() != cm.clientSocket.getPort()) {
                        try {
                            clientOut = new PrintWriter(cm.clientSocket.getOutputStream(), true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        clientOut.println(userInput);
                    }
                }
            }
        }
    }
}

