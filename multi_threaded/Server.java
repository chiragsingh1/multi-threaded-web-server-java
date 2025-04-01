import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// import java.util.function.Consumer;

public class Server {
    private final String htmlFilePath = "../index.html";

    public void handleClient(Socket clientSocket) {
        try (
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream outputStream = clientSocket.getOutputStream();
                PrintWriter headerWriter = new PrintWriter(outputStream, true)) {

            // Read client request (but we're ignoring it for simplicity)
            String line;
            while ((line = fromClient.readLine()) != null && !line.isEmpty()) {
                // You could parse the HTTP request here if needed
                System.out.println("Client: " + line);
            }

            Path path = Paths.get(htmlFilePath);
            byte[] fileContent = Files.readAllBytes(path);

            headerWriter.println("HTTP/1.1 200 OK");
            headerWriter.println("Content-Type: text/html");
            headerWriter.println("Content-Length: " + fileContent.length);
            headerWriter.println("Connection: close");
            headerWriter.println();
            headerWriter.flush();

            outputStream.write(fileContent);
            outputStream.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(70000);
            System.out.println("Multi-threaded server is listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from: " + clientSocket.getInetAddress());
                // Create and start a new thread for each client
                Thread thread = new Thread(() -> server.handleClient(clientSocket));
                thread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}