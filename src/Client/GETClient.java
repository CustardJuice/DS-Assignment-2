package Client;

import java.net.*;
import java.util.Map;
import java.util.Set;
import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class GETClient extends Client {
  public String stationID;

  public GETClient() {
    super();
    stationID = "";
  }

  /* Receive json data from server, parse, and output it */
  @Override
  public void recv() {
    try {
      String line = in.readLine();
      /* if not OK print entire response */
      if (!line.endsWith("OK")) {
        System.out.println(line);
        while ((line = in.readLine()) != null) {
          System.out.println(line);
        }
        return;
      }

      /* skip headers */
      while ((line = in.readLine()).length() > 0) {
      }
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject) parser.parse(in);

      /* receiving nested json */
      if (stationID.length() == 0) {
        for (Map.Entry<String, JSONObject> entry : (Set<Map.Entry<String, JSONObject>>) json.entrySet()) {
          System.out.println(entry.getKey() + ":");
          for (Map.Entry<String, String> sub_entry : (Set<Map.Entry<String, String>>) entry.getValue().entrySet()) {
            System.out.println("  " + sub_entry.getKey() + ": " + sub_entry.getValue());
          }
        }
        return;
      }

      System.out.println(stationID + ":");
      for (Map.Entry<String, String> entry : (Set<Map.Entry<String, String>>) json.entrySet()) {
        System.out.println("  " + entry.getKey() + ": " + entry.getValue());
      }

    } catch (IOException e) {
      System.err.println(e + ": Could not receive message");
      e.printStackTrace();
    } catch (ParseException e) {
      System.err.println(e + ": Could not parse JSON data");
      e.printStackTrace();
    }
  }

  public void run() {
    /* PUT Header */
    String msg;
    if (stationID.length() == 0) {
      msg = "GET /aggregation.json HTTP/1.1\r\n";
    } else {
      msg = "GET /aggregation.json?id=" + stationID + " HTTP/1.1\r\n";
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
      if (args.length > 1) {
        client.stationID = args[1];
      }

      /* Start Connection */
      client.startConnection(uri.getHost(), uri.getPort());

      client.run();
    } catch (URISyntaxException e) {
      System.err.println(e + ": could not parse URI");
      e.printStackTrace();
    }
  }
}