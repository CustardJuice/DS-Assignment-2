package Client;

import java.net.*;
import java.io.*;

public class Client {
  public Socket socket;
  public PrintWriter out;
  public BufferedReader in;

  /* Connects the Client object to a running server
   * Parameters:
   * ip, server hostname
   * port, server port number
   */
  public void startConnection(String ip, int port) throws UnknownHostException, IOException {
    socket = new Socket(ip, port);
    out = new PrintWriter(socket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  /* Sends a message to the connected server
   * Parameters:
   * msg, the message to send
   */
  public void sendMessage(String msg) throws IOException {
    out.println(msg);
  }

    /* Reads a message from the server line-by-line, until a null line is read */
  public void recvMessage() throws IOException {
    String line;
    while ((line = in.readLine()) != null) {
      /* print line */
     System.out.println(line);
    }
  }

  /* Ends the connection with the server */
  public void stopConnection() throws IOException {
    in.close();
    out.close();
    socket.close();
  }
}
