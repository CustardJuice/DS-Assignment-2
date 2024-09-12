package Client;

import java.net.*;
import java.io.*;
import org.json.simple.*;

public class ContentServer extends Client {
  public FileReader reader;
  public BufferedReader buff_read;
  public JSONObject json;

  /* METHODS */
  public ContentServer() {
    json = new JSONObject();
  }

  // public String fileToString(String file_path) throws IOException {
  // String file_id = "";

  // /* newline for the end of every line */
  // String newline = "\r\n";

  // /* initialise BufferedReader */
  // reader = new FileReader(file_path);
  // buff_read = new BufferedReader(reader);

  // /* read line-by-line */
  // String line;
  // while ((line = buff_read.readLine()) != null) {
  // file_id += line;
  // file_id += newline;
  // }

  // return file_id;
  // }

  @SuppressWarnings("unchecked")
  /*
   * Given path to file, parse file contents and populate local json
   * 
   * @param file path to Content file.
   * 
   * @returns true if successfully filled json object, false otherwise.
   */
  public boolean makeJSONFromFile(String file) {
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
      String id = (String) json.get("id");
      if (id.length() == 0) {
        throw new Exception("No id in ContentServer file");
      }

    } catch (IOException e) {
      System.err.println("Problem parsing ContentServer file");
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public static void main(String[] args) {
    if (args.length < 2) {
      System.err.println("Missing ContentServer Arguments: URL/File");
      return;
    }

    try {
      /* Create new ContentServer object */
      ContentServer content_server = new ContentServer();

      /* Read server address and port number from command line */
      URI uri = new URI(args[0]);

      /* Local file to access */
      String file = args[1];

      if (!content_server.makeJSONFromFile(file)) {
        return;
      }

      String type = "application/json";
      String body = content_server.json.toJSONString();
      /* Number of bytes in body */
      String length = Integer.toString(body.length() * 2);

      /* Hard coded name for now */
      String file_id = (String) content_server.json.get("id") + ".json";

      /* PUT Header */
      String headers = "PUT /" + file_id + " HTTP/1.1\r\n";
      headers += "User-Agent: ATOMClient/1/0\r\n";
      headers += "Content-Type: " + type + "\r\n";
      headers += "Content-Length: " + length + "\r\n";
      headers += "\r\n";

      /* Start Connection */
      content_server.startConnection(uri.getHost(), uri.getPort());

      /* Send/Recv */
      content_server.sendMessage(headers + body);

      /* Handle Response */
      content_server.recvMessage();

      /* End Connection */
      content_server.stopConnection();
    } catch (URISyntaxException e) {
      System.err.println(e + ": could not parse URI");
      e.printStackTrace();
    }
  }
}
