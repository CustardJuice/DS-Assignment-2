package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Test;

import Client.Client;

public class ClientTest {
  @Test
  public void SimpleServerEcho1() throws UnknownHostException, IOException {
    Client client1 = new Client();
    client1.startConnection("127.0.0.1", 6666);
    String msg1 = client1.sendMessage("hello");
    String msg2 = client1.sendMessage("world");
    String terminate = client1.sendMessage(".");

    assertEquals(msg1, "hello");
    assertEquals(msg2, "world");
    assertEquals(terminate, "bye");
  }

  @Test
  public void SimpleServerEcho2() throws UnknownHostException, IOException {
    Client client2 = new Client();
    client2.startConnection("127.0.0.1", 6666);
    String msg1 = client2.sendMessage("hello");
    String msg2 = client2.sendMessage("world");
    String terminate = client2.sendMessage(".");

    assertEquals(msg1, "hello");
    assertEquals(msg2, "world");
    assertEquals(terminate, "bye");
  }
}
