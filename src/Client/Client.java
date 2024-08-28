package Client;

import java.net.*;

import AggregationServer.AggregationServer;

import java.io.*;

public class Client {
  private Socket client_socket;
  private PrintWriter out;
  private BufferedReader in;

  public void startConnection(String ip, int port) throws UnknownHostException, IOException {
    client_socket = new Socket(ip, port);
    out = new PrintWriter(client_socket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
  }

  public String sendMessage(String msg) throws IOException {
    out.println(msg);
    String resp = in.readLine();
    return resp;
  }

  public void stopConnection() throws IOException {
    in.close();
    out.close();
    client_socket.close();
  }

   public static void main(String[] args) {
    /* Create new client object */
    Client client = new Client();

    /* Read server address and port number from command line */
    String address = args[0];
    int port = Integer.parseInt(args[1]);
    
  }
}
