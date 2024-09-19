package Client;

import java.net.*;
import java.util.Map;
import java.util.Set;
import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class GETClient extends Client {
  /* Receive json data from server, parse, and output it */
  @SuppressWarnings("unchecked")
  @Override
  public String recv() {
    String response = "";
    try {
      String line = in.readLine();
      /* if not OK print entire response */
      if (!line.endsWith("OK")) {
        System.out.println(line);
        while ((line = in.readLine()) != null) {
          response += line + "\n";
        }
        return response;
      }

      /* skip headers */
      while ((line = in.readLine()).length() > 0) {
      }

      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject) parser.parse(in);

      /* receiving NESTED json */
      for (Map.Entry<String, JSONObject> entry : (Set<Map.Entry<String, JSONObject>>) json.entrySet()) {
        response += entry.getKey() + ":\n";
        for (Map.Entry<String, String> sub_entry : (Set<Map.Entry<String, String>>) entry.getValue().entrySet()) {
          response += "  " + sub_entry.getKey() + ": " + sub_entry.getValue() + "\n";
        }
      }
      return response;

    } catch (IOException e) {
      System.err.println(e + ": Could not receive message");
      e.printStackTrace();
    } catch (ParseException e) {
      System.err.println(e + ": Could not parse JSON data");
      e.printStackTrace();
    }
    return response;
  }

  public String run(String url, String station_id) {
    String response = "";
    try {
      /* Server address and port number from arguments */
      URI uri = new URI(url);

      /* Start Connection */
      startConnection(uri.getHost(), uri.getPort());

      /* PUT Header */
      String msg = "GET /aggregation.json?id=" + station_id + " HTTP/1.1\r\n";

      /* Send/Recv */
      send(msg);

      /* Handle Response */
      response = recv();

      /* End Connection */
      stopConnection();

      return response;

    } catch (URISyntaxException e) {
      System.err.println(e + ": could not parse URI");
      e.printStackTrace();
    }
    return response;
  }

  public String run(String url) {
    String response = "";
    try {
      /* Server address and port number from arguments */
      URI uri = new URI(url);

      /* Start Connection */
      startConnection(uri.getHost(), uri.getPort());

      /* PUT Header */
      String msg = "GET /aggregation.json HTTP/1.1\r\n";

      /* Send/Recv */
      send(msg);

      /* Handle Response */
      response = recv();

      /* End Connection */
      stopConnection();

      return response;

    } catch (URISyntaxException e) {
      System.err.println(e + ": could not parse URI");
      e.printStackTrace();
    }
    return response;
  }

  public static void main(String args[]) {
    GETClient client = new GETClient();
    try {
      if (args.length < 1) {
        throw new Exception();
      }
      String response;
      /* Optional station ID */
      if (args.length > 1) {
        response = client.run(args[0], args[1]);
        client.print(response);
        return;
      }

      response = client.run(args[0]);
      client.print(response);

    } catch (Exception e) {
      System.err.println(e + " Missing GETClient Argument: URL");
      e.printStackTrace();

    }
  }
}