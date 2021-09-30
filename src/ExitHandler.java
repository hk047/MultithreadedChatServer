import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

class ExitHandler implements Runnable{

    private final ServerSocket serverSocket;

    public ExitHandler(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        String userInputString = null;

        while (true) {

            //gets input from the user to the server
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            try {
                userInputString = userInput.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //if the user types EXIT
            if (userInputString != null && userInputString.equals("EXIT")) {
                //force quits the server
                System.exit(0);
            }
        }


    }
}