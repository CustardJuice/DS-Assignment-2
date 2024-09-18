package Client;

import java.net.*;
import java.io.*;

public class GETClient extends Client {
  public void run(String id) {
    /* PUT Header */
    String msg;
    if (id.length() == 0) {
      msg = "GET /aggregation.json HTTP/1.1\r\n";
    } else {
      msg = "GET /aggregation.json?id=" + id + " HTTP/1.1\r\n";
    }

    /* Send/Recv */
    send(msg);

    /* Handle Response */
    recv();

    /* End Connection */
    stopConnection();
  }

  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Missing GETClient Argument: URL");
      return;
    }

    try {
      /* Create new client object */
      GETClient client = new GETClient();

      /* Read server address and port number from command line */
      URI uri = new URI(args[0]);

      /* Optional station ID */
      String stationID = "";
      if (args.length > 1) {
        stationID = args[1];
      }

      /* Start Connection */
      client.startConnection(uri.getHost(), uri.getPort());

      client.run(stationID);
    } catch (URISyntaxException e) {
      System.err.println(e + ": could not parse URI");
      e.printStackTrace();
    }
  }
}