package AggregationServer;

import java.net.*;
import java.io.*;

public class AggregationServer {
  private ServerSocket server_socket;
  private Socket client_socket;
  private PrintWriter out;
  private BufferedReader in;

  public void start(int port) throws IOException {
    server_socket = new ServerSocket(port);
    client_socket = server_socket.accept();
    out = new PrintWriter(client_socket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
    String greeting = in.readLine();
    if ("hello server".equals(greeting)) {
      out.println("hello client");
    } else {
      out.println("unrecognised greeting");
    }
  }

  public void stop() throws IOException {
    in.close();
    out.close();
    client_socket.close();
    server_socket.close();
  }

  public static void main(String[] args) throws IOException {
    AggregationServer server = new AggregationServer();
    server.start(6666);
  }
}
