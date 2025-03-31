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

public class Server {
    private final String htmlFilePath = "../index.html";

    public void handleClient(Socket clientSocket) throws IOException {
        try (
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream outputStream = clientSocket.getOutputStream();
                PrintWriter headerWriter = new PrintWriter(outputStream, true)) {
            String line;
            while ((line = fromClient.readLine()) != null && !line.isEmpty()) {
                // You could parse the HTTP request here if needed
                System.out.println("fromClient: " + line);
            }

            Path path = Paths.get(htmlFilePath);
            byte[] fileContent = Files.readAllBytes(path);

            // Send HTTP headers
            headerWriter.println("HTTP/1.1 200 OK");
            headerWriter.println("Content-Type: text/html");
            headerWriter.println("Content-Length: " + fileContent.length);
            headerWriter.println("Connection: close");
            headerWriter.println(); // Empty line to separate headers from body
            headerWriter.flush();

            // Send the file content
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
        // int port = 8010;
        // ServerSocket socket = new ServerSocket(port);
        // socket.setSoTimeout(20000);

        // while (true) {
        // try {
        // // System.out.println("Server is listening on port: " + port);
        // Socket acceptedConnection = socket.accept();
        // System.out.println("Connection accepted from client: " +
        // acceptedConnection.getRemoteSocketAddress());
        // PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream(),
        // true);
        // BufferedReader fromClient = new BufferedReader(
        // new InputStreamReader(acceptedConnection.getInputStream()));
        // toClient.println("Hello from the server.");
        // toClient.close();
        // fromClient.close();
        // acceptedConnection.close();
        // } catch (IOException ex) {
        // ex.printStackTrace();
        // }
        // }
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(20000);
            System.out.println("Single-threaded server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from: " + clientSocket.getInetAddress());
                server.handleClient(clientSocket);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
