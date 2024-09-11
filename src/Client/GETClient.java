package Client;

import java.net.*;
import java.io.*;

public class GETClient extends Client {
  public static void main(String[] args) {
    try {
      if (args.length < 1) {
        System.err.println("Missing GETClient Argument: URL");
        return;
      }
      /* Create new client object */
      GETClient client = new GETClient();

      /* Read server address and port number from command line */
      URI uri = new URI(args[0]);

      /* Optional station ID */
      int stationID = -1;
      if (args.length > 1) {
        stationID = Integer.parseInt(args[2]);
      }

      /* Server file to access */
      String file = "index.html";

      /* GET Message */
      String msg = "GET /" + file + "\r\n";
      /* Start Connection */
      client.startConnection(uri.getHost(), uri.getPort());

      /* Send Message */
      client.sendMessage(msg);

      /* Handle Response */
      client.recvMessage();

      /* Close Connection */
      client.stopConnection(); 

    } catch (URISyntaxException e) {
      System.err.println("Error Parsing URI argument");
      e.printStackTrace();
    } catch (UnknownHostException e) {
      System.err.println("Error Starting Client Connection: UnknownHostException");
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("Error Starting Client Connection: IOException");
      e.printStackTrace();
    }
  }
}