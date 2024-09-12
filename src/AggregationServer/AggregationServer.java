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
      this.client_socket = socket;
      this.parser = new JSONParser();
    }

    private void handlePUT(String id) {
      try {
        String input_line;
        String json_body = "";
        JSONObject json;
        boolean new_file_created = false;

        /* skip headers */
        while ((input_line = in.readLine()).length() > 0) {
        }

        while ((input_line = in.readLine()) != null) {
          json_body += input_line;
          if (input_line.endsWith("}")) {
            break;
          }
        }

        /* check if id already exists */
        File f = new File(id);
        if (!f.exists()) {
          new_file_created = true;
        }

        /* create/edit json object and save to a file */
        json = (JSONObject) parser.parse(json_body);
        if (json.isEmpty()) {
          out.println("HTTP/1.1 204 No Content\r\n");
          return;
        }

        FileWriter file = new FileWriter(id);
        file.write(json.toJSONString());
        file.flush();
        file.close();

        if (new_file_created) {
          out.println("HTTP/1.1 201 Created\r\n");
        } else {
          out.println("HTTP/1.1 200 OK\r\n");
        }

      } catch (IOException e) {
        out.println("HTTP/1.1 500 Internal Server Error\r\n");
        System.err.println(e + ": Error handling PUT request");
        e.printStackTrace();
      } catch (ParseException e) {
        out.println("HTTP/1.1 500 Internal Server Error\r\n");
        System.err.println(e + ": Could not parse JSON data");
        e.printStackTrace();
      }

    }

    private void handleGET() {

    }

    private void closeConnection() {
      try {
        in.close();

        out.close();
        client_socket.close();
      } catch (IOException e) {
        System.err.println(e + ": Could not close connection");
        e.printStackTrace();
      }
    }

    public void run() {
      try {
        /* Initialise Reader */
        out = new PrintWriter(client_socket.getOutputStream(), true);
        in = new BufferedReader(
            new InputStreamReader(client_socket.getInputStream()));

        /* Recieve message header */
        String input_line = in.readLine();

        int idx = input_line.indexOf("/");
        String id = input_line.substring(idx + 1, input_line.indexOf(" ", idx));

        if (input_line.startsWith("PUT")) {
          handlePUT(id);
        } else if (input_line.startsWith("GET")) {
          handleGET();
        } else {
          out.println("HTTP/1.1 400 Bad Request\r\n");
          System.err.println("Message was not GET or PUT");
        }

        closeConnection();

      } catch (IOException e) {
        System.err.println("Server Client Handler Error");
        e.printStackTrace();
      }
    }
  }
}