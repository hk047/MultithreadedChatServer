import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

class ServerReader implements Runnable{

    private final Socket serverSocket;

    public ServerReader(Socket socket){
        serverSocket = socket;
    }

    @Override
    public void run() {
        try {

            //sets up ability to read data from server
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            while(true){

                //reads and prints valid data from server
                String serverResponse = serverIn.readLine();
                if(serverResponse != null){
                    System.out.println(serverResponse);
                }

                //if the server gives null data
                else{
                    System.out.println("Server has been disconnected.");
                    break;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("No server to connect to.");
        }

        finally{
            //closes the client connection
            try {
                System.out.println("Closing socket");
                serverSocket.close();
                System.exit(0);
            }
            catch (SocketException e) {
                System.out.println("Connection to server lost.");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e){
                System.out.println("Disconnected.");
            }
        }
    }
}