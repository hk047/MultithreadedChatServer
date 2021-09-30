import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

class ServerSender implements Runnable{

    private final Socket serverSocket;

    public ServerSender(Socket socket) {
        serverSocket = socket;
    }

    @Override
    public void run() {

        try {

            //sets up the ability for user to input data
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            //sets up ability to send user's data to the server
            PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream(), true);

            //gets user input and sends to the server
            while(true){
                String userInputString = userInput.readLine();
                serverOut.println(userInputString);
            }

        }
        catch (SocketException e) {
            System.out.println("Connection lost.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e){
            System.out.println("No server to connect to.");
        }

        //disconnect
        try {
            System.out.println("Closing socket");
            serverSocket.close();
            System.exit(0);
        } catch (SocketException e) {
            System.out.println("Connection to server lost.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("Disconnected.");
        }
    }
}

