package ContentServer;

import java.net.*;
import java.io.*;

public class ContentServer {
  private Socket content_socket;
  private PrintWriter out;
  private BufferedReader in;

  public void startConnection(String ip, int port) throws UnknownHostException, IOException {
    content_socket = new Socket(ip, port);
    out = new PrintWriter(content_socket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(content_socket.getInputStream()));
  }

  public String sendMessage(String msg) throws IOException {
    out.println(msg);
    String resp = in.readLine();
    return resp;
  }

  public void stopConnection() throws IOException {
    in.close();
    out.close();
    content_socket.close();
  }
}
