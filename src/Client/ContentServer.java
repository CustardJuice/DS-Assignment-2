package Client;

import java.net.*;
import java.io.*;

public class ContentServer extends Client {
  public FileReader reader;
  public BufferedReader buff_read;

  /* METHODS */
  public String fileToString(String file_path) throws IOException {
    String msg = "";

    /* newline for the end of every line */
    String newline = "\r\n";

    /* initialise BufferedReader */
    reader = new FileReader(file_path);
    buff_read = new BufferedReader(reader);

    /* read line-by-line */
    String line;
    while ((line = buff_read.readLine()) != null) {
      msg += line;
      msg += newline;
    }

    return msg;
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

      /* Read file */
      String msg = content_server.fileToString(file);

      /* Prepend PUT header to msg body */
      msg = put + msg;

      /* Start Connection */
      content_server.startConnection(uri.getHost(), uri.getPort());

      /* Send/Recv */
      content_server.sendMessage(msg);

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