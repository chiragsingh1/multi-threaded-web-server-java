
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public void run() throws UnknownHostException, IOException {
        int port = 8010;
        InetAddress address = InetAddress.getByName("localhost");

        Socket client = new Socket(address, port);
        PrintWriter toServer = new PrintWriter(client.getOutputStream(), true);
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));
        toServer.println("Hello from the client!");
        String line = fromServer.readLine();
        System.out.println("Response from the socket is: " + line);
        toServer.close();
        fromServer.close();
        client.close();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
