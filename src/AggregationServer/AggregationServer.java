package AggregationServer;

import java.net.*;
import java.io.*;

public class AggregationServer {
  private ServerSocket server_socket;

  public static void main(String[] args) {
    AggregationServer server = new AggregationServer();
    try {
      server.start(6666);
    } catch (IOException e) {
      System.err.println("Problem starting server");
      e.printStackTrace();
    }
  }

  public void start(int port) throws IOException {
    server_socket = new ServerSocket(port);
    while (true)
      new ClientHandler(server_socket.accept()).start();
  }

  public void stop() throws IOException {
    server_socket.close();
  }

  private static class ClientHandler extends Thread {
    private Socket client_socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket) {
      this.client_socket = socket;
    }

    public void run() {
      try {
        out = new PrintWriter(client_socket.getOutputStream(), true);
        in = new BufferedReader(
            new InputStreamReader(client_socket.getInputStream()));

        String input_line;
        while ((input_line = in.readLine()) != null) {
          if (".".equals(input_line)) {
            out.println("bye");
            break;
          }
          out.println(input_line);
        }

        in.close();
        out.close();
        client_socket.close();
      } catch (IOException e) {
        System.err.println("Server Client Handler Error");
        e.printStackTrace();
      }
    }
  }
}