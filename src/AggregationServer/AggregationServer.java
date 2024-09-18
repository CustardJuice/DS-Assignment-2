package AggregationServer;

import java.net.*;
import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class AggregationServer {
  private ServerSocket server_socket;

  public static void main(String[] args) {
    AggregationServer server = new AggregationServer();
    int port = 4567; // 4567 default
    if (args.length > 0) {
      port = Integer.parseInt(args[0]);
    }

    try {
      server.start(port);
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
    private JSONParser parser;

    public ClientHandler(Socket socket) {
      client_socket = socket;
      parser = new JSONParser();
    }

    /*
     * Sends a message to the connected client with newline
     * Parameters:
     * msg, the message to send
     */
    public void send(String msg) {
      out.println(msg + "\r\n");
    }

    @SuppressWarnings("unchecked")
    private void handlePUT() {

      try {
        String input_line;
        String json_body = "";
        JSONObject json_content = new JSONObject();
        JSONObject json_aggregation = new JSONObject();
        boolean new_content = false;

        /* skip headers */
        while ((input_line = in.readLine()).length() > 0) {
        }

        while ((input_line = in.readLine()) != null) {
          json_body += input_line;
          if (input_line.endsWith("}")) {
            break;
          }
        }

        /* create/edit json object and save to a file */
        json_content = (JSONObject) parser.parse(json_body);
        if (json_content.isEmpty()) {
          logger("No Content, sending HTTP 204");
          send("HTTP/1.1 204 No Content");
          return;
        }

        String id = (String) json_content.get("id");

        /*
         * turn aggregation.json into an aggregation object
         * 
         * NOTE: this might be inefficient for large file size. Can change to edit the
         * file directly.
         */
        File f = new File("aggregation.json");
        if (f.length() > 0) {
          /* TODO: MAKE THREAD SAFE */
          json_aggregation = (JSONObject) parser.parse(new FileReader("aggregation.json"));
        }

        /* check if content id already exists */
        new_content = !json_aggregation.containsKey(id);

        /* override/add data for this id */
        /* TODO: MAKE THREAD SAFE */
        json_aggregation.put(id, json_content);

        /* is this server crash safe? */
        /* TODO: MAKE THREAD SAFE */
        FileWriter file = new FileWriter("aggregation.json");
        file.write(json_aggregation.toJSONString());
        file.flush();
        file.close();

        if (new_content) {
          logger("new content Created, sending HTTP 201");
          send("HTTP/1.1 201 Created");
          return;
        }
        logger("content modified OK, sending HTTP 200");
        send("HTTP/1.1 200 OK");

      } catch (IOException e) {
        send("HTTP/1.1 500 Internal Server Error");
        logger(e + ": Error handling PUT request");
        e.printStackTrace();
      } catch (ParseException e) {
        send("HTTP/1.1 500 Internal Server Error");
        logger(e + ": Could not parse JSON data");
        e.printStackTrace();
      }
    }

    private void handleGET(String target) {
      JSONObject json_aggregation;
      String file;
      String id = "";

      /* check if client requested specific stationID */
      int idx = target.indexOf('?');
      if (idx == -1) {
        file = target;
      } else {
        file = target.substring(0, idx);
        /*
         * Server assumes that the client is making a query for a specific stationID, so
         * it only checks the value of the query, not the key (assumes format key=value)
         */
        int idx2 = target.indexOf('=');
        if (idx2 == -1) {
          logger("invalid GET query format");
          send("HTTP/1.1 404 Not Found");
          send("error: could not understand query");
          return;
        }
        id = target.substring(idx2 + 1);
      }

      /*
       * turn aggregation.json into an aggregation object
       * 
       * NOTE: this might be inefficient for large file size. Can change to edit the
       * file directly.
       */
      File f = new File(file);
      if (!file.equals("aggregation.json") || !f.exists() || f.length() == 0) {
        logger(file + " not found or empty");
        send("HTTP/1.1 404 Not Found");
        send("error: " + file + " not found in server directory");
        return;
      }

      try {
        /* TODO: MAKE THREAD SAFE */
        json_aggregation = (JSONObject) parser.parse(new FileReader(file));

        /* if id was specified in query */
        if (id.length() > 0) {
          /* if content id exists return return HTTP OK <relevant-data> */
          if (json_aggregation.containsKey(id)) {
            JSONObject json_content = (JSONObject) json_aggregation.get(id);
            logger("sending content from station with id=" + id);
            send("HTTP/1.1 200 OK");
            send(json_content.toJSONString());
          } else {
            logger("server does not have data for id=" + id);
            send("HTTP/1.1 404 Not Found");
            send("error: found " + file + " but " + id + " not found");
          }
        }
        /* else return HTTP OK <all-data> */
        else {
          logger("no id queried sending all content");
          send("HTTP/1.1 200 OK");
          send(json_aggregation.toJSONString());
        }

      } catch (IOException e) {
        send("HTTP/1.1 500 Internal Server Error");
        logger(e + ": Error handling PUT request");
        e.printStackTrace();
      } catch (ParseException e) {
        send("HTTP/1.1 500 Internal Server Error");
        logger(e + ": Could not parse JSON data");
        e.printStackTrace();
      }
    }

    private void closeConnection() {
      try {
        in.close();

        out.close();
        client_socket.close();
      } catch (IOException e) {
        logger(e + ": Could not close connection");
        e.printStackTrace();
      }
    }

    private void logger(String msg) {
      System.out.println(currentThread().getName() + " Log: " + msg);
    }

    public void run() {
      try {
        /* Initialise Reader */
        out = new PrintWriter(client_socket.getOutputStream(), true);
        in = new BufferedReader(
            new InputStreamReader(client_socket.getInputStream()));

        logger("new client connection made");

        /* Recieve message header */
        String input_line = in.readLine();

        if (input_line.startsWith("PUT")) {
          logger("PUT request detected");
          handlePUT();
        } else if (input_line.startsWith("GET")) {
          logger("GET request detected");

          int idx = input_line.indexOf("/");
          String file = input_line.substring(idx + 1, input_line.indexOf(" ", idx));
          handleGET(file);
        } else {
          send("HTTP/1.1 400 Bad Request");
          System.err.println("Message was not GET or PUT");
        }

        closeConnection();
        logger("connection closed");

      } catch (IOException e) {
        logger("Server Client Handler Error");
        e.printStackTrace();
      }
    }
  }
}