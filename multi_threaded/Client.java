import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        int port = 8010;
        try {
            InetAddress address = InetAddress.getByName("localhost");
            Socket client = new Socket(address, port);

            PrintWriter toServer = new PrintWriter(client.getOutputStream(), true);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(client.getInputStream()));

            // Send a simple HTTP GET request
            toServer.println("GET / HTTP/1.1");
            toServer.println("Host: localhost:" + port);
            toServer.println("Connection: close");
            toServer.println(); // Empty line to end the request

            // Read and print the server response
            String line;
            boolean headersEnded = false;
            System.out.println("Response from server:");
            while ((line = fromServer.readLine()) != null) {
                System.out.println(line);
                // Print a separator between headers and HTML content
                if (!headersEnded && line.isEmpty()) {
                    System.out.println("--- HTML Content ---");
                    headersEnded = true;
                }
            }

            toServer.close();
            fromServer.close();
            client.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}