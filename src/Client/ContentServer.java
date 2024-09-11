package Client;

import java.net.*;
import java.io.*;
import org.json.simple.*;

public class ContentServer extends Client {
  public FileReader reader;
  public BufferedReader buff_read;
  public JSONObject json;

  /* METHODS */
  // public String fileToString(String file_path) throws IOException {
  // String data = "";

  // /* newline for the end of every line */
  // String newline = "\r\n";

  // /* initialise BufferedReader */
  // reader = new FileReader(file_path);
  // buff_read = new BufferedReader(reader);

  // /* read line-by-line */
  // String line;
  // while ((line = buff_read.readLine()) != null) {
  // data += line;
  // data += newline;
  // }

  // return data;
  // }

  /* Given path to file, parse file contents and populate local json */
  @SuppressWarnings("unchecked")
  public void makeJSONFromFile(String file) {
    try {
      /* initialise BufferedReader */
      reader = new FileReader(file);
      buff_read = new BufferedReader(reader);

      /* read line-by-line */
      String line;
      while ((line = buff_read.readLine()) != null) {
        int tok = line.indexOf(':');
        String key = line.substring(0, tok);
        String value = line.substring(tok + 1);
        json.put(key, value);
      }

    } catch (IOException e) {
      System.err.println("Problem parsing ContentServer file");
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    try {
      if (args.length < 2) {
        System.err.println("Missing ContentServer Arguments: URL/File");
        return;
      }

      /* Create new ContentServer object */
      ContentServer content_server = new ContentServer();

      /* Read server address and port number from command line */
      URI uri = new URI(args[0]);

      /* Local file to access */
      String file = args[1];

      /* PUT Header */
      String put = "PUT " + file + " HTTP/1.1\r\n";

      content_server.json = content_server.makeJSONFromFile(file);

      /* Start Connection */
      content_server.startConnection(uri.getHost(), uri.getPort());

      /* Send/Recv */
      content_server.sendMessage(put + content_server.json.toString());

      /* Handle Response */
      content_server.recvMessage();

      /* End Connection */
      content_server.stopConnection();

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