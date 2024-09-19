package Client;

import java.net.*;
import java.io.*;

public class Client {
  public Socket socket;
  public PrintWriter out;
  public BufferedReader in;

  /*
   * Connects the Client object to a running server
   * Parameters:
   * ip, server hostname
   * port, server port number
   */
  public void startConnection(String ip, int port) {
    try {
      socket = new Socket(ip, port);
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    } catch (IOException e) {
      System.err.println(e + ": Could not start client connection");
      e.printStackTrace();
    }
  }

  /*
   * Sends a message to the connected server
   * Parameters:
   * msg, the message to send
   */
  public void send(String msg) {
    out.println(msg);
  }

  /* Reads a message from the server line-by-line, until a null line is read */
  public String recv() {
    String response = "";
    String line;
    try {
      while ((line = in.readLine()) != null) {
        /* print line */
        response += line + "\n";
      }
    } catch (IOException e) {
      System.err.println(e + ": Could not receive message");
      e.printStackTrace();
    }
    return response;
  }

  /* Prints message to std out, generally used for printing recv() */
  public void print(String s) {
    System.out.println(s);
  }

  /* Ends the connection with the server */
  public void stopConnection() {
    try {
      in.close();
      out.close();
      socket.close();

    } catch (IOException e) {
      System.err.println(e + ": Could not close connection with server");
      e.printStackTrace();
    }
  }
}
